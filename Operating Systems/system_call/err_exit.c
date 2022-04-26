/// @file err_exit.c
/// @brief Contiene l'implementazione della funzione di stampa degli errori.

#include "everything.h"

void ErrExit(const char *msg) {
    perror(msg);
    exit(1);
}

void err_exit(const char *msg, char *file, int line) {
  printf("Error in file \"%s\" at line %d\n", file, line);
  perror(msg);
  exit(1);
}
