/// @file pipe.h
/// @brief Contiene la definizioni di variabili e
///         funzioni specifiche per la gestione delle PIPE.

#pragma once


/** @brief creazione della PIPE
* 
*	@param PIPE puntatore ad un array di due elementi che punteranno ai filedescriptor per la lettura e scrittura
*/
void create_pipe(int *PIPE);

/** @brief chiusura lato scrittura della PIPE
*
*	@param PIPE puntatore ad un array di due elementi che punteranno ai filedescriptor per la lettura e scrittura
*/
void close_wr_end_pipe(int *PIPE);

/** @brief chiusura lato lettura della PIPE
*
*	@param PIPE puntatore ad un array di due elementi che punteranno ai filedescriptor per la lettura e scrittura
*/
void close_rd_end_pipe(int *PIPE);

/** @brief scrittura su PIPE 
* 
*	scrive su PIPE il contenuto di buffer 
*
*	@param PIPE puntatore ad un array di due elementi che punteranno ai filedescriptor per la lettura e scrittura
*	@param buffer contiente il contenuto del messaggio da caricare su buffer
*/
void write_pipe(int *PIPE, char buffer[]);

/** @brief lettura della PIPE
*	
*	viene letto il contenuto della PIPE e salvato il contenuto in buffer
* 	successivamente viene caricato in nBys la lunghezza della stringa
*	caricata in buffer e in fine ritornata
*
*	@param PIPE puntatore ad un array di due elementi che punteranno ai filedescriptor per la lettura e scrittura
*	@param buffer in cui viene caricato il contenuto della
*	@return nBys ovvero la lunghezza del contenuto di buffer 
*/
int read_pipe(int *PIPE, char buffer[]);
