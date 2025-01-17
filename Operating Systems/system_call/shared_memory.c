/// @file shared_memory.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche per la gestione della MEMORIA CONDIVISA.

#include "shared_memory.h"
#include "err_exit.h"
#include <sys/shm.h>
#include <sys/stat.h>

int alloc_shared_memory(key_t shmKey, size_t size) {
  int shmid = shmget(shmKey, size, IPC_CREAT | S_IRUSR | S_IWUSR | S_IRGRP);
  if (shmid == -1)
    err_exit("shmget", __FILE__, __LINE__);

  return shmid;
}

void *get_shared_memory(int shmid, int shmflg) {
  void *attach = shmat(shmid, NULL, shmflg);
  if (attach == (void *)-1)
    err_exit("shmat", __FILE__, __LINE__);

  return attach;
}

void free_shared_memory(void *ptr_sh) {
  if (shmdt(ptr_sh) == -1)
    err_exit("shmdt", __FILE__, __LINE__);
}

void remove_shared_memory(int shmid) {
  if (shmctl(shmid, IPC_RMID, NULL) == -1)
    err_exit("shmctl", __FILE__, __LINE__);
}