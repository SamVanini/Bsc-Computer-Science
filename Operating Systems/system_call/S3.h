/** @file S3.h
	@brief dichiarazione delle funzioni appartenenti al processo S3
*	
*	questo file contiene le funzioni del processo S3. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef S3_H
#define S3_H

/** @brief wrapper del processo S3
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo R1. Sarà utilizzata nel processo sender_manager.
*
*	@param argc numero parametri da linea di comando ereditati dal chiamante
*	@param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	@return void
*/
void S3(int argc, char* argv[], int *PIPE2, int msgqid, int semid, int shmid, int fdfifo);

#endif
