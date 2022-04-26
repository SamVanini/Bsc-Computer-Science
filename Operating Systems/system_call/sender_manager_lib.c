#include "everything.h"

struct ProcessID* add_node(int ProcessID, pid_t PID){
	// creo variabili
	struct ProcessID* node;
	struct ProcessID* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct ProcessID*)malloc(sizeof(struct ProcessID));
		
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);	
	}

	// inizializzo i campi del nodo
	node->ProcessID = ProcessID;
	node->PID = PID;
	node->next = NULL;
	
	// se list è vuota ritorno il nodo 
	// senò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(list == NULL)
		return node;	
	else {
		head = list;
		while(head->next != NULL)
			head = head->next;
		head->next = node;
		
		return list;
	}
}

void write_out(struct ProcessID* process, int fd){
	int length;
	char *str = malloc(STRING_ALLOC_SIZE);

	// Scrittura del nodo nella stringa
	// ritorna la lunghezza totale per la scrittura
	length = snprintf(str, STRING_ALLOC_SIZE, "S%d;%d\n", process->ProcessID, process->PID);
	
	// controllo errore
	if (length == -1){
		 err_exit("snprintf", __FILE__, __LINE__);
	}

	//posizione in coda al file
	off_t current = lseek(fd, -1, SEEK_END);
	if(current == -1){
		 err_exit("lseek", __FILE__, __LINE__);
	}

	// Scrittura nel file
	if (write(fd, str, length) == -1){
		 err_exit("write", __FILE__, __LINE__);
	}
	
	if (write(fd, "\n", 1) == -1){
		 err_exit("write new line", __FILE__, __LINE__);
	}
	//Liberazione memoria
	free(str);
}

void print_file(int fd) {
	// itero la lista dall'inizio alla fine e stampo sul file di sucita
	while(list != NULL){
		write_out(list, fd);
		list = list->next;
	}
}

void free_node_list() {
	// itero la lista dall'inizio alla fine e libero tramite 
	// l'utilizzo di una variabile temporanea il nodo della lista
	while (list != NULL) {
        struct ProcessID *tmp = list;
        list = list->next;
		free(tmp);
	}
}
