-- Esercizio 1

BEGIN;
INSERT INTO public.museo(nome, città, indirizzo, numero_telefono, giorno_chiusura, prezzo) VALUES ('Museo', 'Verona', 'una', '0458003204', 'martedì', 20)
COMMIT;

-- Livelli di isolamento
-- Essendo una sola operazione non serve un livello di isolamento
-- La seconda operazione verrà abortita

-- Esercizio 2

BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SELECT * FROM Museo
WHERE prezzo <> ceil ( prezzo ) AND citta ILIKE 'Verona';
UPDATE Museo SET prezzo = round( prezzo * 1.10, 2 )
WHERE prezzo <> ceil ( prezzo ) AND citta ILIKE 'Verona' ;
COMMIT;

BEGIN;
UPDATE Museo SET prezzo = round ( prezzo * 1.10, 2 )
WHERE citta ILIKE 'Verona';
COMMIT;

-- Livelli di isolamento
-- Serializable: Garantisce che un’intera transazione è eseguita in un qualche ordine sequenziale rispetto ad altre transazioni: completo isolamento da transazioni concorrenti.
-- Repeatable Read: Garantisce che i dati letti durante la transazione non cambieranno a causa di altre transazioni: rifacendo la lettura dei medesimi dati, si ottengono sempre gli stessi.

-- Esercizio 3

INSERT INTO Mostra( titolo, inizio, fine, museo, 
 citta, prezzoIntero, prezzoRidotto ) 
VALUES( 'Mostra Scaligera', '2017-02-12', '2017-11-30',
 'CastelVecchio', 'Verona', 40.00 , 20.00) ;

BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SELECT AVG ( prezzoIntero ) FROM Mostra WHERE citta ILIKE 'Verona';
SELECT AVG ( prezzoRidotto ) FROM Mostra WHERE citta ILIKE 'Verona';
COMMIT;

-- Livello di isolamento
-- Serializable: Garantisce che un’intera transazione è eseguita in un qualche ordine sequenziale rispetto ad altre transazioni: completo isolamento da transazioni concorrenti.

-- Esercizio 4

UPDATE Mostra SET prezzoIntero = round ( prezzoIntero * 1.10 , 2) 
WHERE citta ILIKE 'Verona';

UPDATE Mostra SET prezzoRidotto = round ( prezzoRidotto * 0.95 , 2)
WHERE citta ILIKE 'Verona';

-- Livello di isolamento
-- Lavorando su due colonne diverse non serve un livello di isolamento specifico

-- Esercizio 5

BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

INSERT INTO Museo( nome, citta, prezzo ) 
 SELECT 'Museo Preistorico di Verona', 'Verona', AVG( prezzo ) 
 FROM Museo WHERE citta ILIKE 'Vicenza';

COMMIT;

BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

INSERT INTO Musei( nome, citta, prezzo ) 
SELECT 'Museo provinciale di Vicenza', 'Vicenza', AVG ( prezzo ) 
FROM Musei WHERE citta ILIKE 'Verona';

COMMIT;

-- Livello di isolamento
-- Serializable: Garantisce che un’intera transazione è eseguita in un qualche ordine sequenziale rispetto ad altre transazioni: completo isolamento da transazioni concorrenti.