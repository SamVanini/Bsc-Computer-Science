/** @file R2.h
	@brief dichiarazione delle funzioni appartenenti al processo R2
*	
*	questo file contiene le funzioni del processo R2. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef R2_H
#define R2_H

/** @brief wrapper del processo R2
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo R1. Sarà utilizzata nel processo receiver_manager.
*
*	@param argc numero parametri da linea di comando ereditati dal chiamante
*	@param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	@return void
*/
void R2(int argc, char* argv[], int *PIPE3, int *PIPE4, int msgqid, int semid, int shmid);

#endif
