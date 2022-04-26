# FILE:
#	parcheggio.s
#
# DESCRIZIONE:
#	modulo assembly che, data una combinazione di valori in ingresso
#	rappresentanti l'operazione de eseguire ed il parcheggio coinvolto,
#	restituisce lo stato delle sbarre di ingresso ed uscita, il numero
#	di auto per settore e lo stato in cui si trova il settore.
#
# AUTORI:
#	Ferronato Marta (VR443649)
#	Massagrande Marco (VR446285)
#	Vanini Samantha (VR443381)
#
# DATA:
#	13-07-2020
#
#######################################
.section .data

    dieci: .byte 10
    
.section .text
	.global elaborazione
	
elaborazione:

	##########################
	# OPERAZIONI PRELIMINARI #
	##########################
	#
	# - Imposto lo stack pointer attuale come base pointer
	# - Salvo lo stato dei registri (per ripristino futuro)
	# - Salvo l'indirizzo dei parametri in %esi e %edi
	# - Controllo se mi viene passato un file vuoto
	# - Imposto a 3 il contatore per il loop
	#
	####################################

    #salvo ebp attuale e imposto esp
	pushl %ebp
	movl %esp, %ebp
	
	#salvo lo stato dei registri, VANNO SCARICATI PRIMA DELLA RET
	pushl %eax
	pushl %ebx
	pushl %ecx
	pushl %edi
	pushl %esi
	
	#esi contiene l'indirizzo di bufferin
	#edi contiene l'indirizzo di bufferout
	movl 8(%ebp), %esi
	movl 12(%ebp), %edi

	#svuoto i registri
	xorl %ecx, %ecx
	xorl %eax, %eax
	xorl %ebx, %ebx
	xorl %edx, %edx

	#numero di righe per l'inizializzazione
    movl $3, %ecx
    
    
INIZIALIZZA:
	    
	##################################
	# INSERIMENTO AUTO NEI PARCHEGGI #
	##################################
	#
	# Inserisco il valore delle prime 3
	# righe del file di ingresso all'interno dei rispettivi
	# parcheggi e scorro utilizzando la dimensione della riga
	# del file sorgente
	#
	############################################

    xorl %eax, %eax
    xorl %ebx, %ebx

	# carico la prima cifra 
    movb 2(%esi), %dl	
    subl $48, %edx 		
    addl %edx, %eax
    
    cmpb $10, 3(%esi)
    je NEXT

    mull dieci
    
	# carico l'eventuale seconda cifra
    movb 3(%esi), %dl	
    subl $48, %edx 		
    addl %edx, %eax

    pushl %eax
	movb (%esi), %bl
	pushl %ebx
    addl $5, %esi
    
    loop INIZIALIZZA
    jmp start

NEXT:

    pushl %eax
	movb (%esi), %bl
	pushl %ebx
    addl $4, %esi
    
    loop INIZIALIZZA
	xorl %ecx, %ecx
	xorl %eax, %eax
	xorl %ebx, %ebx
	movl $32, %eax
	movl $32, %ebx
	movl $32, %ecx
    jmp start
    
start:
	
	xorl %edx, %edx
	popl %edx
A:
	cmpl $65, %edx  
	jne B
	xorl %eax, %eax
    popl %eax
	jmp control

B:
	cmpl $66, %edx  
	jne C
	xorl %ebx, %ebx
    popl %ebx
	jmp control

C:
	cmpl $67, %edx  
	jne control
	xorl %ecx, %ecx
    popl %ecx
	jmp control

control:
	cmpl $32, %eax
	je start
	cmpl $32, %ebx
	je start
	cmpl $32, %ecx
	je start
	jmp AUTO

AUTO:
	
	############################
	# FUNZIONAMENTO AUTOMATICO #
	############################
	#
	# Controllo che l'istruzione sia nel formato
	# atteso e procedo a far entrare oppure uscire
	# il veicolo dal parcheggio.
	# Se il parcheggio è pieno sarà impedito l'ingresso,
	# se la stringa ricevuta è corrotta non sarà alzata 
	# nessuna sbarra.
	#
	# A = %eax
	# B = %ebx
	# C = %ecx
	#
	############################################

	#inizializzo stringa out
	movb $67, (%edi)
	movb $67, 1(%edi)
	movb $48, 12(%edi)
	movb $48, 12(%edi)
	movb $48, 13(%edi)
	movb $48, 14(%edi)

IN:
	#controllo che la stringa sia di ingresso
	cmpb $73, (%esi)  #I
	jne OUT
	cmpb $78, 1(%esi) #N
	jne PIENO_A
	cmpb $45, 2(%esi) #-
	jne PIENO_A
	cmpb $65, 3(%esi) #A
	je PA_IN
	cmpb $66, 3(%esi) #B
	je PB_IN
	cmpb $67, 3(%esi) #C
	je PC_IN
	jmp PIENO_A	

PA_IN:
	cmpb $10, 4(%esi)
	jne PIENO_A
	cmpl $31, %eax
	je PIENO_A
	addl $1, %eax
	movb $79, (%edi)
	jmp PIENO_A
PB_IN:
	cmpb $10, 4(%esi)
	jne PIENO_A
	cmpl $31, %ebx
	je PIENO_A
	addl $1, %ebx
	movb $79, (%edi)
	jmp PIENO_A
PC_IN:
	cmpb $10, 4(%esi)
	jne PIENO_A
	cmpl $24, %ecx
	je PIENO_A
	addl $1, %ecx
	movb $79, (%edi)
	jmp PIENO_A

OUT:
	#controllo che la stringa sia di uscita 
	cmpb $79, (%esi)  #O
	jne PIENO_A
	cmpb $85, 1(%esi) #U
	jne PIENO_A
	cmpb $84, 2(%esi) #T
	jne PIENO_A
	cmpb $45, 3(%esi) #-
	jne PIENO_A
	cmpb $65, 4(%esi) #A
	je PA_OUT
	cmpb $66, 4(%esi) #B
	je PB_OUT
	cmpb $67, 4(%esi) #C
	je PC_OUT
	jmp PIENO_A	

PA_OUT:
	cmpb $10, 5(%esi)
	jne PIENO_A
	cmpl $0, %eax
	je PIENO_A
	subl $1, %eax
	movb $79, 1(%edi)
	jmp PIENO_A	
PB_OUT:
	cmpb $10, 5(%esi)
	jne PIENO_A
	cmpl $0, %ebx
	je PIENO_A
	subl $1, %ebx
	movb $79, 1(%edi)
	jmp PIENO_A
PC_OUT:
	cmpb $10, 5(%esi)
	jne PIENO_A
	cmpl $0, %ecx
	je PIENO_A
	subl $1, %ecx
	movb $79, 1(%edi)
	jmp PIENO_A

PIENO_A:
	cmpl $31, %eax
	jne PIENO_B
	movb $49, 12(%edi)
PIENO_B:
	cmpl $31, %ebx
	jne PIENO_C
	movb $49, 13(%edi)
PIENO_C:
	cmpl $24, %ecx
	jne NPOSTIA
	movb $49, 14(%edi)

	###########################
	#     RIEMPIMENTO EDI     #
	###########################
	#
	# Inserisco il numero di auto presenti nella
	# stringa del file di output, implementando un
	# codice che converte il valore del registro 
	# corrispondente al parcheggio nella sequenza
	# di caratteri corrispondente
	#
	############################################ 

NPOSTIA:
    pushl %eax
    divb dieci
    addb $48, %al
    addb $48, %ah
    movb %al, 3(%edi)
    movb %ah, 4(%edi)
    popl %eax
    
NPOSTIB:    
    pushl %eax 
    pushl %ebx
    movl %ebx, %eax
    divb dieci
    addb $48, %al
    addb $48, %ah
    movb %al, 6(%edi)
    movb %ah, 7(%edi)
    popl %ebx
    popl %eax

NPOSTIC:
    pushl %eax 
    pushl %ecx
    movl %ecx, %eax
    divb dieci
    addb $48, %al
    addb $48, %ah
    movb %al, 9(%edi)
    movb %ah, 10(%edi)
    popl %ecx
    popl %eax

	movb $45, 2(%edi)
	movb $45, 5(%edi)
	movb $45, 8(%edi)
	movb $45, 11(%edi)
	movb $10, 15(%edi)

	############################
	# PROSSIMA RIGA, CONTROLLO #
	############################
	#
	# Sposto il "puntatore" sulla stringa di input fino a quando non arrivo al primo
	# carattere della riga successiva, così facendo posso accedere alla riga.
	#
	# Agisco analogamente sulla stringa output, con il rispettivo valore
	# di lunghezza (16).
	#
	# Controllo se l'input è finito: in tal caso, imprimo EOF ($0)
	# sulla stringa in output e termino.
	# In caso contrario, il ciclo si ripete
	#
	############################################
 
prossimaRiga:

	#passo alla riga successiva (input a lunghezza variabile, 16 di output)
	addl $16, %edi

scorrimentoEsi:
	addl $1, %esi
	cmpb $10,(%esi)
	jne scorrimentoEsi
	addl $1, %esi

prossimaIstruzione:
	#controllo di essere alla fine della stringa. Altrimenti ricomincio
	cmpb $0, (%esi)
	jne AUTO	

	#metto /0 a fine stringa
	movb $0, (%edi)
	
	#####################################################
	# FINE ELABORAZIONE, RIPRISTINO CONDIZIONI INIZIALI #
	#####################################################
	#
	# Riporto lo stato dei registri a com'era prima della chiamata a funzione
	# e termino 
	#
	#####################################

	#ripristino i registri e ritorno
	popl %esi
	popl %edi
	popl %ecx
	popl %ebx
	popl %eax
	popl %ebp
	
	ret
