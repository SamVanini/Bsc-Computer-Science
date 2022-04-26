/// @file semaphore.h
/// @brief Contiene la definizioni di variabili e funzioni
///         specifiche per la gestione dei SEMAFORI.

#pragma once

union semun {
  int val;
  struct semid_ds *buf;
  unsigned short *array;
};

/** @brief esegue l'operazione sem_op al ssemaforo sem_num
*	
*
*	  @param semid filedescriptor del semaforo
*	  @param sem_num indica il semaforo a cui eseguire un operazione
*	  @param sem_op operazione da eseguire
*/
void semaphore_op(int semid, unsigned short sem_num, short sem_op);
