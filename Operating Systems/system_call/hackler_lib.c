#include "everything.h"

f7_out1 f1[N];
int riga;
struct List *testa = NULL;

int carica_file(char* inputfile, char* buf) {

    // apro il file
    int fd = open(inputfile, O_RDONLY);
    if (fd == -1) { 
		err_exit("Open", __FILE__, __LINE__);	     
     }

    // utilizzo lseek per calcolarne le dimensioni
    int fileSize = lseek(fd, (size_t)0, SEEK_END);
    if (fileSize == -1) { 
		err_exit("lseek", __FILE__, __LINE__);	 
    }
    
    // riposiziono l'offset di lettura a inizio file //
    lseek(fd, (size_t)0, SEEK_SET);    

    // eseguo la lettura dal file al buffer
    if ((read(fd, buf, fileSize) == -1)) { 
		err_exit("read", __FILE__, __LINE__);	 
   	}
   	
    return fileSize;
}

void copy_buffer(char bufferR[], int FileSize) {
	int j = 0;  // contatore che indica l'indice del buffer temporaneo result
	int campo = 0;  // indicatore del campo nella struttura
	int i;  // contatore che indica l'indice del buffer su cui è salvato il file
	riga = 0; // contatore del numero di righe presenti nel file e del numero di strutture nell'array
	char result[M]; // buffer temporaneo per il salvataggio dei caratteri

    // itero sul bufferR e salvo nel buffer temporaneo, result, gli elementi
	for(i = 0 ; i < FileSize ; i++) {
		if(bufferR[i] != ';' && bufferR[i] != '\n') {
			result[j] = bufferR[i];
			j++;
		}
		
		// se ho terminato la lettura di un campo nella stessa riga 
		// salvo sulla struttura il buffer result
		// gestendo la posizione del salvataggio con il contatore campo
		if (riga != 0){		
			if (bufferR[i] == ';' || bufferR[i] == '\n') { 
				result[j] = '\0';
				
				switch (campo) {
					case 0:
						f1[riga].Id = atoi(result);
						break;				
					case 1:
						f1[riga].Delay = atoi(result);
						break;
					case 2:
						strcpy(f1[riga].Target, result);
						break;
					case 3:
						strcpy(f1[riga].Action, result);
						break;
					default:
						break;
	
				}
				
				// dopo aver copiato un campo azzero j per poter salvarne un altro
				// sul buffer result
				// incremento campo per salvare la parola nel campo successivo
				// e svuoto il buffer result
				campo++;
				j = 0;
				strcpy(result, "");
			}

			if(bufferR[i] == '\n'){
				send_signal(f1[riga].Id, f1[riga].Delay, f1[riga].Target, f1[riga].Action);
			}
		}
		
		// se nel buffer c'è il carattere speciale newline 
		// significa che siamo giunti alla fine della riga
		// si procede azzerando i contatori e result 
		// e incrementando la riga
		if (bufferR[i] == '\n') {
				strcpy(result, "");
				campo = 0;
				riga++;
				j = 0;		
		}
	}
	
	if(bufferR[FileSize - 1] == 'n'){
		strcpy(f1[riga].Action, "ShutDown");
		send_signal(f1[riga].Id, f1[riga].Delay, f1[riga].Target, f1[riga].Action);
	}
}

void revers_file(int fd) {
	char result[M]; // buffer temporaneo per il salvataggio dei caratteri
	
	// copio sul buffer result le righe del file al contrario
	// che ho precedentemente salvato su di un array di strutture
	// tramite una write scrivo su file di uscita 
	for(int reverse = riga - 1 ; reverse > 0 ; reverse--) {
		sprintf(result,"%i;%i;%s;%s\n", f1[reverse].Id, f1[reverse].Delay, f1[reverse].Target, f1[reverse].Action);
		write(fd, result, strlen(result));		
	}
}

void send_signal(int Id, int Delay, char *Target, char *Action){
	pid_t pid;
	struct List* curr = testa; //puntatore alla lista in cui sono contenuti i pid dei processi a cui inviare i segnali

	//tramite dei controlli si verifica il tipo di azione che il segnale deve compiere
	//trovata l'azione richiesta si cicla la lista di pid finchè si trova il pid corretto 
	//e infine si invia il segnale tramite la kill
	if(strcmp(Action, "ShutDown") == 0){
		sleep(Delay);
		while(curr != NULL){
			kill(curr->pid, SIGTERM);
			curr = curr->next;
		}
	}else if(strcmp(Action, "IncreaseDelay") == 0){
		while(curr != NULL){
			if(strcmp(Target, curr->id_Target) == 0){
				pid = fork();
				if(pid == 0){
					sleep(Delay);
					kill(curr->pid, SIGUSR1);
					exit(0);
				}
			}
			curr = curr->next;
		}
	}else if(strcmp(Action, "RemoveMSG") == 0 || strcmp(Action, "RemoveMsg") == 0){
		while(curr != NULL){
			if(strcmp(Target, curr->id_Target) == 0){
				pid = fork();
				if(pid == 0){
					sleep(Delay);
					kill(curr->pid, SIGUSR2);
					exit(0);
				}
			}
			curr = curr->next;
		}
	}else if(strcmp(Action, "SendMSG") == 0 || strcmp(Action, "SendMsg") == 0){
		while(curr != NULL){
			if(strcmp(Target, curr->id_Target) == 0){
				pid = fork();
				if(pid == 0){
					sleep(Delay);
					kill(curr->pid, SIGQUIT);
					exit(0);
				}
			}
			curr = curr->next;
		}	
	}
}

void getPid(char buffer[], int filesize){
	char result[BUFLEN]; //buffer su cui viene caricato temporaneamente il valore dei campi 
	int riga = 0; //identifica il numreo della riga sul file
	int i = 0; //indice per il scorrimento dei buffer
	int j = 0; //...
	int campo = 0; //un contatore che indica il campo in cui ci si trova
	char id_Target[3]; //campi da memorizzare
	int pid; //...

	// itero sul buffer e salvo nel buffer temporaneo, result, gli elementi
	for(i = 0 ; i < filesize ; i++){
		if(buffer[i] != ';' && buffer[i] != '\n') {
			result[j] = buffer[i];
			j++;
		}

		// se ho terminato la lettura di un campo nella stessa riga 
		// salvo sulla variabile corretta il buffer result
		// gestendo la posizione del salvataggio con il contatore campo
		if (riga != 0){		
			if (buffer[i] == ';' || buffer[i] == '\n') { 
				result[j] = '\0';
				
				switch (campo) {
					case 0:
						strcpy(id_Target,result);
						break;				
					case 1:
						pid = atoi(result);
						break;
					default:
						break;
				}

				// dopo aver copiato un campo azzero j per poter salvarne un altro
				// sul buffer result
				// incremento campo per salvare la parola nel campo successivo
				// e svuoto il buffer result
				campo++;
				j = 0;
				strcpy(result, "");
			}

			//aggiungo il pid e l'azione alla lista
			if (buffer[i] =='\n' && strcmp(id_Target, "") != 0){
				testa = add_list(id_Target, pid);
			}
		}

		// se nel buffer c'è il carattere speciale newline 
		// significa che siamo giunti alla fine della riga
		// si procede azzerando i contatori
		// e incrementando la riga
		if (buffer[i] == '\n') {
			strcpy(result, "");
			campo = 0;
			riga++;
			j = 0;
		}
	}
}

struct List* add_list(char id_Target[], pid_t pid){
	struct List* node;
	struct List* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct List*)malloc(sizeof(struct List));
	
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);
	}

	// inizializzo i campi del nodo
	strcpy(node->id_Target, id_Target);
	node->pid = pid;
	node->next = NULL;

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(testa == NULL)
		return node;
	else{
		head = testa;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return testa;
	}
}
