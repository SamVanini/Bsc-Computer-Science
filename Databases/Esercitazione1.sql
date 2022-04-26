CREATE TABLE public.museo (
	nome character varying(30) DEFAULT 'MuseoVeronese',
	città character varying(20) DEFAULT 'Verona',
	indirizzo character varying(80),
	numero_telefono char(10),
	giorno_chiusura varchar(10) NOT NULL,
	prezzo integer NOT NULL DEFAULT 10,
	PRIMARY KEY (nome, città)
)

CREATE TABLE public.mostra (
	titolo character varying(30),
	inizio date,
	fine date NOT NULL,
	museo character varying(30),
	città character varying(20),
	prezzo integer,
	PRIMARY KEY (titolo, inizio),
	FOREIGN KEY (museo, città) REFERENCES public.museo(nome, città)
)

CREATE TABLE public.opera (
	nome character varying(30),
	cognomeAutore character varying(20),
	nomeAutore character varying(20),
	museo character varying(30),
	città character varying(20),
	epoca character varying(30),
	anno character varying(4),
	PRIMARY KEY (nome, cognomeAutore, nomeAutore),
	FOREIGN KEY (museo, città) REFERENCES public.museo(nome, città)
)

CREATE DOMAIN giorniSettimana AS CHAR (3)
CHECK(VALUE IN('LUN','MAR','MER','GIO','VEN','SAB','DOM'));

CREATE TABLE public.orario (
	progressivo SERIAL UNIQUE NOT NULL PRIMARY KEY,
	museo character varying(30) NOT NULL,
	città character varying(20) NOT NULL,
	giorno giorniSettimana NOT NULL,
	orarioApertura TIME WITH TIME ZONE DEFAULT '09:00 CET',
	orarioChiusura TIME WITH TIME ZONE DEFAULT '19:00 CET',
	FOREIGN KEY (museo, città) REFERENCES public.museo(nome, città)
)

INSERT INTO public.museo (nome, città, indirizzo, numero_telefono, giorno_chiusura, prezzo) VALUES ('Arena', 'Verona', 'piazza Bra', '0458003204', 'martedì', 20)
INSERT INTO public.museo (nome, città, indirizzo, numero_telefono, giorno_chiusura, prezzo) VALUES ('CastelVecchio', 'Verona', 'Corso Castelvecchio', '045594734', 'lunedì', 15)

INSERT INTO public.mostra (titolo, inizio, fine, museo, città, prezzo) VALUES ('Mostra1', '2022-01-01', '2022-01-09', 'Arena', 'Verona', 10)
INSERT INTO public.mostra (titolo, inizio, fine, museo, città, prezzo) VALUES ('Mostra2', '2022-01-01', '2022-01-09', 'Arena', 'Verona', 10)
INSERT INTO public.mostra (titolo, inizio, fine, museo, città, prezzo) VALUES ('Mostra3', '2022-01-01', '2022-01-09', 'Arena', 'Verona', 10)
INSERT INTO public.opera (nome, cognomeautore, nomeautore, museo, città, epoca, anno) VALUES ('Opera1', 'Cognome', 'Nome', 'Arena', 'Verona', 'Vecchio', '1500')
INSERT INTO public.opera (nome, cognomeautore, nomeautore, museo, città, epoca, anno) VALUES ('Opera2', 'Cognome', 'Nome', 'Arena', 'Verona', 'Vecchio', '1500')
INSERT INTO public.opera (nome, cognomeautore, nomeautore, museo, città, epoca, anno) VALUES ('Opera3', 'Cognome', 'Nome', 'Arena', 'Verona', 'Vecchio', '1500')

ALTER TABLE public.museo
ADD COLUMN sitoInternet VARCHAR(100)

 
ALTER TABLE public.mostra
RENAME prezzo TO prezzoIntero
ALTER TABLE public.mostra
ADD COLUMN prezzoRidotto integer NOT NULL DEFAULT 5
CHECK (prezzoRidotto < prezzoIntero)

UPDATE public.museo
SET prezzo = prezzo + 1

UPDATE public.mostra
SET prezzoRidotto = prezzoRidotto + 1
WHERE PrezzoIntero < 15