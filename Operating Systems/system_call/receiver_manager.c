/// @file receiver_manager.c
/// @brief Contiene l'implementazione del receiver_manager.

#include "everything.h"

int main(int argc, char * argv[]) {
	//dichiarazioni variabili per il file f10
	char BUFFERPIPE3[BUFLEN];
	char BUFFERPIPE4[BUFLEN];
	char BUFFERSEM3[BUFLEN];
	char buffer[BUFLEN];

	// dichiaro le due pipe
	int PIPE3[2];
	int PIPE4[2];

	// dichiarazione variabili
	pid_t pidR1, pidR2, pidR3, waitPID;

	//attesa per sincronizzare la creazione dei semafori
	sleep(1);

	//creazione semaforo per gestire la lettura sul file F9.csv con hackler.c
	int fdsem1 = open("tmp6.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey1 = ftok("tmp6.txt", 't');
	if (semkey1 == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid1 = semget(semkey1, 1, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid1 == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}

	//creazione semaforo per la gestione concorrente della scrittura su file F10.csv
	int fdsem10 = open("tmp7.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey10 = ftok("tmp7.txt", 'o');
	if (semkey10 == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid10 = semget(semkey10, 2, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid10 == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}
	
	//creazione stringa iniziale per semaforo da scrivere su file F10.csv
	int length = snprintf(BUFFERSEM3, BUFLEN, "SEM;%X;RM;", semkey10);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t semF10 = fork();
	if(semF10 == 0){
		int tmp = open("sem3.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		char time[BUFLEN];
		take_time(time);
		write(tmp, time, strlen(time));
		close(tmp);
		exit(0);
	}
	
	//creazione shared-memory utilizzata dai processi figli
	int fdshm = open("tmp2.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	if (fdshm == -1){
		err_exit("open failed", __FILE__, __LINE__);
	}
	key_t shmKey = ftok("tmp2.txt", 'm');
	if (shmKey == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int shmid = alloc_shared_memory(shmKey, sizeof(char) * BUFLEN);
	
	//creazione messagequeue usata dai processi figli
	key_t msgkey = ftok("tmp.txt", 'q');
	if (msgkey == -1)
		err_exit("ftok failed", __FILE__, __LINE__);
    int msgqid = msgget(msgkey, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (msgqid == -1){
        err_exit("msgget failed", __FILE__, __LINE__); 
	}

	//creazione delle pipe
	create_pipe(PIPE3);
	create_pipe(PIPE4);

	//creazione stringa iniziale per PIPE3 da scrivere su file F10.csv
	length = snprintf(BUFFERPIPE3, BUFLEN, "PIPE3;%d/%d;RM;", PIPE3[0], PIPE3[1]);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t pipe3 = fork();
	if(pipe3 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("pipe3.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//creazione stringa iniziale per PIPE4 da scrivere su file F10.csv
	length = snprintf(BUFFERPIPE4, BUFLEN, "PIPE4;%d/%d;RM;", PIPE4[0], PIPE4[1]);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t pipe4 = fork();
	if(pipe4 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("pipe4.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}
	
	//rendo le PIPE3 e PIPE4 non bloccanti in lettura
	if(fcntl(PIPE3[0], F_SETFL, O_NONBLOCK) == -1)
		err_exit("fcntl failed", __FILE__, __LINE__);
	if(fcntl(PIPE4[0], F_SETFL, O_NONBLOCK) == -1)
		err_exit("fcntl failed", __FILE__, __LINE__);

	//creazione semaforo utilizzato per lettura/scrittura shared-memory
	int fdsem = open("tmp1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey = ftok("tmp1.txt", 's');
	if (semkey == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid = semget(semkey, 3, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}

	//generazione del figlio R1
	pidR1 = fork();
	
	if(pidR1 == 0) {
		pidR1 = getpid();
		R1(argc, argv, PIPE4, msgqid, semid, shmid);
		exit(0);
	}

	//generazione del figlio R2
	pidR2 = fork();
	
	if(pidR2 == 0) {
		pidR2 = getpid();
		R2(argc, argv, PIPE3, PIPE4, msgqid, semid, shmid);
		exit(0);
	}

	//generazione del figlio R3
	pidR3 = fork();
	
	if(pidR3 == 0) {
		pidR3 = getpid();
		R3(argc, argv, PIPE3, msgqid, semid, shmid);
		exit(0);
	}

	// creazione e aggiuta di nodi alla lista
	list = add_node(1, pidR1);
	list = add_node(2, pidR2);
	list = add_node(3, pidR3);

	// creazione del file di uscita 
	int fd = open("OutputFiles/F9.csv", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ReceiverID;PID\n";
	write(fd, bufferW, sizeof(bufferW));

	// stampo sul file di uscita i nodi creati prima
	print_file(fd);

	semaphore_op(semid1, (unsigned short)0, 1);
	
	// chiusura del file descriptor
	if (close(fd) == -1)
        err_exit("Close failed", __FILE__, __LINE__);	

	// aspetto tutti i figli
	int stato = 0;
	while((waitPID = wait(&stato)) > 0); 

	//scrittura del distruction time di PIPE3 
	pid_t destructionPIPE3 = fork();
	if(destructionPIPE3 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("destpipe.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}
	
	//chiusura dei filedescriptor
	if (close(fdsem) == -1)
        err_exit("Close failed", __FILE__, __LINE__);
	if (close(fdsem1) == -1)
        err_exit("Close failed", __FILE__, __LINE__);

	// libero la lista
	free_node_list();

	// aspetto tutti i figli
	stato = 0;
	while((waitPID = wait(&stato)) > 0); 

	//carico da file il contenuto e lo aggiungo al buffer principale ottenendo la stringa completa
	Load_file("pipe3.txt",buffer);
	strcat(BUFFERPIPE3, buffer);
	Load_file("pipe4.txt", buffer);
	strcat(BUFFERPIPE4, buffer);
	Load_file("destpipe.txt", buffer);
	strcat(BUFFERPIPE3, buffer);
	strcat(BUFFERPIPE4, buffer);

	//elimino ";" alla fine di ogni riga
	BUFFERPIPE3[strlen(BUFFERPIPE3) - 1] = ' ';
	BUFFERPIPE4[strlen(BUFFERPIPE4) - 1] = ' ';
	
	//scrittura su F10.csv
	semaphore_op(semid10, (unsigned int) 1, -1);
	int fd10 = open("OutputFiles/F10.csv", O_WRONLY | O_CREAT | O_APPEND, S_IRUSR | S_IWUSR);
	char buffer10[BUFLEN];
	int lenght = snprintf(buffer10, STRING_ALLOC_SIZE, "%s\n%s\n", BUFFERPIPE3, BUFFERPIPE4);
	write(fd10, buffer10, lenght);

	//chiusura e rimozione del semaforo per la scrittura su F10.csv
	if(close(fdsem10) == -1)
		err_exit("Close failed", __FILE__, __LINE__);
	if (semctl(semid10, 0 , IPC_RMID, NULL) == -1)
        err_exit("semctl IPC_RMID failed", __FILE__, __LINE__);

	//scrittura del distruction time del semaforo
	pid_t destructionSEM3 = fork();
	if(destructionSEM3 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("destSEM3.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}
	
	// aspetto tutti i figli
	stato = 0;
	while((waitPID = wait(&stato)) > 0);

	//carico da file il contenuto e lo aggiungo al buffer principale ottenendo la stringa completa
	Load_file("sem3.txt", buffer);
	strcat(BUFFERSEM3, buffer);
	Load_file("destSEM3.txt", buffer);
	strcat(BUFFERSEM3, buffer);

	BUFFERSEM3[strlen(BUFFERSEM3) - 1] = ' ';

	//scrivo e chiudo il file
	write(fd10, BUFFERSEM3, strlen(BUFFERSEM3));
	close(fd10);

	//chiudo i file che sono stati utilizzati per ottenere i tempi di creazione e distruzione delle IPC
	unlink("tmp.txt");
	unlink("tmp1.txt");
	unlink("tmp2.txt");
	unlink("tmp6.txt");
	unlink("tmp7.txt");
	unlink("pipe3.txt");
	unlink("pipe4.txt");
	unlink("destpipe.txt");
	unlink("shm.txt");
	unlink("destSHM.txt");
	unlink("msgq.txt");
	unlink("destMSGQ.txt");
	unlink("sem3.txt");
	unlink("destSEM3.txt");

    return 0;
}
