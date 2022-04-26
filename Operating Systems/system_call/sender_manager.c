/// @file sender_manager.c
/// @brief Contiene l'implementazione del sender_manager.

#include "everything.h"

int main(int argc, char * argv[]) {
	//dichiarazione variabili per il file f10
	char BUFFERPIPE1[BUFLEN];
	char BUFFERPIPE2[BUFLEN];
	char BUFFERSEM1[BUFLEN];
	char BUFFERSEM2[BUFLEN];
	char FIFO[BUFLEN];
	char SHM[BUFLEN];
	char MSGQ[BUFLEN];
	char loader[BUFLEN];

	//dichiaro le due pipe
	int PIPE1[2];
	int PIPE2[2];
	
	// dichiarazione variabili
	pid_t pidS1, pidS2, pidS3, waitPID;

	//creazione semaforo per gestire la lettura sul file F8.csv con hackler.c
	int fdsem1 = open("tmp6.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey1 = ftok("tmp6.txt", 't');
	if (semkey1 == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid1 = semget(semkey1, 1, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid1 == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}
	unsigned short semInitVal1[] = {0};
    union semun arg1;
    arg1.array = semInitVal1;
	if (semctl(semid1, 0, SETALL, arg1) == -1)
        err_exit("semctl SETALL failed", __FILE__, __LINE__);

	//creazione stringa iniziale per semaforo da scrivere su file F10.csv
	int length = snprintf(BUFFERSEM1, BUFLEN, "SEM;%02X;SM;", semkey1);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);

	pid_t sem1 = fork();
	if(sem1 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("sem1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//creazione semaforo per la gestione concorrente della scrittura su file F10.csv
	int fdsem10 = open("tmp7.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	if (fdsem10 == -1){
		err_exit("open failed", __FILE__, __LINE__);
	}
	key_t semkey10 = ftok("tmp7.txt", 'o');
	if (semkey10 == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid10 = semget(semkey10, 2, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid10 == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}
	unsigned short semInitVal10[] = {1, 0};
    union semun arg10;
    arg10.array = semInitVal10;
	if (semctl(semid10, 0, SETALL, arg10) == -1)
        err_exit("semctl SETALL failed", __FILE__, __LINE__);
	
	//creazione sharedmemory utilizzata dai processi figlio
	int fdshm = open("tmp2.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t shmKey = ftok("tmp2.txt", 'm');
	if (shmKey == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int shmid = alloc_shared_memory(shmKey, sizeof(char) * BUFLEN);

	//creazione stringa iniziale per semaforo da scrivere su file F10.csv
	length = snprintf(SHM, BUFLEN, "SH;%02X;SM;", shmKey);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t writeSHM = fork();
	if(writeSHM == 0){
		char temp3[BUFLEN];
		take_time(temp3);
		int tmp = open("shm.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp3, strlen(temp3));
		close(tmp);
		exit(0);
	}

	//creazione semaforo utilizzato dai processi figli per la scrittura su sharedmemory
	int fdsem = open("tmp1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey = ftok("tmp1.txt", 's');
	if (semkey == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid = semget(semkey, 9, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}
	unsigned short semInitVal[] = {1, 0, 0, 1, 1, 1, 1, 1, 1};
    union semun arg;
    arg.array = semInitVal;
	if (semctl(semid, 0, SETALL, arg) == -1)
        err_exit("semctl SETALL failed", __FILE__, __LINE__);

	//creazione stringa iniziale per semaforo da scrivere su file F10.csv
	length = snprintf(BUFFERSEM2, BUFLEN, "SEM;%02X;SM;", semkey);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t sem2 = fork();
	if(sem2 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("sem2.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//creazione messagequeue utilizzata dai processi figlio
	int fdmsgq = open("tmp.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t msgkey = ftok("tmp.txt", 'q');
	if (msgkey == -1)
		err_exit("ftok failed", __FILE__, __LINE__);
	int msgqid = msgget(msgkey, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (msgqid == -1){
        err_exit("msgget failed", __FILE__, __LINE__);
	}

	//creazione stringa iniziale per MESSAGEQUEUE da scrivere su file F10.csv
	length = snprintf(MSGQ, BUFLEN, "Q;%02X;SM;", msgkey);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t writeMSGQ = fork();
	if(writeMSGQ == 0){
		char time_msg[BUFLEN];
		take_time(time_msg);
		int tmp = open("msgq.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, time_msg, strlen(time_msg));
		close(tmp);
		exit(0);
	}

	//CREAZIONE PIPE
	create_pipe(PIPE1);
	create_pipe(PIPE2);

	//creazione stringa iniziale per PIPE1 da scrivere su file F10.csv
	length = snprintf(BUFFERPIPE1, BUFLEN, "PIPE1;%d/%d;SM;", PIPE1[0], PIPE1[1]);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t pipe1 = fork();
	if(pipe1 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("pipe1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//creazione stringa iniziale per PIPE2 da scrivere su file F10.csv
	length = snprintf(BUFFERPIPE2, BUFLEN, "PIPE2;%d/%d;SM;", PIPE2[0], PIPE2[1]);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t pipe2 = fork();
	if(pipe2 == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("pipe2.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//rendo la PIPE2 non bloccante in lettura
	if(fcntl(PIPE1[1], F_SETFL, O_NONBLOCK) == -1)
		err_exit("fcntl failed", __FILE__, __LINE__);

	//rendo la PIPE2 non bloccante in lettura
	if(fcntl(PIPE2[0], F_SETFL, O_NONBLOCK) == -1)
		err_exit("fcntl failed", __FILE__, __LINE__);
	
	//creazione FIFO usata solamente dal processo S3 e R3
	char fname[] = "OutputFiles/my_fifo.txt";
	create_fifo(fname);
	int fdfifo = open_write_fifo(fname);
	fcntl(fdfifo, F_SETFL, O_NDELAY);

	//creazione stringa iniziale per FIFO da scrivere su file F10.csv
	length = snprintf(FIFO, BUFLEN, "FIFO;%d;SM;", fdfifo);
	if(length == -1)
		err_exit("snprintf", __FILE__, __LINE__);
	pid_t fifo = fork();
	if(fifo == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("fifo.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//generazione del figlio S1
	pidS1 = fork();

	if(pidS1 == -1){
		printf("Child 1 not created");
		exit(1);
	}
	else if(pidS1 == 0) {
		pidS1 = getpid();
		S1(argc, argv, PIPE1, msgqid, semid, shmid);
		exit(0);
	}

	//generazione del figlio S2
	pidS2 = fork();
	
	if(pidS2 == -1){
		printf("Child 2 not created");
		exit(1);
	}
	else if(pidS2 == 0) {
		pidS2 = getpid();
		S2(argc, argv, PIPE1, PIPE2, msgqid, semid, shmid);
		exit(0);
	}

	//generazione del figlio S3
	pidS3 = fork();
	
	if(pidS3 == -1){
		printf("Child 3 not created");
		exit(1);
	}
	else if(pidS3 == 0) {
		pidS3 = getpid();
		S3(argc, argv, PIPE2, msgqid, semid, shmid, fdfifo);
		exit(0);
	}

	// creazione e aggiuta di nodi alla lista
	list = add_node(1, pidS1);
	list = add_node(2, pidS2);
	list = add_node(3, pidS3);

	//generazione del file F8.csv
	int fd = open("OutputFiles/F8.csv", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	char buffer[] = "SenderID;PID\n";
	write(fd, buffer, sizeof(buffer));
	
	// stampo sul file di uscita i nodi creati prima
	print_file(fd);

	//incrementando il semaforo indico all'hackler che può leggere dal file F8.csv
	semaphore_op(semid1, (unsigned short)0, 1);
	
	// chiusura del file descriptor
	if (close(fd) == -1)
        err_exit("Close failed", __FILE__, __LINE__);

	//aspetto tutti i figli
	int stato = 0;
	while((waitPID = wait(&stato)) > 0); 

	//scrittura del distruction time di PIPE1
	pid_t destructionPIPE = fork();
	if(destructionPIPE == 0){
		char bufferpipe1[BUFLEN];
		take_time(bufferpipe1);
		int tmp = open("destpipe1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, bufferpipe1, strlen(bufferpipe1) - 1);
		exit(0);
	}
	
	close_fifo(fdfifo, fname);

	//scrittura del distruction time di FIFO
	pid_t destructionFIFO = fork();
	if(destructionFIFO == 0){
		char buffer[BUFLEN];
		take_time(buffer);
		int tmp = open("destFIFO.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, buffer, strlen(buffer));
		exit(0);
	}

	//rimuovo il semaforo con semid
	if (semctl(semid, 0, IPC_RMID, NULL) == -1)
        err_exit("semctl IPC_RMID failed", __FILE__, __LINE__);

	//scrittura del distruction time di semaforo
	pid_t destructionSEM2 = fork();
	if(destructionSEM2 == 0){
		char buffer[BUFLEN];
		take_time(buffer);
		int tmp = open("destSEM2.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, buffer, strlen(buffer));
		exit(0);
	}

	//rimuovo semaforo con semid1
	if (semctl(semid1, 0 , IPC_RMID, NULL) == -1)
        err_exit("semctl IPC_RMID failed", __FILE__, __LINE__);

	//scrittura del distruction time di semaforo
	pid_t destructionSEM1 = fork();
	if(destructionSEM1 == 0){
		char buffer[BUFLEN];
		take_time(buffer);
		int tmp = open("destSEM1.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, buffer, strlen(buffer));
		exit(0);
	}

	//rimuovo la messagequeue
	if (msgctl(msgqid, IPC_RMID, NULL) == -1)
     	err_exit("msgctl failed", __FILE__, __LINE__);

	//scrittura del distruction time di MSGQ
	pid_t destructionMSGQ = fork();
	if(destructionMSGQ == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("destMSGQ.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	//chiudo shared memory
	if (close(fdshm) == -1)
        err_exit("Close failed", __FILE__, __LINE__);

	//rimuovo sharedmemory
	remove_shared_memory(shmid);
	
	//scrittura del distruction time di SHM
	pid_t destructionSHM = fork();
	if(destructionSHM == 0){
		char temp[BUFLEN];
		take_time(temp);
		int tmp = open("destSHM.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
		write(tmp, temp, strlen(temp));
		close(tmp);
		exit(0);
	}

	stato = 0;
	while((waitPID = wait(&stato)) > 0); 

	//carico da file il contenuto e lo aggiungo al buffer principale ottenendo la stringa completa
	Load_file("pipe1.txt",loader);
	strcat(BUFFERPIPE1, loader);
	Load_file("pipe2.txt", loader);
	strcat(BUFFERPIPE2, loader);
	Load_file("destpipe1.txt", loader);
	strcat(BUFFERPIPE1, loader);
	strcat(BUFFERPIPE2, loader);
	Load_file("sem1.txt", loader);
	strcat(BUFFERSEM1, loader);
	Load_file("destSEM1.txt", loader);
	strcat(BUFFERSEM1, loader);
	Load_file("sem2.txt", loader);
	strcat(BUFFERSEM2, loader);
	Load_file("destSEM2.txt", loader);
	strcat(BUFFERSEM2, loader);
	Load_file("fifo.txt", loader);
	strcat(FIFO, loader);
	Load_file("destFIFO.txt", loader);
	strcat(FIFO, loader);
	Load_file("shm.txt", loader);
	strcat(SHM, buffer);
	Load_file("destSHM.txt", loader);
	strcat(SHM, buffer);
	Load_file("msgq.txt", loader);
	strcat(MSGQ, buffer);
	Load_file("destMSGQ.txt", loader);
	strcat(MSGQ, buffer);

	//elimino il ";" da ogni fine di riga
	BUFFERPIPE1[strlen(BUFFERPIPE1) - 1] = ' ';
	BUFFERPIPE2[strlen(BUFFERPIPE2) - 1] = ' ';
	FIFO[strlen(FIFO) - 1] = ' ';
	BUFFERSEM1[strlen(BUFFERSEM1) - 1] = ' ';
	BUFFERSEM2[strlen(BUFFERSEM2) - 1] = ' ';

	//chiude lIPC
	if(close(fdmsgq) == -1)
		err_exit("Close failed", __FILE__, __LINE__);
	if(close(fdsem) == -1)
		err_exit("Close failed", __FILE__, __LINE__);
	if (close(fdsem1) == -1)
        err_exit("Close failed", __FILE__, __LINE__);
	if(close(fdshm) == -1)
		err_exit("Close failed", __FILE__, __LINE__);	
	
	// libero la lista
	free_node_list();

	//scrivo su F10.csv e dopo aver scritto permetto al receiver di poter scriver 
	semaphore_op(semid10, (unsigned int) 0, -1);
	int fd10 = open("OutputFiles/F10.csv", O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
	char buffer10[BUFLEN];
	char buffer_intestazione[] = "IPC;IDKey;Creator;CreatorTime;DestructionTime";
	int lenght = snprintf(buffer10, STRING_ALLOC_SIZE, "%s\n%s\n%s\n%s\n%s\n%s\n", buffer_intestazione, BUFFERPIPE1, BUFFERPIPE2,
								FIFO, BUFFERSEM1, BUFFERSEM2);
	write(fd10, buffer10, lenght);
	semaphore_op(semid10, (unsigned int) 1, 1);

	//chiudo i file che sono stati utilizzati per ottenere i tempi di creazione e distruzione delle IPC
	unlink("pipe1.txt");
	unlink("pipe2.txt");
	unlink("sem1.txt");
	unlink("sem2.txt");
	unlink("destpipe1.txt");
	unlink("fifo.txt");
	unlink("destFIFO.txt");
	unlink("destSEM1.txt");
	unlink("destSEM2.txt");
	
    return 0;
}
