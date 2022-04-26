/// @file fifo.h
/// @brief Contiene la definizioni di variabili e
///         funzioni specifiche per la gestione delle FIFO.

#pragma once

/** @brief crea un file FIFO speciale con il nome fname
*	
*	@param fname è il nome della FIFO
*/
void create_fifo(char *fname);

/** @brief apre in lettura la FIFO
*	
*	tramite la open viene aperto il file fname e creato il file
*	descriptor, il file descriptor viene poi controllato e verificato che sia andato
* 	a buon fine
*
*	@param fname è il nome della FIFO
*	@return file descriptor per la lettura della FIFO
*/
int open_read_fifo(char *fname);

/** @brief apre in scrittura la FIFO
*	
*	tramite la open viene aperto il file fname e creato il file
*	descriptor, il file descriptor viene poi controllato e verificato che sia andato
* 	a buon fine
*
*	@param fname è il nome della FIFO
*	@return file descriptor per la scrittura della FIFO
*/
int open_write_fifo(char *fname);

/** @brief scrittura sulla FIFO
*	
*	tramite la write viene caricato sulla FIFO il contenuto di 
*	buffer e controllato che sia andato a buon fine tramite il
*	confronto del valore della write con -1
*
*	@param fdfifo è il file descriptor della FIFo
*	@param buffer all'interno è contenuto il messaggio da stampare nella FIFO
*/
void write_fifo(int fdfifo, char buffer[]);

/** @brief lettura sulla FIFO
*	
*	viene letto il contenuto della fifo e salvato il contenuto in buffer
* 	successivamente viene caricato in nBys la lunghezza della stringa
*	caricata in buffer e in fine ritornata
*
*	@param fdfifo è il filedescriptor della FIFO 
*	@param buffer in cui viene caricato il contenuto della
*	@return nBys ovvero la lunghezza del contenuto di buffer 
*/
int read_fifo(int fdfifo, char buffer[]);

/** @brief chiusura della FIFO
*	
*	viene chiuso prima il filedescriptor della FIFO e poi tramite 
*	l'unlink viene eliminato il file usato dalla FIFO
*
*	@param fdfifo è il filedescriptor della FIFO 
*	@param fname è il nome della FIFO
*/
void close_fifo(int fdfifo, char *fname);