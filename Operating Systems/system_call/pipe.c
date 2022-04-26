/// @file pipe.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche per la gestione delle PIPE.

#include "err_exit.h"
#include "pipe.h"
#include "everything.h"
#include <errno.h>

void create_pipe(int* PIPE){
    if(pipe(PIPE))
        err_exit("PIPE", __FILE__, __LINE__);
}

void close_wr_end_pipe(int* PIPE){
    if(close(PIPE[1]) == -1)
    	err_exit("close PIPE[1]", __FILE__, __LINE__);
}

void close_rd_end_pipe(int* PIPE){
    if(close(PIPE[0]) == -1)
    	err_exit("close PIPE[0]", __FILE__, __LINE__);
}

void write_pipe(int* PIPE, char buffer[]){
	if(write(PIPE[1], buffer, strlen(buffer)) == -1)
    	err_exit("write PIPE", __FILE__, __LINE__);
}

int read_pipe(int* PIPE, char buffer[]){
	int nBys;

	nBys = read(PIPE[0], buffer, BUFLEN);
	if((nBys == 1) && (errno != EAGAIN))
		err_exit("read PIPE", __FILE__, __LINE__);
	
	return nBys;
}
