/// @file pipe.c
/// @brief Contiene l'implementazione delle funzioni
///         specifiche per la gestione del time.

#include "err_exit.h"
#include "time.h"
#include "everything.h"

void take_time(char buffer[]){

    struct tm* newtime;
	time_t tempo;
	
	time_t hours;
	time_t minutes;
	time_t seconds;

	// tramite la funzione time
	// la variabile tempo conterrà esattamente 
	// i secondi trascorsi dal 1 gennaio 1970.
	// mentre localtime ritorna un puntatore alla struttura
	// tm con le informazioni correttamente suddivise
	time(&tempo);
	newtime = localtime(&tempo);

	// adesso carichiamo sulle variabili predefinite
	// i valori, ora, minuti e secondi accedendo alla struttura tm
	hours = newtime->tm_hour;
	minutes =  newtime->tm_min;
	seconds = newtime->tm_sec;

    int length = snprintf(buffer, STRING_ALLOC_SIZE, "%s%li:%s%li:%s%li;", hours < 10 ? "0" : "",
							 hours, minutes < 10 ? "0" : "", minutes, seconds < 10 ? "0" : "", seconds);

    if(length == -1)
        err_exit("snprintf", __FILE__, __LINE__);
}