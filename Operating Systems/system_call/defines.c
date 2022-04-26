/// @file defines.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche del progetto.

#include "defines.h"

int Load_file(char* inputfile, char* buf) {
    
    // apro il file 
    int fd = open(inputfile, O_RDONLY);
    if (fd == -1) { 
      printf("Open\n");
      exit(0);
    }

    // utilizzo lseek per calcolarne le dimensioni 
    int fileSize = lseek(fd, (size_t)0, SEEK_END);
    if (fileSize == -1) { 
		  printf("lseek\n");
      exit(0);
    }
    
    // riposiziono l'offset di lettura a inizio file 
    lseek(fd, (size_t)0, SEEK_SET);    

    // eseguo la lettura dal file al buffer 
    if ((read(fd, buf, fileSize) == -1)) { 
		  printf("read\n");
      exit(0); 
   	}

    return fileSize;
}