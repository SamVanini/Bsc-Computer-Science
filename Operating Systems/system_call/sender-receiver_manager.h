#ifndef SENDER_H
#define SENDER_H

#define STRING_ALLOC_SIZE 1024

/**	struttura che contiene
*	il process id di un processo
*/
struct ProcessID{
	int ProcessID;
	pid_t PID;
	struct ProcessID* next;
};

struct msg {
    long mtype;
    char description [BUFLEN];
};

struct Pid{
	int pid;
	struct Pid *next;
};

/**
*	dichiarazione di una lista list globale
*/
struct ProcessID* list;

/** @brief crea un nodo e lo aggiunge alla lista
*	
*	viene creato un nodo e inizializzato con gli opportuni
*	parametri. Successivamente viene ritornato l'ultimo il puntatore all'ultimo
*	elemento della lista
*
*	@param puntatore ad una nodo della lista
*	@param pid del processo
*/
struct ProcessID* add_node(int ProcessID, pid_t PID);

/** @brief stampa sul file il nodo processo
*	
*	viene misurato tramite una snprintf la lunghezza della stringa da stampare
*	e salvato su un puntatore a char, str, il contenuto del nodo.
*	Tramite lseek si posiziona il puntatore in coda al file
*	infine viene stampato sul file il contenuto di str
*
*	@param puntatore ad una nodo della lista
*	@param file descriptor 
*/
void write_out(struct ProcessID* process, int fd);

/** @brief stampa sul file il nodo processo
*	
*	tramite un ciclo while viene iterata la lista finchè punta a null
*	e nel mentre viene invocata la funzione writer_out che stampa sul file
*	il contenuto del nodo della lista
*
*	@param puntatore ad una nodo della lista
*	@param file descriptor 
*/
void print_file(int fd);

/** @brief tramite una free libera dalla memoria il nodo della lista
*	
*	tramite un ciclo while viene iterata la lista finchè punta a null
*	e nel mentre viene liberata dalla memoria il nodo della lista con una free
*
*	@param puntatore ad un nodo della lista
*/
void free_node_list();

#endif
