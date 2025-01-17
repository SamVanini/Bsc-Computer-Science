/// @file semaphore.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche per la gestione dei SEMAFORI.

#include "semaphore.h"
#include "err_exit.h"
#include <sys/sem.h>
#include <errno.h>
#include <stdlib.h>

void semaphore_op(int semid, unsigned short sem_num, short sem_op) {
  struct sembuf sop = {sem_num, sem_op, 0};

  if ((semop(semid, &sop, 1) == -1) && errno != EINTR && errno != EIDRM)
    err_exit("semop", __FILE__, __LINE__);
}
