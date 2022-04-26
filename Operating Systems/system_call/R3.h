/** @file R3.h
	@brief dichiarazione delle funzioni appartenenti al processo R3
*	
*	questo file contiene le funzioni del processo R3. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef R3_H
#define R3_H

/** @brief wrapper del processo R3
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo R1. Sarà utilizzata nel processo receiver_manager.
*
*	@param argc numero parametri da linea di comando ereditati dal chiamante
*	@param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	@return void
*/
void R3(int argc, char* argv[], int *PIPE3, int msgqid, int semid, int shmid);

#endif
