#include "everything.h"

// definisco un puntatore ad una lista e lo inizializzo a null
struct Message* messagesS1 = NULL;
struct Message* messagesS2 = NULL;
struct Message* messagesS3 = NULL;
int type;
struct Pid *s1 = NULL;
struct Pid *s2 = NULL;
struct Pid *s3 = NULL;

int load_file(char* inputfile, char* buf) {
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

void Reader_buffer(char buffer[], int FileSize, int fd, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo){
	int campo = 0;  // indicatore del campo nella struttura
	int i;  // contatore che indica l'indice del buffer su cui è salvato il file
	int j = 0;  // contatore che indica l'indice del buffer temporaneo result
	int riga = 0; // contatore del numero di righe presenti nel file e del numero di nodi nella lista
	
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
	
	if(sender != 1)
		riga = 1;

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
				if (sender == 1)
					messagesS1 = Add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, sender, msgqid, semid, shmid, fdfifo);
				if (sender == 2)
					messagesS2 = Add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, sender, msgqid, semid, shmid, fdfifo);
				if (sender == 3)
					messagesS3 = Add_message(message_id, message, id_sender, id_receiver, DelS1, DelS2, DelS3, Type, fd, PIPE, sender, msgqid, semid, shmid, fdfifo);
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
	while(messagesS1 != NULL){
		struct Message* temp;
	
		// procedo all'invio del messaggio e lo scrivo nel file F corrispondente
		Compile_file(fd, messagesS1, PIPE, sender, msgqid, semid, shmid, fdfifo);

		temp = messagesS1;
		messagesS1 = messagesS1->next;
		free(temp);
	}
}

struct Message* Add_message(int message_id, char message[], char id_sender[], char id_receiver[], int DelS1, int DelS2, int DelS3, char Type[], int fd, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo){
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
	
	if(sender != 1){
		Compile_file(fd, node, PIPE, sender, msgqid, semid, shmid, fdfifo);
	}

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if (sender == 1){
		if(messagesS1 == NULL)
			return node;
		else{
			head = messagesS1;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesS1;
		}
	}
	else if (sender == 2){
		if(messagesS2 == NULL){
			return node;
		}
		else{
			head = messagesS2;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesS2;
		}
	}
	else{
		if(messagesS3 == NULL){
			return node;
		}
		else{
			head = messagesS3;
			while(head->next !=NULL)
				head = head->next;
			head->next = node;
			return messagesS3;
		}
	}
}

void Compile_file(int fd, struct Message* msg, int *PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo){
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

	//ottengo il ritardo del messaggio
	if(sender == 1){
		delay = msg->DelS1;
	}else if (sender == 2){
		delay = msg->DelS2;
	}else{
		delay = msg->DelS3;
	}

	//creo un processo figlio per oggi messaggio
	pid_t pid = fork();
	if(pid == -1){
		err_exit("fork", __FILE__, __LINE__);
	}else if(pid == 0){
		// Scrittura del nodo nella stringa
		// ritorna la lunghezza totale per la scrittura
		if (sender == 1)
			length = snprintf(str, STRING_ALLOC_SIZE, "%d;%s;%d;%d;%s%li:%s%li:%s%li;", msg->message_id, msg->message, 
							(msg->id_sender[1] - 48), (msg->id_receiver[1] -48), hours < 10 ? "0" : "",
							 hours, minutes < 10 ? "0" : "", minutes, seconds < 10 ? "0" : "", seconds);
		else
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
		send_message(msg, PIPE, sender, msgqid, semid, shmid, fdfifo, fd, str);
		exit(0);
	}

	//aggiungo il pid del processo figlio sulla apposità lista
	if(sender == 1){
		s1 = add_pids1(pid);
	}else if (sender == 2){
		s2 = add_pids2(pid);
	}else{
		s3 = add_pids3(pid);
	}

	//Liberazione memoria
	free(str);
}

void send_message(struct Message *msg, int * PIPE, int sender, int msgqid, int semid, int shmid, int fdfifo, int fd, char *row){
	char buffershm[BUFLEN];
	char receiver[2];
	int length;
	char buffer[BUFLEN];
	struct msg m;
	unsigned short semnum;

	if(sender == 1)
		semnum = 3;
	else if(sender == 2)
		semnum = 4;
	else
		semnum = 5;

	// Scrittura dell'ora ritardata
	take_time(buffer);
	strcat(row, buffer);
	if(sender != 1)
		strcat(row, "\n");

	//definisco il ricevitore del messaggio
	//nel caso di sender 1 bisogna verificare l'elemento 1 dell'array di id_receiver
	//perchè il sender leggendo da file carica anche il tipo di processo che deve inviare il messaggio
	//quindi alla posizione zero di id_receiver può contenere "R" o "S", che risulta essere non necessario in questo caso
	//inoltre viene definito il type che indica a quale ricevitore nella messagequeue deve leggere il messaggio
	if(sender == 1){
		switch(msg->id_receiver[1] -48){
			case 1:
				type = 1;
				receiver[0] = '1';
			break;
			case 2:
				type = 2;
				receiver[0] = '2';
			break;
			case 3:
				type = 3;
				receiver[0] = '3';
			break;
			default:
			break;
		}
	}else{
		switch(msg->id_receiver[0] -48){
			case 1:
				type = 1;
				receiver[0] = '1';
			break;
			case 2:
				type = 2;
				receiver[0] = '2';
			break;
			case 3:
				type = 3;
				receiver[0] = '3';
			break;
			default:
			break;
		}
	}

	semaphore_op(semid, semnum, -1);
	// scrittura sul file
	char ROW[BUFLEN];
	int l = snprintf(ROW, BUFLEN, "%s\n", row);
	if (write(fd, ROW, l) == -1){
		 err_exit("write", __FILE__, __LINE__);
	}
	semaphore_op(semid, semnum, 1);
	
	if(sender == 1)
		length = snprintf(buffer, BUFLEN, "%d;%s;%d;%d;%i;%i;%i;%s;\n", msg->message_id, msg->message, 
					(msg->id_sender[1] - 48), (msg->id_receiver[1] -48), msg->DelS1, msg->DelS2, msg->DelS3, msg->Type);
	else
		length = snprintf(buffer, BUFLEN, "%d;%s;%d;%d;%i;%i;%i;%s;\n", msg->message_id, msg->message, 
					(msg->id_sender[0] - 48), (msg->id_receiver[0] -48), msg->DelS1, msg->DelS2, msg->DelS3, msg->Type);

	// controllo errore
	if (length == -1){
		err_exit("snprintf", __FILE__, __LINE__);
	}

	if(sender == 1){
		//se il messaggio non deve essere inviato dal sender 1, il messaggio viene scritto su PIPE
		if(msg->id_sender[1] != (sender + 48)){
			write_pipe(PIPE, buffer);

		//se il tipo di messaggio richiede la messagequeue viene impostato il tipo del messaggio e inviato
		}else if(strcmp(msg->Type, "Q") == 0){
			m.mtype = type;
			
			memcpy(m.description, buffer, strlen(buffer) + 1);
	
			size_t mSize = sizeof(struct msg) - sizeof(long);
	
			if (msgsnd(msgqid, &m, mSize, 0) == -1)
				err_exit("msgsnd failed", __FILE__, __LINE__);
		
		//se il messaggio richiede la sharedmemory carico in buffershm il receiver e il contenuto del messaggio
		//poi viene decrementato il semaforo 0 per stabilire che la shm è occupata, se la shared memory 
		//è già occupata attende che si liberi, poi viene scritto su shm il contenuto di buffershm
		//viene poi indicato incrementando il semaforo 1 che il messaggio è pronto
		//il messaggio attende che il messaggio sia ricevuto decrementando il semaforo -1
		//la shared memory viene azzerata e liberata, e infine incrimentando il semaforo 0, lascia che qualcun altro la usi
		}else if(strcmp(msg->Type, "SH") == 0){
			strcpy(buffershm, receiver);
			strcat(buffershm, buffer);
			semaphore_op(semid, (unsigned short)0, -1);
			//attacco della shared memory
			char *shmbuf = (char *)get_shared_memory(shmid, 0);
			strcpy(shmbuf, buffershm);		
			semaphore_op(semid, (unsigned short)1, 1);
			semaphore_op(semid, (unsigned short)2, -1);
			strcpy(shmbuf, "");
			free_shared_memory(shmbuf);
			semaphore_op(semid, (unsigned short)0, 1);
		}
	}else if(sender == 2){

		//se il messaggio non deve essere inviato dal sender 2, il messaggio viene scritto su PIPE
		if(msg->id_sender[0] != (sender + 48)){
			write_pipe(PIPE, buffer);

		//se il tipo di messaggio richiede la messagequeue viene impostato il tipo del messaggio e inviato
		}else if(strcmp(msg->Type, "Q") == 0){
			m.mtype = type;
	
			memcpy(m.description, buffer, strlen(buffer) + 1);
	
			size_t mSize = sizeof(struct msg) - sizeof(long);
	
			if (msgsnd(msgqid, &m, mSize, 0) == -1)
				err_exit("msgsnd failed", __FILE__, __LINE__);

		//se il messaggio richiede la sharedmemory carico in buffershm il receiver e il contenuto del messaggio
		//poi viene decrementato il semaforo 0 per stabilire che la shm è occupata, se la shared memory 
		//è già occupata attende che si liberi, poi viene scritto su shm il contenuto di buffershm
		//viene poi indicato incrementando il semaforo 1 che il messaggio è pronto
		//il messaggio attende che il messaggio sia ricevuto decrementando il semaforo -1
		//la shared memory viene azzerata e liberata, e infine incrimentando il semaforo 0, lascia che qualcun altro la usi
		}else if(strcmp(msg->Type, "SH") == 0){
			strcpy(buffershm, receiver);
			strcat(buffershm, buffer);
			semaphore_op(semid, (unsigned short)0, -1);
			// attach the shared memory segment
			char *shmbuf = (char *)get_shared_memory(shmid, 0);
			strcpy(shmbuf, buffershm);		
			semaphore_op(semid, (unsigned short)1, 1);
			semaphore_op(semid, (unsigned short)2, -1);
			strcpy(shmbuf, "");
			free_shared_memory(shmbuf);
			semaphore_op(semid, (unsigned short)0, 1);
		}
	}else if(sender == 3){

		//se il messaggio non deve essere inviato dal sender 3, il messaggio viene scritto su PIPE
		if(strcmp(msg->Type, "FIFO") == 0){
			write_fifo(fdfifo, buffer);

		//se il tipo di messaggio richiede la messagequeue viene impostato il tipo del messaggio e inviato
		}else if(strcmp(msg->Type, "Q") == 0){
			m.mtype = type;

			memcpy(m.description, buffer, strlen(buffer) + 1);
	
			size_t mSize = sizeof(struct msg) - sizeof(long);

			if (msgsnd(msgqid, &m, mSize, 0) == -1)
				err_exit("msgsnd failed", __FILE__, __LINE__);

		//se il messaggio richiede la sharedmemory carico in buffershm il receiver e il contenuto del messaggio
		//poi viene decrementato il semaforo 0 per stabilire che la shm è occupata, se la shared memory 
		//è già occupata attende che si liberi, poi viene scritto su shm il contenuto di buffershm
		//viene poi indicato incrementando il semaforo 1 che il messaggio è pronto
		//il messaggio attende che il messaggio sia ricevuto decrementando il semaforo -1
		//la shared memory viene azzerata e liberata, e infine incrimentando il semaforo 0, lascia che qualcun altro la usi
		}else if(strcmp(msg->Type, "SH") == 0){
			strcpy(buffershm, receiver);
			strcat(buffershm, buffer);
			semaphore_op(semid, (unsigned short)0, -1);
			// attach the shared memory segment
			char *shmbuf = (char *)get_shared_memory(shmid, 0);
			strcpy(shmbuf, buffershm);		
			semaphore_op(semid, (unsigned short)1, 1);
			semaphore_op(semid, (unsigned short)2, -1);
			strcpy(shmbuf, "");
			free_shared_memory(shmbuf);
			semaphore_op(semid, (unsigned short)0, 1);
		}
	}
}

void free_list(int sender){
	// itero la lista finchè giungo alla fine
	if(sender == 1){
		while (messagesS1 != NULL) {
       		struct Message *tmp = messagesS1;
        	messagesS1 = messagesS1->next;
        	free(tmp);
		}
	}
	if(sender == 2){
		while (messagesS2 != NULL) {
        	struct Message *tmp = messagesS2;
        	messagesS2 = messagesS2->next;
        	free(tmp);
		}
	}
	if(sender == 3){
		while (messagesS3 != NULL) {
    	    struct Message *tmp = messagesS3;
    	    messagesS3 = messagesS3->next;
    	    free(tmp);
		}
	}
}

//invio segnale per la rimozione del messaggio
void remove_messageS1(){
	struct Pid* alias;
	alias = s1;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale per la rimozione del messaggio
void remove_messageS2(){
	struct Pid* alias;
	alias = s2;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale per la rimozione del messaggio
void remove_messageS3(){
	struct Pid* alias;
	alias = s3;
	while(alias != NULL){
		kill(alias->pid, SIGKILL);
		alias = alias->next;
	}
}

//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageS1(){
	struct Pid* alias;
	alias = s1;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}

//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageS2(){
	struct Pid* alias;
	alias = s2;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}


//invio segnale che direttamente elimina l'attesa del messaggio in attesa
void send_messageS3(){
	struct Pid* alias;
	alias = s3;
	while(alias != NULL){
		kill(alias->pid, SIGCONT);
		alias = alias->next;
	}
}

//invio segnale che incrementa l'attesa dei messaggi ancora da inviare
void increase_delayS1(){
	struct Pid* alias;
	alias = s1;
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
void increase_delayS2(){
	struct Pid* alias;
	alias = s2;
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
void increase_delayS3(){
	struct Pid* alias;
	alias = s3;
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

struct Pid* add_pids1(pid_t pid){
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

	//printf("pidS1 : %i\n", pid);

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(s1 == NULL){
		return node;
	}else{
		head = s1;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return s1;
	}
}

struct Pid* add_pids2(pid_t pid){
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

	//printf("pidS2 : %i\n", pid);

	// se list è vuota ritorno il nodo 
	// sennò itero la lista fino all'ultimo elemento della stessa
	// e lo ritorno
	if(s2 == NULL){
		return node;
	}else{
		head = s2;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return s2;
	}
}

struct Pid* add_pids3(pid_t pid){
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
	if(s3 == NULL){
		return node;
	}else{
		head = s3;
		while(head->next !=NULL)
			head = head->next;
		head->next = node;
		
		return s3;
	}
}

void debug(char *path){
	char DEBUG[BUFLEN];

	//carico in DEBUG il contenuto del file
	int length = load_file(path, DEBUG);

	//apro il file e lo azzero
	int fd = open(path, O_CREAT | O_WRONLY | O_TRUNC ,S_IRUSR | S_IWUSR);

	//itero il buffer DEBUG e quando arrivo al sesto ";" inserisco un a capo
	for(int i = 2 ; i < length ; i++){
		if(DEBUG[i] == '\n' && DEBUG[i -1] == DEBUG[i] && DEBUG[i - 2] == ';')
			DEBUG[i - 1] = 32;
	}

	//scrivo sul file
	write(fd, DEBUG, strlen(DEBUG));

	if (close(fd) == -1)
         err_exit("Close failed", __FILE__, __LINE__);	
}