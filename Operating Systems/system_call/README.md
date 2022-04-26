Progetto sistemi operativi A.A. 2020/2021

Con questo piccolo read me, volevamo far prensente che durante lo shutdown i semafori danno come errore la stringa "Invalid argument".
Questo messaggio viene scaturito quando ci sono processi in attesa per usare la shared memory ma il processo ricevente è già terminato, quindi quest'ultimo non può eseguire la semop per sbloccare il sender. Per sincronizzare la memoria condivisa abbiamo infatti utilizzato il meccanismo utilizzato durante gli esercizi di laboratorio, in quanto metodo efficace per risolvere il problema dell'accesso a sezione critica.
Nonostante i nostri molteplici tentativi non siamo riusciti ad evitare questo spiacevole errore, che non influisce sullo scambio di messaggi in quanto avviene nel momento in cui tutti i figli devono terminare, ma non è comunque piacevole da vedere.

Saluti,

Massagrande Marco, Vanini Samantha, Zorer Elena
