#include "everything.h"

int *pipe4;
int fd;
int waitPID;

//handler per la ricezione del segnale di incremento
void sigHandlerINCR1(int sig) {
	printf("INCR1\n");
	increase_delayR1();	
}

//handler per la ricezione del segnale di rimozione
void sigHandlerREMR1(int sig) {
	printf("REMR1\n");
	free_list(1);
	remove_messageR1();
}

//handler per la ricezione del segnale di invio
void sigHandlerSENDR1(int sig) {
	printf("SENDR1\n");
	send_messageR1();
}

//ricevo SIGTERM
void sigtermHandlerR1(int sig) {
	printf("SHUTR1\n");

	remove_messageR1();
		
	sleep(5);
	
	debig("OutputFiles/F6.csv");

	close_rd_end_pipe(pipe4);

	if(close(fd) == -1)
		err_exit("close fd", __FILE__, __LINE__);

	exit(0);
}

void R1(int argc, char * argv[], int *PIPE4, int msgqid, int semid, int shmid) {
	char buffer[BUFLEN];
	int nBys = 0;
	struct msg m;
	pid_t pid;
	// chiusura write-end della pipe4
	close_wr_end_pipe(PIPE4);

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

	if (signal(SIGUSR1, sigHandlerINCR1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGUSR2, sigHandlerREMR1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGQUIT, sigHandlerSENDR1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGTERM, sigtermHandlerR1) == SIG_ERR)
         err_exit("change signal handler failed", __FILE__, __LINE__);
	
	// creazione del file di uscita 
	fd = open("OutputFiles/F6.csv", O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Message;IDSender;IDReceiver;TimeArrival;TimeDeparture;\n\n";
	write(fd, bufferW, sizeof(bufferW) - 1);
    	
	while(1){
		//lettura della PIPE
		nBys = read_pipe(PIPE4, buffer);
		if (nBys > 0){
			pid = fork();
			if(pid == 0){
				buffer[nBys] = '\0';  
				reader_buffer(buffer, strlen(buffer), fd, 0, 1, semid);
				int stato = 0;
				while((waitPID = wait(&stato)) > 0);
				exit(0);
			}
		}

		//attacco il segmento di memoria condivisa
		char *shmbuf = (char *)get_shared_memory(shmid, SHM_RDONLY);		
		if(shmbuf[0] == 49){
			semaphore_op(semid, (unsigned short)1, -1);
			strncpy(buffer, shmbuf, strlen(shmbuf));
			buffer[0] = 32;
			semaphore_op(semid, (unsigned short)2, 1);
			pid = fork();
			if(pid == 0){			
				reader_buffer(buffer, strlen(buffer), fd, 0, 1, semid);
				int stato = 0;
				while((waitPID = wait(&stato)) > 0);
				exit(0);
			}
		}
		free_shared_memory(shmbuf);

		//lettura messaggi dalla message queue
		size_t mSize = sizeof(struct msg) - sizeof(long);
        if (msgrcv(msgqid, &m, mSize, 1, IPC_NOWAIT) == -1){
			if (errno == ENOMSG) {
                continue;
            }
			else{
            	err_exit("msgget failed", __FILE__, __LINE__);
			}
		}else{
			pid = fork();
			if(pid == 0){
				reader_buffer(buffer, strlen(buffer), fd, 0, 1, semid);
				int stato = 0;
				while((waitPID = wait(&stato)) > 0);
				exit(0);
			}
		}
	}
}
