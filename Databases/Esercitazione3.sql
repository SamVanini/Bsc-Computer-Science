-- Esercizio 1
SELECT * 
FROM CorsoStudi
--Esercizio 2
SELECT nome, codice, indirizzo, id_preside_persona
FROM Facolta
--Esercizio 3
SELECT DISTINCT CS.nome, F.nome
FROM InsErogato AS IE INNER JOIN CorsoInFacolta AS CIF ON (IE.id_corsostudi = CIF.id_corsostudi) 
    JOIN Facolta AS F ON (CIF.id_facolta = F.id)
    JOIN CorsoStudi AS CS ON (CIF.id_corsostudi = CS.id)
WHERE IE.annoaccademico = '2010/2011'
ORDER BY CS.nome
--Esercizio 4
SELECT CS.id, CS.nome, CS.abbreviazione 
FROM CorsoStudi AS CS INNER JOIN CorsoInFacolta AS CIF ON  (CIF.id_corsostudi = CS.id)
    JOIN Facolta AS F ON (CIF.id_facolta = F.id)
WHERE LOWER(F.nome) = 'medicina e chirurgia'
--Esercizio 5
SELECT CS.id, CS.nome, CS.abbreviazione 
FROM CorsoStudi AS CS
WHERE CS.nome ILIKE '%lingue%'
--Esercizio 6
SELECT DISTINCT CS.sede
FROM CorsoStudi AS CS
--Esercizio 7
SELECT DISTINCT I.nomeins, Discr.descrizione, IE.nomemodulo, IE.modulo
FROM InsErogato AS IE JOIN CorsoInFacolta AS CIF ON CIF.id_corsostudi = IE.id_corsostudi
    JOIN Facolta AS F ON F.id = CIF.id_facolta 
    JOIN Insegn AS I ON I.id = IE.id_insegn
    JOIN Discriminante AS Discr ON Discr.id = IE.id_discriminante
WHERE IE.annoaccademico = '2010/2011' AND F.nome = 'Economia' AND IE.modulo > 0;
-- Esercizio 8
SELECT DISTINCT I.nomeins, IE.id_discriminante, D.descrizione
FROM InsErogato AS IE INNER JOIN Insegn AS I ON (IE.id_insegn = I.id)
    JOIN Discriminante AS D ON (IE.id_discriminante = D.id)
WHERE IE.annoaccademico = '2009/2010' AND IE.Crediti IN (3, 5, 12) AND IE.modulo = 0
ORDER BY IE.id_discriminante
-- Esercizio 9
SELECT IE.id, I.nomeins, D.descrizione
FROM InsErogato AS IE INNER JOIN Insegn AS I ON (IE.id_insegn = I.id)
    JOIN Discriminante AS D ON (IE.id_discriminante = D.id)
WHERE IE.annoaccademico = '2008/2009' AND IE.Crediti > 9 AND IE.modulo = 0
ORDER BY I.nomeins
LIMIT 5 OFFSET 1023 -- Visualizza solo 5 righe a partire da riga 1023
-- Esercizio 10
SELECT I.nomeins, D.descrizione, IE.crediti, IE.annierogazione
FROM InsErogato AS IE INNER JOIN Insegn AS I ON (IE.id_insegn = I.id)
    JOIN Discriminante AS D ON (IE.id_discriminante = D.id)
    JOIN CorsoStudi AS CS ON (IE.id_corsostudi = CS.id)
WHERE IE.annoaccademico = '2010/2011' AND IE.modulo = 0 AND LOWER(CS.nome) = 'laurea in informatica'
ORDER BY I.nomeins
-- Esercizio 11
SELECT MAX(IE.Crediti) AS massimo
FROM InsErogato AS IE
WHERE IE.annoaccademico = '2010/2011'
-- Esercizio 12
SELECT IE.annoaccademico, MAX(IE.Crediti) AS massimo, MIN(IE.Crediti) AS minimo
FROM InsErogato AS IE
GROUP BY IE.annoaccademico
-- Esercizio 13 
SELECT CS.nome, IE.annoaccademico, SUM(IE.Crediti), MAX(IE.Crediti), MIN(IE.Crediti)
FROM InsErogato AS IE INNER JOIN CorsoStudi AS CS ON (IE.id_corsostudi = CS.id)
WHERE IE.modulo = 0
GROUP BY CS.nome, IE.annoaccademico, CS.id
-- Esercizio 14 
SELECT CS.nome, F.nome, COUNT(IE.id_insegn) AS totale
FROM InsErogato AS IE INNER JOIN Facolta AS F ON (IE.id_facolta = F.id) 
    JOIN CorsoStudi AS CS ON (IE.id_corsostudi = CS.id)
WHERE IE.modulo = 0 AND LOWER(F.nome) = 'scienze matematiche fisiche e naturali' AND IE.annoaccademico = '2009/2010'
GROUP BY CS.nome, F.nome
-- ALTERNATIVA CON IL GIUSTO NUMERO DI RIGHE
SELECT CS.nome, COUNT(IE.id_insegn) AS totale
FROM InsErogato AS IE INNER JOIN CorsoInFacolta AS CIF ON (IE.id_corsostudi = CIF.id_corsostudi) 
    JOIN Facolta AS F ON (CIF.id_facolta = F.id)
    JOIN CorsoStudi AS CS ON (CIF.id_corsostudi = CS.id)
WHERE IE.modulo = 0 AND LOWER(F.nome) = 'scienze matematiche fisiche e naturali' AND IE.annoaccademico = '2009/2010'
GROUP BY CS.nome, CS.id
-- Esercizio 15 
SELECT DISTINCT CS.nome, CS.durataAnni
FROM InsErogato AS IE INNER jOIN CorsoStudi AS CS ON (IE.id_corsostudi = CS.id)
WHERE IE.annoaccademico = '2010/2011' AND (IE.Crediti IN (4, 6, 8, 10, 12) OR IE.creditilab BETWEEN 10 AND 15)
ORDER BY CS.nome