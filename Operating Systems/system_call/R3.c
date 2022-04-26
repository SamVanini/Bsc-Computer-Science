#include "everything.h"

int *pipe3;
int fd;
int fdfifo;
char fname[] = "OutputFiles/my_fifo.txt";
pid_t waitPID;

//handler per la ricezione del segnale di incremento
void sigHandlerINCR3(int sig) {
	printf("INCR3\n");
	increase_delayR3();
}

//handler per la ricezione del segnale di rimozione
void sigHandlerREMR3(int sig) {
	printf("REMR3\n");
	free_list(3);
	remove_messageR3();
}

//handler per la ricezione del segnale di invio
void sigHandlerSENDR3(int sig) {
	printf("SENDR3\n");
	send_messageR3();
}

//ricevo SIGTERM
void sigtermHandlerR3(int sig) {
	printf("SHUTR3\n");

	remove_messageR3();
		
	sleep(5);
	
	debig("OutputFiles/F4.csv");

	close_wr_end_pipe(pipe3);

	if (close(fd) == -1)
         err_exit("Close failed", __FILE__, __LINE__);	

	close_fifo(fdfifo, fname);

	exit(0);
}

void R3(int argc, char * argv[], int *PIPE3, int msgqid, int semid, int shmid) {
	char buffer[BUFLEN];
	int nBys = 0;
	struct msg m;
	pid_t pid;

	// creazione del file di uscita 
	fd = open("OutputFiles/F4.csv", O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);

	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Message;IDSender;IDReceiver;TimeArrival;TimeDeparture;\n\n";
	write(fd, bufferW, sizeof(bufferW) - 1);

	//creazione fifo	
	fdfifo = open_read_fifo(fname);

	fcntl(fdfifo, F_SETFL, O_NDELAY);

	// chiusura read-end della pipe3
	close_rd_end_pipe(PIPE3);

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

	if (signal(SIGUSR1, sigHandlerINCR3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGUSR2, sigHandlerREMR3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGQUIT, sigHandlerSENDR3) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGTERM, sigtermHandlerR3) == SIG_ERR)
         err_exit("change signal handler failed", __FILE__, __LINE__);
    
	while(1){  
		//lettura della PIPE
		nBys = read_pipe(PIPE3, buffer);
		if (nBys > 0){
			pid = fork();
			if(pid == 0){
				buffer[nBys] = '\0';
				reader_buffer(buffer, strlen(buffer), fd, PIPE3, 3, semid);
				exit(0);
			}
		}

		//attacco il segmento di memoria condivisa
		char *shmbuf = (char *)get_shared_memory(shmid, SHM_RDONLY);		
		if(shmbuf[0] == 51){
			semaphore_op(semid, (unsigned short)1, -1);
			strncpy(buffer, shmbuf, strlen(shmbuf));
			buffer[0] = 32;
			semaphore_op(semid, (unsigned short)2, 1);	
			pid = fork();
			if(pid == 0){		
				reader_buffer(buffer, strlen(buffer), fd, PIPE3, 3, semid);
				exit(0);
			}
		}
		free_shared_memory(shmbuf);

		//lettura messaggi dalla message queue
		size_t mSize = sizeof(struct msg) - sizeof(long);
        if (msgrcv(msgqid, &m, mSize, 2, IPC_NOWAIT) == -1){
			if (errno == ENOMSG) {
                continue;
            }
			else{
            	err_exit("msgget failed", __FILE__, __LINE__);
			}
		}else{
			pid = fork();
			if(pid == 0){
				reader_buffer(m.description, strlen(m.description), fd, PIPE3, 3, semid);
				exit(0);
			}
		}
	}
}
