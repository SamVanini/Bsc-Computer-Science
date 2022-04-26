/** @file S2.h
	@brief dichiarazione delle funzioni appartenenti al processo S2
*	
*	questo file contiene le funzioni del processo S2. Tale processo esegue
*	quanto segue:
*	
*	
*	@author Samantha Vanini, Elena Zorer, Marco Massagrande
*/
#ifndef S2_H
#define S2_H

/** @brief wrapper del processo S2
*	
*	funzione utilizzata per richiamare l'interezza del filone esecutivo
*	del processo R1. Sarà utilizzata nel processo sender_manager.
*
*	@param argc numero parametri da linea di comando ereditati dal chiamante
*	@param argv[] specifica parametri da linea di comando ereditati dal chiamante
*	@return void
*/
void S2(int argc, char* argv[], int *PIPE1, int *PIPE2, int msgqid, int semdid, int shmid);

#endif
