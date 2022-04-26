#include "everything.h"

int *pipe1;
int fd;
pid_t waitPID;

//handler per la ricezione del segnale di incremento
void sigHandlerINCS1(int sig) {
	printf("INCS1\n");
	increase_delayS1();
}

//handler per la ricezione del segnale di rimozione
void sigHandlerREMS1(int sig) {
	printf("REMS1\n");
	free_list(1);
	remove_messageS1();
}

//handler per la ricezione del segnale di invio
void sigHandlerSENDS1(int sig) {
	printf("SENDS1\n");
	send_messageS1();
}

//ricevo SIGTERM
void sigtermHandlerS1(int sig) {
	printf("SHUTS1\n");
	
	remove_messageS1();
	
	int stato = 0;
	while((waitPID = wait(&stato)) > 0); 
	
	close_wr_end_pipe(pipe1);

	if(close(fd) == -1)
		err_exit("close fd", __FILE__, __LINE__);

	debug("OutputFiles/F1.csv");

	exit(0);
}

void S1(int argc, char * argv[], int *PIPE1, int msgqid, int semid, int shmid) {
	char bufferR[BUFLEN]; // buffer su cui andrà caricato il contenuto del file contenuto in argv[1]
	pipe1 = PIPE1;
	// chiusura read-end della pipe
	close_rd_end_pipe(PIPE1);

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

	if (signal(SIGUSR1, sigHandlerINCS1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGUSR2, sigHandlerREMS1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGQUIT, sigHandlerSENDS1) == SIG_ERR)
        err_exit("change signal handler failed", __FILE__, __LINE__);

	if (signal(SIGTERM, sigtermHandlerS1) == SIG_ERR)
         err_exit("change signal handler failed", __FILE__, __LINE__);
	
	// salvataggio del file in argv[1] nel buffer
	load_file(argv[1], bufferR); 
	strcat(bufferR, "\n");
	
	// creazione del file di uscita 
	fd = open("OutputFiles/F1.csv", O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Message;IDSender;IDReceiver;TimeArrival;TimeDeparture;\n";
	write(fd, bufferW, sizeof(bufferW));

	// caricamento del contenuto di bufferR alla lista,
	// stampa dei nodi della lista sul file di uscita ed
	// invio attraverso compile_file(fd);
	Reader_buffer(bufferR, strlen(bufferR), fd, PIPE1, 1, msgqid, semid, shmid, 0);	
	
	while(1);

}
