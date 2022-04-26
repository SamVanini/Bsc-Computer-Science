#include "everything.h"

int *pipe1;
int *pipe2;
int fd;
pid_t waitPID;

//handler per la ricezione del segnale di incremento
void sigHandlerINCS2(int sig) {
	printf("INCS2\n");
	increase_delayS2();
}

//handler per la ricezione del segnale di rimozione
void sigHandlerREMS2(int sig) {
	printf("REMS2\n");
	free_list(2);
	remove_messageS2();
}

//handler per la ricezione del segnale di invio
void sigHandlerSENDS2(int sig) {
	printf("SENDS2\n");
	send_messageS2();
}

//ricevo SIGTERM
void sigtermHandlerS2(int sig) {
	printf("SHUTS2\n");
	
	remove_messageS2();
	
	close_rd_end_pipe(pipe1);

	if (close(fd) == -1)
    	err_exit("Close failed", __FILE__, __LINE__);	

	sleep(5);
	
	debug("OutputFiles/F2.csv");

	close_wr_end_pipe(pipe2);
   
	free_list(2);

	// uscita
	exit(0);
}

void S2(int argc, char * argv[], int *PIPE1, int *PIPE2, int msgqid, int semid, int shmid) {
	int nBys = 0;
	char buffer[BUFLEN];

	pipe1 = PIPE1;
	pipe2 = PIPE2;
	
	// chiusura write-end della pipe1
	close_wr_end_pipe(PIPE1);
    	
    // chiusura read-end della pipe2
	close_rd_end_pipe(PIPE2);

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
	// rimuovi SIGTERM from mySet
    sigdelset(&mySet, SIGTERM);
	// rimuovi SIGTERM from mySet
    sigdelset(&mySet, SIGCONT);
	// rimuovi SIGSTOP from mySet
    sigdelset(&mySet, SIGSTOP);
    // blocca tutti i segnali tranne i precedenti
    sigprocmask(SIG_SETMASK, &mySet, NULL);

	if (signal(SIGUSR1, sigHandlerINCS2) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGUSR2, sigHandlerREMS2) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGQUIT, sigHandlerSENDS2) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGTERM, sigtermHandlerS2) == SIG_ERR)
         err_exit("change signal handler failed", __FILE__, __LINE__);

	// creazione del file di uscita 
	fd = open("OutputFiles/F2.csv", O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Message;IDSender;IDReceiver;TimeArrival;TimeDeparture;\n\n";
	write(fd, bufferW, sizeof(bufferW) - 1);

	while(1){

		//lettura della pipe
		nBys = read_pipe(PIPE1, buffer);
		if (nBys > 0){
			pid_t pid = fork();
			if(pid == 0){
				buffer[nBys] = '\0';
				Reader_buffer(buffer, strlen(buffer), fd, PIPE2, 2, msgqid, semid, shmid, 0);
				int stato = 0;
				while((waitPID = wait(&stato)) > 0);
				exit(0);
			}	
		}
	}
}
