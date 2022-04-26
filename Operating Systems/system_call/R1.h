/** @file R1.h
	@brief dichiarazione delle funzioni appartenenti al processo R1
*	
*	questo file contiene le funzioni del processo R1. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef R1_H
#define R1_H

/** @brief wrapper del processo R1
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo R1. Sarà utilizzata nel processo receiver_manager.
*
*	@param argc numero parametri da linea di comando ereditati dal chiamante
*	@param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	@return void
*/
void R1(int argc, char* argv[], int *PIPE4, int msgqid, int semid, int shmid);

/** @brief carica il file input e ne ritorna la dimensione
*	
*	il file viene aperto in lettura. Viene utilizzata la funzione lseek
*	per calcolare la dimensione del file. L'offset di lettura viene
*	poi riposizionato per la lettura.
*	Il file viene letto (read) e il suo contenuto copiato nel buffer
*	passato come argomento.
*
*	@param inputfile nome del file da caricare
*	@param buf buffer su cui caricare il contenuto del file
*	@return numero di byte che risponde alla dimensione del file
*/
int car_file(char* inputfile, char* buf);

/** @brief inizializza un nodo e lo aggiunge alla lista messages
*	
*	viene creato un nodo e inizializzato con i parametri passati dalla funzion
*	infine ritorna il puntatore all'ultimo elemento della lista messages
*
*	@param parametri che indicano i campi della struttura Message
*	@return puntatore ad un nodo della lista messages
*/
struct Message* add_message(int message_id, char message[], char id_sender[], char id_receiver[], int DelS1, int DelS2, int DelS3, char Type[], int fd, int *PIPE, int receiver, int semid);

/** @brief carica su una lista il contenuto del buffer
*	
*	viene iterato il buffer e salvato su di un buffer temporaneo result
*	il contenuto di ogni parola. Il contenuto di result viene poi caricato
*	sulle variabili dei campi.
*	Infine viene creato e aggiunto il nodo alla lista messages.
*
*	@param bufferR buffer su è caricato il contenuto del file
*	@param numero di byte che risponde alla dimensione del file
*   @param file descriptor del file di uscita 
*/
void reader_buffer(char buffer[], int FileSize, int fd, int *PIPE, int receiver, int semid);

/** @brief stampa sul file di uscita i nodi della lista messages
*	
*	viene ricavato l'orario di ricezione dei messaggi che verrà poi stampato sul file
*	Tramite un while, si itera la lista fino alla fine e nel mentre viene caricato 
*	su un puntatore a char il contenuto di ciascun nodo che successivamente verrà
*	stampato sul file di uscita
*
*	@param file descriptor del file di uscita 
*/
void compile_file(int fd, struct Message* msg, int *PIPE, int receiver, int semid);

/** @brief tramite una free libera dalla memoria il nodo della lista
*	
*	tramite un ciclo while viene iterata la lista finchè punta a null
*	e nel mentre viene liberata dalla memoria il nodo della lista con una free
*
*	@param puntatore ad un nodo della lista
*/
void free_list(int receiver);

/** @brief ricava il tempo di invio del messaggio e lo stampa su file e scrive su PIPE
*	
*	tramite take_time calcola il tempo ritardato e lo scrive sul file di output inoltre 
*	carica il messaggio da scrivere su di un buffer che poi verrà utilizzato per
*	scrivere su PIPE, ma solamente dai receiver 2,3
*
*	@param fd è il filedescriptor del file su cui si andrà a scrivere
*	@param msg è un puntatore a lista che contiene il messaggio
*	@param PIPE è il puntatore alla PIPE
*	@param receiver indica il numero del receiver
*	@param row è un puntatore alla stringa che contiene la parte iniziale di messaggio da stampare sul file
*/
void Send_message(int fd, struct Message* msg, int* PIPE, int receiver, char* row, int semid);

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageR1();

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageR2();

/** @brief invio segnale per la rimozione del messaggio
*	
*/
void remove_messageR3();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageR1();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageR2();

/** @brief invio segnale che direttamente elimina l'attesa del messaggio in attesa
* 
*/
void send_messageR3();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayR1();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayR2();

/** @brief invio segnale che incrementa l'attesa dei messaggi ancora da inviare
* 
*/
void increase_delayR3();

/** @brief creazione nodo che viene aggiunto alla lista r1
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista r1
*/
struct Pid* add_pidr1(pid_t pid);

/** @brief creazione nodo che viene aggiunto alla lista r2
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista r2
*/
struct Pid* add_pidr2(pid_t pid);

/** @brief creazione nodo che viene aggiunto alla lista r3
* 
*	la funzione prende come parametro il pid del processo creato per l'invio del messaggio
*	e aggiungere il pid alla lista	
*
*	@param pid corrisponde al pid creato nella compile_file
*	@return il puntatore alla lista r3
*/
struct Pid* add_pidr3(pid_t pid);

/** @brief sistemo il file di uscita correggendo e indentando correttamente il testo
* 
*	@param path nome del file di output
*/
void debig(char* path);

#endif
