/** @file hackler.h
	@brief dichiarazione delle funzioni appartenenti al processo hackler
*	
*	questo file contiene le funzioni del processo hackler. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef HACKLER_H
#define HACKLER_H

#define N 20
#define M 1000

/**	struttura che rappresenta i campi di una riga 
* 	contenuti sul file
*/
struct filef7_out {
	int Id;
	int Delay;
	char Target[N];
	char Action[N];

} typedef f7_out1;

struct List{
	char id_Target[N];
	int pid;
	struct List *next;
};

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
int carica_file(char* inputfile, char* buf);

/** @brief carica sull'array di strutture il contenuto del buffer
*	
*	viene iterato il buffer e salvato su di un buffer temporaneo result
*	il contenuto di ogni parola. Il contenuto di result viene poi caricato
*	sulla struttura.
*
*	@param bufferR buffer su è caricato il contenuto del file
*	@param numero di byte che risponde alla dimensione del file
*/
void copy_buffer(char bufferR[], int FileSize);


/** @brief stampa sul file di uscita le righe della struttura al contrario
*	
*	tramite un for viene iterato l'array di strutture al contrario.
*	Viene caricato su di un buffer temporaneo il contenuto di una struttura alla volta
*	infine viene stampato tramite una write il contenuto di result
*	sul file di uscita
*
*	@param file descriptor del file di uscita 
*/
void revers_file(int fd);

/** @brief invia sengali ai processi 
*	
*	Viene verificata l'azione che deve essere eseguita dal segnale e poi tramite
*	l'iterazione della lista dei pid viene inviato il segnale al processo 
*	con il determinato pid
*
*	@param Id identificatore del messaggio
*	@param Delay contiente la lunghezza della attesa da fare prima di inviare il sengale
*	@param Target contiente il nome del processo che deve ricevere il segnale
*	@param Action contiente l'azione che deve fare il segnale
*/
void send_signal(int Id, int Delay, char* Target, char* Action);

/** @brief ottiene dai file F8.csv e F9.csv il pid dei processi
* 
*	viene iterato il buffer e salvato su di un buffer temporaneo result
*	il contenuto di ogni parola. Il contenuto di result viene poi caricato
*	sulle variabili dei campi.
*	Infine viene creato e aggiunto il nodo alla lista List.
*
*	@param buffer contiente il contenuto del file
*	@param filesize è la lunghezza del contenuto del buffer
*/
void getPid(char buffer[], int filesize);

/** @brief creazione del nodo della lista List
*
*	@param id_Target è il campo che indica il bersaglio del segnale da inviare
*	@param pid è il valore del pid del Target
*/
struct List* add_list(char id_Target[], pid_t pid);

#endif
