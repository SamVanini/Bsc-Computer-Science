/// @file defines.h
/// @brief Contiene la definizioni di variabili
///         e funzioni specifiche del progetto.
#pragma once

#ifndef DEFINES_H
#define DEFINES_H

#include <stdint.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <string.h>
#include <stdarg.h>
#include <errno.h>
#include <time.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/dir.h>
#include <dirent.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/sem.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <sys/time.h>

#define BUFF_MESSAGE_SIZE 50
#define BUFLEN 4000
#define QUEUE_LENGTH 10

/** @brief carica il file input e ne ritorna la dimensione
*	
*	il file viene aperto in lettura. Viene utilizzata la funzione lseek
*	per calcolare la dimensione del file. L'offset di lettura viene
*	poi riposizionato per la lettura.
*	Il file viene letto (read) e il suo contenuto copiato nel buffer
*	passato come argomento.
*
*	@param inputfile nome del file da caricare
*	@param buf buffer su cui caricare il contenuto del file
*	@return numero di byte che risponde alla dimensione del file
*/
int Load_file(char* inputfile, char* buf);

#endif
