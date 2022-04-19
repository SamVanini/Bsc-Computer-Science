/* CREAZIONE DB*/

create table RESPONSABILI_AGENZIA(
    login varchar(20),
    nome varchar(20),
    cognome varchar(20),
    telefono varchar(10),
    password varchar(20),
    PRIMARY KEY (login)
);
create table GENITORI(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome varchar(20),
    cognome varchar(20),
    telefono varchar(10),
    mail varchar(500),
    PRIMARY KEY (id)
);

create table ALLERGIE(
    nome varchar(20),
    trattamento varchar(5000),
    PRIMARY KEY (nome)
);

create table GITE(
    destinazione varchar(50),
    costo int,
    numeroOre int,
    Descrizione varchar(5000),
    PRIMARY KEY (destinazione)
);

create table VACANZE(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    destinazione varchar(20),
    dataPartenza varchar(10),
    numeroSettimane int,
    linguaStudiata varchar(20),
    gita1 varchar(50),
    gita2 varchar(50),
    costo int,
    PRIMARY KEY (id),
    FOREIGN KEY (gita1) REFERENCES GITE(destinazione),
    FOREIGN KEY (gita2) REFERENCES GITE(destinazione)
);

create table RAGAZZI(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome varchar(20),
    cognome varchar(20),
    eta int,
    via varchar(50),
    numeroCivico varchar(10),
    paese varchar(100),
    telefono varchar(10),
    mail varchar(500),
    hobby varchar(500),
    allergia1 varchar(20),
    allergia2 varchar(20),
    genitore int,
    PRIMARY KEY (id),
    FOREIGN KEY (genitore) REFERENCES GENITORI(id),
    FOREIGN KEY (allergia1) REFERENCES ALLERGIE(nome),
    FOREIGN KEY (allergia2) REFERENCES ALLERGIE(nome)
);
create table FAMIGLIA(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    nome varchar(20),
    cognome varchar(20),
    telefono varchar(10),
    mail varchar(500),
    paeseResidenza varchar(20),
    componenti int,
    /* 0 se non hanno animali, 1 altrimenti */
    animaliDomestici int,
    numeroCamere int,
    numeroBagni int,
    distanzaDalCentro int,
    PRIMARY KEY (id)
);

create table ATTIVITA(
    nome varchar(20),
    descrizione varchar(500),
    PRIMARY KEY (nome)
);

create table COLLEGE(
    nome varchar(50),
    citta varchar(50),
    indirizzo varchar(100),
    attivita1 varchar(20),
    attivita2 varchar(20),
    PRIMARY KEY (nome)
);

create table CAMERE(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    tipologia varchar(20),
    college varchar(50),
    PRIMARY KEY (id),
    FOREIGN KEY (college) REFERENCES COLLEGE(nome)
);

create table QUESTIONARIO(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    voto int,
    commento varchar(5000),
    PRIMARY KEY (id)
);

create table prenotazione(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    data varchar(10),
    ragazzo int,
    vacanza int,
    college int,
    famiglia int,
    metodoPagamento varchar(50),
    nomeAmico varchar(20),
    cognomeAmico varchar(20),
    mailAmico varchar(500),
    certificatoAcquisito varchar(30),
    questionario int,
    PRIMARY KEY (id),
    FOREIGN KEY (ragazzo) REFERENCES RAGAZZI(id),
    FOREIGN KEY (vacanza) REFERENCES VACANZE(id),
    FOREIGN KEY (famiglia) REFERENCES FAMIGLIA(id),
    FOREIGN KEY (college) REFERENCES CAMERE(id),
    FOREIGN KEY (questionario) REFERENCES QUESTIONARIO(id)
);

create table CREDENZIALI(
    mail varchar(200),
    password varchar(12),
    PRIMARY KEY (mail, password)
);

create table LOCK (
    tabella varchar(30),
    /* se == 0 entro, se == 1 aspetto*/
    modifica int,
    PRIMARY KEY (tabella)
);

/*POPOLAMENTO RESPONSABILI*/
INSERT INTO RESPONSABILI_AGENZIA(login, nome, cognome, telefono, password) VALUES ('responsabile1', 'Samantha', 'Vanini', '0123456789', 'Password!');
INSERT INTO RESPONSABILI_AGENZIA(login, nome, cognome, telefono, password) VALUES ('responsabile2', 'Marco', 'Massagrande', '9874563210', 'Password!');
INSERT INTO RESPONSABILI_AGENZIA(login, nome, cognome, telefono, password) VALUES ('responsabile3', 'Elena', 'Zorer', '3210654897', 'Password!');

/*POPOLAMENTO ALLERGIE*/
INSERT INTO ALLERGIE(nome, trattamento) VALUES ('Nessuna', '');
INSERT INTO ALLERGIE(nome, trattamento) VALUES ('Celiachia', 'Trattamento1');
INSERT INTO ALLERGIE(nome, trattamento) VALUES ('Favismo', 'Trattamento2');
INSERT INTO ALLERGIE(nome, trattamento) VALUES ('Lattosio', 'Trattamento3');
INSERT INTO ALLERGIE(nome, trattamento) VALUES ('Polline', 'Trattamento4');

/*POPOLAMENTO ATTIVITA'*/
INSERT INTO ATTIVITA(nome, descrizione) VALUES ('Calcio', 'Squadra di calcio del college');
INSERT INTO ATTIVITA(nome, descrizione) VALUES ('Teatro', 'Corso di teatro');
INSERT INTO ATTIVITA(nome, descrizione) VALUES ('Pallamano', 'Squadra di pallamano del college');
INSERT INTO ATTIVITA(nome, descrizione) VALUES ('Pallacanestro', 'Squadra di pallacanestro del college');

/*POPOLAMENTO COLLEGE*/
INSERT INTO COLLEGE(nome, citta, indirizzo, attivita1, attivita2) VALUES ('', '', '', '', '');
INSERT INTO COLLEGE(nome, citta, indirizzo, attivita1, attivita2) VALUES ('CollegeA', 'Dublino','via di qui', 'Calcio', 'Teatro');
INSERT INTO COLLEGE(nome, citta, indirizzo, attivita1, attivita2) VALUES ('CollegeB', 'Monaco', 'vicolo corto', 'Pallamano', 'Teatro');

/*POPOLAMENTO CAMERE*/
INSERT INTO  CAMERE(tipologia, college) VALUES ('', '');
INSERT INTO  CAMERE(tipologia, college) VALUES ('singola', 'CollegeA');
INSERT INTO  CAMERE(tipologia, college) VALUES ('condivisa', 'CollegeA');
INSERT INTO  CAMERE(tipologia, college) VALUES ('singola', 'CollegeB');
INSERT INTO  CAMERE(tipologia, college) VALUES ('condivisa', 'CollegeB');

/*POPOLAMENTO GENITORI*/
INSERT INTO GENITORI(nome, cognome, telefono, mail) VALUES ('Mia', 'Madre', '8765941302', 'aaaa@gmail.com');
INSERT INTO GENITORI(nome, cognome, telefono, mail) VALUES ('Mio', 'Padre', '3268415970', 'bbbb@gmail.com');

/*POPOLAMENTO GITE*/
INSERT INTO GITE(destinazione, costo, numeroOre, Descrizione) VALUES ('', 0, 0, '');
INSERT INTO GITE(destinazione, costo, numeroOre, Descrizione) VALUES ('Allianz Arena', 50, 3, 'Visita allo stadio');
INSERT INTO GITE(destinazione, costo, numeroOre, Descrizione) VALUES ('Guinness Plant', 40, 2, 'Visita alla fabbrica di birra co degustazione finale');
INSERT INTO GITE(destinazione, costo, numeroOre, Descrizione) VALUES ('Estoril', 30, 2, 'Visita al circuito di Estoril');

/*POPOLAMENTO VACANZE*/
INSERT INTO VACANZE(destinazione, dataPartenza, numeroSettimane, linguaStudiata, gita1, gita2, costo) VALUES ('Dublino', '19/05/21', 2, 'Inglese', 'Guinness Plant', '', 1800);
INSERT INTO VACANZE(destinazione, dataPartenza, numeroSettimane, linguaStudiata, gita1, gita2, costo) VALUES ('MonacoDiBaviera', '28/06/21', 1, 'Tedesco', 'Allianz Arena', '', 700);
INSERT INTO VACANZE(destinazione, dataPartenza, numeroSettimane, linguaStudiata, gita1, gita2, costo) VALUES ('Lisbona', '12/07/21', 2, 'Portoghese', 'Estoril', '', 1200);

/*POPOLAMENTO FANIGLIA*/
INSERT INTO FAMIGLIA (nome, cognome, telefono, mail, paeseResidenza, componenti, animaliDomestici, numeroCamere, numeroBagni, distanzaDalCentro) VALUES ('Nessuna', 'Famiglia', '', '', 0, 0, 0, 0, 0, 0);
INSERT INTO FAMIGLIA (nome, cognome, telefono, mail, paeseResidenza, componenti, animaliDomestici, numeroCamere, numeroBagni, distanzaDalCentro) VALUES ('John', 'Doe', '6985320147', 'dddd@gmail.com', 'MonacoDiBaviera', 5, 0, 4, 3, 2);
INSERT INTO FAMIGLIA (nome, cognome, telefono, mail, paeseResidenza, componenti, animaliDomestici, numeroCamere, numeroBagni, distanzaDalCentro) VALUES ('Jane', 'Doe', '6478952130', 'eeee@gmail.com', 'Lisbona', 3, 1, 3, 2, 1);
INSERT INTO FAMIGLIA (nome, cognome, telefono, mail, paeseResidenza, componenti, animaliDomestici, numeroCamere, numeroBagni, distanzaDalCentro) VALUES ('Peter', 'Griffin', '7413652089', 'ihihihih@gmail.com', 'Dublino', 5, 1, 5, 4, 3);

/*POPOLAMENTO RAGAZZI*/
INSERT INTO RAGAZZI(nome, cognome, eta, via, numeroCivico, paese, telefono, mail, hobby, allergia1, allergia2, genitore) VALUES ('Ragazzo', 'Uno', 18, 'di casa mia', '2', 'Il mio', '0036529841', 'llll@gmail.com', 'chitarra', 'Celiachia', 'Nessuna', 1);
INSERT INTO RAGAZZI(nome, cognome, eta, via, numeroCivico, paese, telefono, mail, hobby, allergia1, allergia2, genitore) VALUES ('Ragazzo', 'Due', 14, 'dei matti', '0', 'Non lo so', '3365214987', 'pppp@gmail.com', 'tennis', 'Polline', 'Lattosio', 2);

/*POPOLAMENTO CREDENZIALI*/
INSERT INTO CREDENZIALI(mail, password) VALUES ('llll@gmail.com', 'Password1!');
INSERT INTO CREDENZIALI(mail, password) VALUES ('pppp@gmail.com', 'FerieSiSiS1!');

/*POPOLAMENTO QUESTIONARI*/
INSERT INTO QUESTIONARIO(voto, commento) VALUES (1, 'Questionario mancante');
INSERT INTO QUESTIONARIO(voto, commento) VALUES (8, 'Vacanza a Dublino fantastica, famiglia ospitale');
INSERT INTO QUESTIONARIO(voto, commento) VALUES (7, 'Vacanza a Monaco fantastica, college ed ambiente internazionale');

/*POPOLAMENTO PRENOTAZIONI*/
INSERT INTO PRENOTAZIONE(data, ragazzo, vacanza, college, famiglia, metodoPagamento, nomeAmico, cognomeAmico, mailAmico, certificatoAcquisito, questionario) VALUES ('06/05/21', 1, 1, 1, 3, 'Bonifico bacario', 'Homer', 'Simposon', 'miamail@gmail.com', 'Inglese B1', 2);
INSERT INTO PRENOTAZIONE(data, ragazzo, vacanza, college, famiglia, metodoPagamento, nomeAmico, cognomeAmico, mailAmico, certificatoAcquisito, questionario) VALUES ('11/05/21', 2, 2, 4, 1, 'Bonifico bacario', 'Alfredo', 'Mingiazzo', 'tuamail@gmail.com', 'Tedesco A2', 3);

/*POPOLAMENTO LOCK*/
INSERT INTO LOCK(tabella, modifica) VALUES ('genitori', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('ragazzi', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('gite', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('vacanze', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('credenziali', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('questionario', 0);
INSERT INTO LOCK(tabella, modifica) VALUES ('prenotazione', 0);