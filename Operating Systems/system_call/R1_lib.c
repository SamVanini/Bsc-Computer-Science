#include "everything.h"

// definisco un puntatore ad una lista e lo inizializzo a null
struct Message* messagesR1 = NULL;
struct Message* messagesR2 = NULL;
struct Message* messagesR3 = NULL;
struct Pid* r1 = NULL;
struct Pid* r2 = NULL;
struct Pid* r3 = NULL;

int car_file(char* inputfile, char* buf) {
    
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
    
    // riposiziono l'offset di lettura a inizio file 
    lseek(fd, (size_t)0, SEEK_SET);    

    // eseguo la lettura dal file al buffer 
    if ((read(fd, buf, fileSize) == -1)) { 
		err_exit("read", __FILE__, __LINE__); 
   	}

    return fileSize;
}

void reader_buffer(char buffer[], int FileSize, int fd, int *PIPE, int receiver, int semid){

	int campo = 0;  // indicatore del campo nella struttura
	int i;  // contatore che indica l'indice del buffer su cui è salvato il file
	int j = 0;  // contatore che indica l'indice del buffer temporaneo result
	int riga = 1; // contatore del numero di righe presenti nel file e del numero di nodi nella lista
	
	// variabili che conterranno temporaneamente i campi del file
	int message_id; // campo che conterrà l'id 
	char message[BUFF_MESSAGE_SIZE]; // buffer che contiene il messaggio ricevuto
	char id_sender[3]; // campo che conterrà l'id del processo sender
	char id_receiver[3];  // campo che conterrà l'id del processo receiver
	int DelS1; // ritardi con cui tale azione deve essere eseguita
	int DelS2; // ...
	int DelS3; // ...
	char Type[QUEUE_LENGTH]; // buffer che contiene il tipo di coda
	
	char result[BUFF_MESSAGE_SIZE]; // buffer temporaneo per il salvataggio dei caratteri

	// itero sul bufferR e salvo nel buffer temporaneo, result, gli elementi
	for(i = 0 ; i < FileSize ; i++) {
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
						message_id = atoi(result);
						break;				
					case 1:
						strcpy(message,result);
						break;
					case 2:
						strcpy(id_sender,result);
						break;
					case 3:
						strcpy(id_receiver,result);
						break;
					case 4:
						DelS1 = atoi(result);
						break;
					case 5:
						DelS2 = atoi(result);
						break;
					case 6:
						DelS3 = atoi(result);
						break;
					case 7:
						strcpy(Type,result);
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
			
			// aggiungo il messaggio alla lista 
			if (buffer[i] =='\n'){
				if (receiver == 1)
					messagesR1 = add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, receiver, semid);
				if (receiver == 2)
					messagesR2 = add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, receiver, semid);
				if (receiver == 3)
					messagesR3 = add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, receiver, semid);
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

struct Message* add_message(int message_id, char message[], char id_sender[], char id_receiver[], int DelS1, int DelS2, int DelS3, char Type[], int fd, int *PIPE, int receiver, int semid){

	// creo variabili
	struct Message* node;
	struct Message* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct Message*)malloc(sizeof(struct Message));
	
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);
	}

	// inizializzo i campi del nodo
	node->message_id = message_id;
 	strcpy(node->message, message);
  	strcpy(node->id_sender, id_sender);
  	strcpy(node->id_receiver, id_receiver);
  	node->DelS1 = DelS1;
  	node->DelS2 = DelS2;
  	node->DelS3 = DelS3;
  	strcpy(node->Type, Type);
	
    compile_file(fd, node, PIPE, receiver, semid);
	
	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if (receiver == 1){
		if(messagesR1 == NULL)
			return node;
		else{
			head = messagesR1;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesR1;
		}
	}
	else if (receiver == 2){
		if(messagesR2 == NULL){
			return node;
		}
		else{
			head = messagesR2;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesR2;
		}
	}
	else{
		if(messagesR3 == NULL){
			return node;
		}
		else{
			head = messagesR3;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesR3;
		}
	}
}

void compile_file(int fd, struct Message* msg, int *PIPE, int receiver, int semid){
	int length;  // variabile che conterrà la lunghezza della stringa da scrivere
	char *str = malloc(STRING_ALLOC_SIZE); // creazione del puntatore a char
	int delay;		

	// creo puntatore a struttura tm
	// creo variabile di tipo time tempo
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

	// posizionamento in coda al file
	off_t current = lseek(fd, -1, SEEK_END);
	
	// controllo errore
	if(current == -1){
		err_exit("lseek", __FILE__, __LINE__);
	}

	//determino il ritardo del messaggio
	if(receiver == 1){
		delay = msg->DelS1;
	}else if (receiver == 2){
		delay = msg->DelS2;
	}else{
		delay = msg->DelS3;
	}

	//creo dei processi figlio che si occuperanno di inviare il messaggio
	pid_t pid = fork();
	if(pid == -1){
		err_exit("fork", __FILE__, __LINE__);
	}else if(pid == 0){
		// Scrittura del nodo nella stringa
		// ritorna la lunghezza totale per la scrittura
		length = snprintf(str, STRING_ALLOC_SIZE, "%d;%s;%d;%d;%s%li:%s%li:%s%li;", msg->message_id, msg->message, 
				(msg->id_sender[0] - 48), (msg->id_receiver[0] -48), hours < 10 ? "0" : "",
				 hours, minutes < 10 ? "0" : "", minutes, seconds < 10 ? "0" : "", seconds);
		
		// controllo errore
		if (length == -1){
			err_exit("snprintf", __FILE__, __LINE__);
		}
		// aspetto del tempo indicato in Del
		sleep(delay);
		//Funzione invio messaggio
		Send_message(fd, msg, PIPE, receiver, str, semid);
		exit(0);
	}

	//creo liste contenenti i pid dei processi figli 
	if(receiver == 1){
		r1 = add_pidr1(pid);
	}else if (receiver == 2){
		r2 = add_pidr2(pid);
	}else{
		r3 = add_pidr3(pid);
	}

	//Liberazione memoria
	free(str);

}

void Send_message(int fd, struct Message *msg, int * PIPE, int receiver, char* row, int semid){
	char buffer[BUFLEN];
	int length;
	unsigned short semnum;

	if(receiver == 1)
		semnum = 6;
	else if(receiver == 2)
		semnum = 7;
	else
		semnum = 8;

	// Scrittura dell'ora ritardata
	take_time(buffer);
	strcat(row, buffer);
	strcat(row, "\n\n");

	semaphore_op(semid, semnum, -1);
	// scrittura sul file
	if (write(fd, row, strlen(row)) == -1){
		 err_exit("write", __FILE__, __LINE__);
	}
	semaphore_op(semid, semnum, 1);
	
	length = snprintf(buffer, BUFLEN, "%d;%s;%d;%d;%i;%i;%i;%s\n", msg->message_id, msg->message, 
					(msg->id_sender[0] - 48), (msg->id_receiver[0] -48), msg->DelS1, msg->DelS2, msg->DelS3, msg->Type);

	// controllo errore
	if (length == -1){
		err_exit("snprintf", __FILE__, __LINE__);
	}

	//solo il receiver 1 non deve scrivere su PIPE perche si occupa solo di ricevere i messaggi
	if(receiver != 1)
		write_pipe(PIPE, buffer);
}

void free_list(int receiver){

	// itero la lista finchè giungo alla fine
	if(receiver == 1)
		while (messagesR1 != NULL) {
        	struct Message *tmp = messagesR1;
        	messagesR1 = messagesR1->next;
        	free(tmp);
		}

	if(receiver == 2)
		while (messagesR2 != NULL) {
        	struct Message *tmp = messagesR2;
        	messagesR2 = messagesR2->next;
        	free(tmp);
		}

	if(receiver == 3)
		while (messagesR3 != NULL) {
        	struct Message *tmp = messagesR3;
        	messagesR3 = messagesR3->next;
        	free(tmp);
		}
}

//invio segnale per la rimozione del messaggio 
void remove_messageR1(){
	struct Pid* alias;
	alias = r1;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale per la rimozione del messaggio
void remove_messageR2(){
	struct Pid* alias;
	alias = r2;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale per la rimozione del messaggio
void remove_messageR3(){
	struct Pid* alias;
	alias = r3;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageR1(){
	struct Pid* alias;
	alias = r1;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}

//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageR2(){
	struct Pid* alias;
	alias = r2;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}

//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageR3(){
	struct Pid* alias;
	alias = r3;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}

//invio segnale che incrementa l'attesa dei messaggi ancora da inviare
void increase_delayR1(){
	struct Pid* alias;
	alias = r1;
	while(alias != NULL){
		pid_t pid = fork();
		pid_t child = alias->pid;
		if(pid == -1)
			err_exit("fork", __FILE__, __LINE__);
		if(pid == 0){
			kill(child, SIGSTOP);
			sleep(5);
			kill(child, SIGCONT);
			exit(0);
		}
		alias = alias->next;
	}
}

//invio segnale che incrementa l'attesa dei messaggi ancora da inviare
void increase_delayR2(){
	struct Pid* alias;
	alias = r2;
	while(alias != NULL){
		pid_t pid = fork();
		pid_t child = alias->pid;
		if(pid == -1)
			err_exit("fork", __FILE__, __LINE__);
		if(pid == 0){
			kill(child, SIGSTOP);
			sleep(5);
			kill(child, SIGCONT);
			exit(0);
		}
		alias = alias->next;
	}
}

//invio segnale che incrementa l'attesa dei messaggi ancora da inviare
void increase_delayR3(){
	struct Pid* alias;
	alias = r3;
	while(alias != NULL){
		pid_t pid = fork();
		pid_t child = alias->pid;
		if(pid == -1)
			err_exit("fork", __FILE__, __LINE__);
		if(pid == 0){
			kill(child, SIGSTOP);
			sleep(5);
			kill(child, SIGCONT);
			exit(0);
		}
		alias = alias->next;
	}
}

struct Pid* add_pidr1(pid_t pid){
	struct Pid* node;
	struct Pid* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct Pid*)malloc(sizeof(struct Pid));
	
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);
	}

	// inizializzo i campi del nodo
	node->pid = pid;
	node->next = NULL;

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(r1 == NULL){
		return node;
	}else{
		head = r1;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return r1;
	}
}

struct Pid* add_pidr2(pid_t pid){
	struct Pid* node;
	struct Pid* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct Pid*)malloc(sizeof(struct Pid));
	
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);
	}

	// inizializzo i campi del nodo
	node->pid = pid;
	node->next = NULL;

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(r2 == NULL){
		return node;
	}else{
		head = r2;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return r2;
	}
}

struct Pid* add_pidr3(pid_t pid){
	struct Pid* node;
	struct Pid* head;

	// allocazione dinamica in memoria di un nodo
	node = (struct Pid*)malloc(sizeof(struct Pid));
	
	// se il nodo contiene null 
	// non è stata allocato spazio in memoria 
	// quindi viene stampato errore
	if(node == NULL){
		err_exit("malloc", __FILE__, __LINE__);
	}

	// inizializzo i campi del nodo
	node->pid = pid;
	node->next = NULL;

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(r3 == NULL){
		return node;
	}else{
		head = r3;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return r3;
	}
}

void debig(char *path){
	char DEBUG[BUFLEN];

	//carico in DEBUG il contenuto del file
	int length = car_file(path, DEBUG);

	//apro il file e lo azzero
	int fd = open(path, O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);

	//itero il buffer DEBUG e quando arrivo al sesto ";" inserisco un a capo
	for(int i = 2 ; i < length ; i++){
		if(DEBUG[i] == '\n' && DEBUG[i -1] == DEBUG[i] && DEBUG[i - 2] == ';'){
			DEBUG[i - 1] = 32;
			DEBUG[i] = '\n';
		}
	}

	//scrivo sul file
	write(fd, DEBUG, strlen(DEBUG));

	if (close(fd) == -1)
         err_exit("Close failed", __FILE__, __LINE__);	
}
