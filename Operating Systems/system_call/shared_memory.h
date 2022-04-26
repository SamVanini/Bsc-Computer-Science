/// @file shared_memory.h
/// @brief Contiene la definizioni di variabili e funzioni
///         specifiche per la gestione della MEMORIA CONDIVISA.

#pragma once

#include <stdlib.h>

/** @brief prende se esiste, o crea un segmento di shared memori
*	
*	@param shmkey è la chiave per ottenere la memoria condivisa
*   @param size grandezza della shared memory
*   @return il file descriptor della sharedmemory
*/
int alloc_shared_memory(key_t shmKey, size_t size);

/** @brief attacca il segmento di memoria condivisa
*	
*	@param shmid il file descriptor della sharedmemory
*   @param shmflg flag
*/
void *get_shared_memory(int shmid, int shmflg);

/** @brief stacca il puntatore alla memoria condivisa
*	
*	@param ptr_sh è il puntatore alla shared memory
*/
void free_shared_memory(void* ptr_sh);

/** @brief elimina il segmento di memoria condivisa
*	
*	@param shmid il file descriptor della sharedmemory
*/
void remove_shared_memory(int shmid);
