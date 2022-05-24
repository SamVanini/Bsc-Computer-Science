-- Esercizio 1
BEGIN;
INSERT INTO public.museo(nome, città, indirizzo, numero_telefono, giorno_chiusura, prezzo) VALUES ('Museo', 'Verona', 'una', '0458003204', 'martedì', 20)
COMMIT;

-- Livelli di isolamento
-- Essendo una sola operazione non serve un livello di isolamento
-- La seconda operazione verrà abortita

-- Esercizio 2
BEGIN;

SELECT nome, prezzo
FROM museo
WHERE RIGHT(prezzo::varchar, 2)::int <> 0;

UPDATE public.museo
SET prezzo = prezzo + (prezzo * 0.1)
WHERE RIGHT(prezzo::varchar, 2)::int <> 0;

UPDATE public.museo
SET prezzo = prezzo + (prezzo * 0.1);

COMMIT;

-- Livelli di isolamento
-- Serializable: Garantisce che un’intera transazione è eseguita in un qualche ordine sequenziale rispetto ad altre transazioni: completo isolamento da transazioni concorrenti.
-- Repeatable Read: Garantisce che i dati letti durante la transazione non cambieranno a causa di altre transazioni: rifacendo la lettura dei medesimi dati, si ottengono sempre gli stessi.

-- Esercizio 3
BEGIN;
INSERT INTO public.mostra (titolo, inizio, fine, museo, città, prezzoIntero, prezzoRidotto) VALUES ('Mostra4', '2022-01-01', '2022-01-09', 'Arena', 'Verona', 40, 20);

SELECT AVG(prezzoIntero)
FROM public.mostra;

SELECT AVG(prezzoRidotto)
FROM public.mostra;

COMMIT;

-- Livello di isolamento
-- Serializable: Garantisce che un’intera transazione è eseguita in un qualche ordine sequenziale rispetto ad altre transazioni: completo isolamento da transazioni concorrenti.
-- Repeatable Read: Garantisce che i dati letti durante la transazione non cambieranno a causa di altre transazioni: rifacendo la lettura dei medesimi dati, si ottengono sempre gli stessi.

-- Esercizio 4
BEGIN;

UPDATE public.mostra
SET prezzoIntero = prezzoIntero + (prezzoIntero * 0.1)
WHERE città ILIKE 'verona';

UPDATE public.mostra
SET prezzoRidotto = prezzoRidotto - (prezzoRidotto * 0.05);

COMMIT;

-- Livello di isolamento
-- Lavorando su due colonne diverse non serve un livello di isolamento specifico

-- Esercizio 5
BEGIN;

SELECT AVG(prezzo)
FROM public.museo;
WHERE città ILIKE 'vicenza';

INSERT INTO public.museo(nome, città, indirizzo, numero_telefono, giorno_chiusura, prezzo) VALUES ('Museo moderno’', 'Verona', 'una', '0458003204', 'martedì', (
    SELECT AVG(prezzo)
    FROM public.museo;
    WHERE città ILIKE 'vicenza';
))

COMMIT;