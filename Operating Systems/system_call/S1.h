/** @file S1.h
*	  @brief dichiarazione delle funzioni appartenenti al processo S1
*	
*	questo file contiene le funzioni del processo S1. Tale processo esegue
*	quanto segue:
*	
*	
*	  @author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef S1_H
#define S1_H

/**	struttura che rappresenta i campi di una riga 
* 	contenuti sul file
*/
struct Message {
  int message_id;
  char message[BUFF_MESSAGE_SIZE];
  char id_sender[2];
  char id_receiver[2];
  int DelS1;
  int DelS2;
  int DelS3;
  char Type[QUEUE_LENGTH];
  struct Message * next;
};

/** @brief wrapper del processo S1
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo S1. Sarà utilizzata nel processo sender_manager.
*
*	  @param argc numero parametri da linea di comando ereditati dal chiamante
*	  @param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	  @return void
*/
void S1(int argc, char* argv[], int *PIPE1, int msgqid, int semid, int shmid);

/** @brief carica il file input e ne ritorna la dimensione
*	
*	il file viene aperto in lettura. Viene utilizzata la funzione lseek
*	per calcolare la dimensione del file. L'offset di lettura viene
*	poi riposizionato per la lettura.
*	Il file viene letto (read) e il suo contenuto copiato nel buffer
*	passato come argomento.
*
*	  @param inputfile nome del file da caricare
*	  @param buf buffer su cui caricare il contenuto del file
*	  @return numero di byte che risponde alla dimensione del file
*/
int load_file(char* inputfile, char* buf);

/** @brief inizializza un nodo e lo aggiunge alla lista messages
*	
*	viene creato un nodo e inizializzato con i parametri passati dalla funzion
*	infine ritorna il puntatore all'ultimo elemento della lista messages
*
*	  @param parametri che indicano i campi della struttura Message
*	  @return puntatore ad un nodo della lista messages
*/
struct Message* Add_message(int message_id, char message[], char id_sender[], char id_receiver[], int DelS1, int DelS2, int DelS3, char Type[], int fd, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo);

/** @brief carica su una lista il contenuto del buffer
*	
*	viene iterato il buffer e salvato su di un buffer temporaneo result
*	il contenuto di ogni parola. Il contenuto di result viene poi caricato
*	sulle variabili dei campi.
*	Infine viene creato e aggiunto il nodo alla lista messages.
*
*	  @param bufferR buffer su è caricato il contenuto del file
*	  @param numero di byte che risponde alla dimensione del file
*   @param file descriptor del file di uscita 
*/
void Reader_buffer(char buffer[], int FileSize, int fd, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo);

/** @brief stampa sul file di uscita i nodi della lista messages
*	
*	viene ricavato l'orario di ricezione dei messaggi che verrà poi stampato sul file
*	Tramite un while, si itera la lista fino alla fine e nel mentre viene caricato 
*	su un puntatore a char il contenuto di ciascun nodo che successivamente verrà
*	stampato sul file di uscita
*
*	  @param file descriptor del file di uscita 
*/
void Compile_file(int fd, struct Message* msg, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo);

/** @brief tramite una free libera dalla memoria il nodo della lista
*	
*	tramite un ciclo while viene iterata la lista finchè punta a null
*	e nel mentre viene liberata dalla memoria il nodo della lista con una free
*
*	  @param puntatore ad un nodo della lista
*/
void free_list(int sender);

/** @brief ricava il tempo di invio del messaggio e lo stampa su file e scrive su PIPE
*	
*	tramite take_time calcola il tempo ritardato e lo scrive sul file di output inoltre 
*	carica il messaggio da scrivere su di un buffer che poi verrà utilizzato per
*	scrivere su PIPE, ma solamente dai receiver 2,3
*
*	  @param msg è un puntatore a lista che contiene il messaggio
*   @param PIPE è il puntatore alla PIPE
*	  @param sender indica il numero del sender
*   @param msgqid filedescriptor della messagequeue
*   @param semid filedescriptor del semaforo
*   @param shmid filedescriptor della sharedmemory
*   @param fdfifo filedescriptor della fifo
*   @param fd filedescriptor del file di output
*	  @param row è un puntatore alla stringa che contiene la parte iniziale di messaggio da stampare sul file
*/
void send_message(struct Message* msg, int* PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo, int fd, char* row);

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageS1();

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageS2();

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageS3();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageS1();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageS2();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageS3();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayS1();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayS2();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayS3();

/** @brief creazione nodo che viene aggiunto alla lista s1
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista s1
*/
struct Pid* add_pids1();

/** @brief creazione nodo che viene aggiunto alla lista s2
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista s2
*/
struct Pid* add_pids2();

/** @brief creazione nodo che viene aggiunto alla lista s3
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista s3
*/
struct Pid* add_pids3();

/** @brief sistemo il file di uscita correggendo e indentando correttamente il testo
* 
*	@param path nome del file di output
*/
void debug(char *path);

#endif
