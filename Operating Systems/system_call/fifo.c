/// @file fifo.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche per la gestione delle FIFO.

#include "err_exit.h"
#include "fifo.h"
#include "everything.h"

void create_fifo(char* fname){
    if(mkfifo(fname, S_IRUSR | S_IWUSR | S_IXUSR | S_IRWXG | S_IRWXO) == -1)
		err_exit("mkfifo", __FILE__, __LINE__);
}

int open_read_fifo(char* fname){
    int fdfifo = open(fname, O_RDONLY);
	
    if(fdfifo == -1)
		err_exit("read FIFO", __FILE__, __LINE__);

    return fdfifo;
}

int open_write_fifo(char* fname){
    int fdfifo = open(fname, O_WRONLY);
	
    if(fdfifo == -1)
		err_exit("write FIFO", __FILE__, __LINE__);

    return fdfifo;
}

void write_fifo(int fdfifo, char buffer[]){
	if(write(fdfifo, buffer, strlen(buffer)) == -1)
    	err_exit("write FIFO", __FILE__, __LINE__);
}

int read_fifo(int fdfifo, char buffer[]){
	int nBys;
	nBys = read(fdfifo, buffer, BUFLEN);

	return nBys;
}

void close_fifo(int fdfifo, char* fname){
    if(close(fdfifo) == -1)
		err_exit("close", __FILE__, __LINE__);

	if(unlink(fname) == -1)
		err_exit("unlink", __FILE__, __LINE__);
}
