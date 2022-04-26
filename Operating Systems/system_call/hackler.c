/// @file client.c
/// @brief Contiene l'implementazione del client.

#include "everything.h"

int main(int argc, char * argv[]) {
	char bufferR[BUFLEN]; // buffer su cui andrà caricato il contenuto del file contenuto in argv[1]
	char buffer[BUFLEN];

	//creazione di un semaforo per l'utilizzo concorrente dei file di output
	int fdsem = open("tmp6.txt", O_WRONLY | O_CREAT | O_TRUNC , S_IRUSR | S_IWUSR);
	key_t semkey = ftok("tmp6.txt", 't');
	if (semkey == -1){
		err_exit("ftok failed", __FILE__, __LINE__);
	}
	int semid = semget(semkey, 1, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semid == -1){
        err_exit("semget failed", __FILE__, __LINE__);
	}

	//attendo tramite semaforo che sender_manager e receiver_manager abbiano terminato di scrivere i pid dei processi figli sui file
	semaphore_op(semid, (unsigned short)0, -2);

	//carica su buffer il contenuto dei file e tramite la funzione getPid aggiungo i pid ad una lista
	int lunghezza = carica_file("OutputFiles/F8.csv", buffer);
	getPid(buffer, lunghezza);

	lunghezza = carica_file("OutputFiles/F9.csv", buffer);
	getPid(buffer, lunghezza);
	
	// salvataggio della lunghezza in byte del file in argv[1]
	int filesize = carica_file(argv[1], bufferR);

	// caricamento del contenuto di bufferR sull'array di strutture
	copy_buffer(bufferR, filesize); 
	
	//creazione del file di uscita 
	/*int fd = open ("OutputFiles/F7_out.csv", O_WRONLY | O_CREAT | O_TRUNC, S_IRWXU);
	
	// scrivo su bufferW l'intestazione e poi la stampo sul file
	char bufferW[] = "ID;Delay;Target;Action\n";
	write(fd, bufferW, sizeof(bufferW) - 1);
	
	// stampo sul file di uscita il file letto al contrario
	revers_file(fd);

	// chiusura del file descriptor
	if (close(fd) == -1)
        err_exit("Close failed", __FILE__, __LINE__);*/

	// chiusura del file descriptor
	if (close(fdsem) == -1)
        err_exit("Close failed", __FILE__, __LINE__);
	
    return 0;
}
