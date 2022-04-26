#include "everything.h"

int *pipe2;
int fd;
pid_t waitPID;

//handler per la ricezione del segnale di incremento
void sigHandlerINCS3(int sig) {
	printf("INCS3\n");
	increase_delayS3();
}

//handler per la ricezione del segnale di rimozione
void sigHandlerREMS3(int sig) {
	printf("REMS3\n");
	free_list(3);
	remove_messageS3();
}

//handler per la ricezione del segnale di invio
void sigHandlerSENDS3(int sig) {
	printf("SENDS3\n");
	send_messageS3();
}

//ricevo SIGTERM
void sigtermHandlerS3(int sig) {
	printf("SHUTS3\n");
	
	remove_messageS3();
	
	if (close(fd) == -1)
         err_exit("Close failed", __FILE__, __LINE__);	

	sleep(5);
	debug("OutputFiles/F3.csv");

	close_rd_end_pipe(pipe2);

	free_list(3);
	// uscita
	exit(0);
}

void S3(int argc, char * argv[], int *PIPE2, int msgqid, int semid, int shmid, int fdfifo) {
	int nBys;
	char buffer[BUFLEN];
	
	pipe2 = PIPE2;

	// chiusura write-end della pipe1
	close_wr_end_pipe(PIPE2);

	// set di segnali (N.B. Non è inizializzato!)
    sigset_t mySet;
    // inizializza mySet per contenere tutti i segnali
    sigfillset(&mySet);
    // rimuovi SIGUSR1 from mySet
    sigdelset(&mySet, SIGUSR1);
	// rimuovi SIGUSR3 from mySet
    sigdelset(&mySet, SIGUSR2);
	// rimuovi SIGALRM from mySet
    sigdelset(&mySet, SIGQUIT);
	// rimuovi SIGCONT from mySet
    sigdelset(&mySet, SIGCONT);
	// rimuovi SIGSTOP from mySet
    sigdelset(&mySet, SIGSTOP);
	// rimuovi SIGTERM from mySet
    sigdelset(&mySet, SIGTERM);
    // blocca tutti i segnali tranne i precedenti
    sigprocmask(SIG_SETMASK, &mySet, NULL);

	if (signal(SIGUSR1, sigHandlerINCS3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGUSR2, sigHandlerREMS3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGQUIT, sigHandlerSENDS3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGTERM, sigtermHandlerS3) == SIG_ERR)
         err_exit("change signal handler failed", __FILE__, __LINE__);

	// creazione del file di uscita 
	fd = open("OutputFiles/F3.csv", O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Message;IDSender;IDReceiver;TimeArrival;TimeDeparture;\n\n";
	write(fd, bufferW, sizeof(bufferW) - 1);
	
	while(1){

		//lettura della PIPE
		nBys = read_pipe(PIPE2, buffer);
		if (nBys > 0){
			pid_t pid = fork();
			if(pid == 0){
				buffer[nBys] = '\0';
				Reader_buffer(buffer, strlen(buffer), fd, 0, 3, msgqid, semid, shmid, fdfifo);
				int stato = 0;
				while((waitPID = wait(&stato)) > 0);
				exit(0);
			}	
		}
	}
}
