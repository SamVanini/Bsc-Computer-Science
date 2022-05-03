-- Esercizio 1
SELECT DISTINCT P.nome, P.cognome, P.telefono
FROM InsErogato AS IE JOIN Docenza AS D ON IE.id = D.id_inserogato JOIN Persona AS P ON P.id = D.id_persona
WHERE IE.id_corsostudi = 4 AND IE.modulo = 0 AND IE.annoaccademico = '2009/2010' 

EXCEPT

SELECT DISTINCT P.nome, P.cognome, P.telefono
FROM InsErogato AS IE JOIN Docenza AS D ON IE.id = D.id_inserogato JOIN Persona AS P ON P.id = D.id_persona JOIN Insegn AS I ON I.id = IE.id_insegn
WHERE IE.nomeunita ILIKE 'programmazione' 
ORDER BY P.cognome

-- Esercizio 2
SELECT F.nome, COUNT(IE.modulo) AS UnitàLogistiche , SUM(IE.crediti) AS Crediti
FROM InsErogato AS IE JOIN Facolta AS F ON IE.id_facolta = F.id
WHERE IE.annoaccademico = '2010/2011' AND IE.modulo < 0
GROUP BY F.nome

-- Esercizio 3
CREATE TEMP VIEW OrePerDocente(id_docente, cognome_docente, nome_docente, oreleztot, id_facolta) AS
    SELECT P.id, P.cognome, P.nome, SUM(D.orelez), IE.id_facolta
    FROM Persona AS P
        JOIN Docenza AS D
            ON D.id_persona = P.id
        JOIN InsErogato AS IE
            ON IE.id = D.id_inserogato
    WHERE
        IE.annoaccademico = '2009/2010'
    GROUP BY P.id, P.cognome, P.cognome, IE.id_facolta;

SELECT P.cognome, P.nome, F.nome, OPD.oreleztot
FROM Persona AS P
    JOIN OrePerDocente AS OPD
        ON OPD.id_docente = P.id
    JOIN Facolta AS F
        ON F.id = OPD.id_facolta
WHERE
    OPD.oreleztot = ANY(
        SELECT MAX(OPD.oreleztot)
        FROM OrePerDocente AS OPD
        WHERE OPD.id_facolta = F.id
    )
ORDER BY P.cognome;

-- Esercizio 4
SELECT I.nomeins, Discr.nome
FROM InsErogato AS IE JOIN Insegn AS I ON I.id = IE.id_insegn JOIN Docenza AS D ON D.id_inserogato = IE.id JOIN Discriminante AS Discr ON Discr.id = IE.id_discriminante JOIN Persona AS P ON P.id = D.id_persona
WHERE IE.modulo = 0 AND IE.id_corsostudi = 240 AND IE.annoaccademico = '2009/2010' AND P.nome NOT IN ('Roberto', 'Alberto', 'Massimo', 'Luca')

INTERSECT

SELECT I.nomeins, Discr.nome
FROM InsErogato AS IE JOIN Insegn AS I ON I.id = IE.id_insegn JOIN Docenza AS D ON D.id_inserogato = IE.id JOIN Discriminante AS Discr ON Discr.id = IE.id_discriminante JOIN Persona AS P ON P.id = D.id_persona
WHERE IE.modulo = 0 AND IE.id_corsostudi = 240 AND IE.annoaccademico = '2010/2011' AND P.nome NOT IN ('Roberto', 'Alberto', 'Massimo', 'Luca')
ORDER BY nomeins;

-- Alternativa

CREATE TEMP VIEW ViewQuery4(ie_id, nomeins, id_discriminante, annoaccademico) AS
    SELECT IE.id, I.nomeins, IE.id_discriminante, IE.annoaccademico
    FROM InsErogato AS IE
        JOIN Insegn AS I
            ON I.id = IE.id_insegn
    WHERE
        IE.modulo = 0 AND
        IE.id_corsostudi = 240;

SELECT VQ.nomeins, Discr.nome
FROM ViewQuery4 AS VQ
    JOIN Docenza AS D
        ON D.id_inserogato = VQ.ie_id
    JOIN Discriminante AS Discr
        ON Discr.id = VQ.id_discriminante
WHERE
    VQ.annoaccademico = '2009/2010' AND
    D.id_persona = ANY(
        SELECT P.id
        FROM Persona AS P
        WHERE
            P.nome NOT IN ('Roberto', 'Alberto', 'Massimo', 'Luca')
    )

INTERSECT

SELECT VQ.nomeins, Discr.nome
FROM ViewQuery4 AS VQ
    JOIN Docenza AS D
        ON D.id_inserogato = VQ.ie_id
    JOIN Discriminante AS Discr
        ON Discr.id = VQ.id_discriminante
WHERE
    VQ.annoaccademico = '2010/2011' AND
    D.id_persona = ANY(
        SELECT P.id
        FROM Persona AS P
        WHERE
            P.nome NOT IN ('Roberto', 'Alberto', 'Massimo', 'Luca')
    )

ORDER BY nomeins;

-- Esercizio 5

SELECT nomeins, nomeunita
FROM( 
    SELECT IE.id, I.nomeins, IE.nomeunita
    FROM InsErogato AS IE JOIN Insegn AS I ON I.id = IE.id_insegn JOIN Lezione AS L ON L.id_inserogato = IE.id
    WHERE IE.id_corsostudi =  420 AND
            IE.annoaccademico = '2010/2011' AND
            IE.modulo < 0 AND (L.giorno = 3 OR L.giorno = 2)

    EXCEPT
    (
        SELECT IE.id, I.nomeins, IE.nomeunita
        FROM InsErogato AS IE JOIN Insegn AS I ON I.id = IE.id_insegn JOIN Lezione AS L ON L.id_inserogato = IE.id
        WHERE IE.id_corsostudi =  420 AND IE.annoaccademico = '2010/2011' AND IE.modulo < 0 AND (L.giorno = 3)

        INTERSECT

        SELECT IE.id, I.nomeins, IE.nomeunita
        FROM InsErogato AS IE JOIN Insegn AS I ON I.id = IE.id_insegn JOIN Lezione AS L ON L.id_inserogato = IE.id
        WHERE IE.id_corsostudi =  420 AND IE.annoaccademico = '2010/2011' AND IE.modulo < 0 AND (L.giorno = 2)
    )
) AS Risultato
ORDER BY nomeins

-- Esercizio 6
CREATE TEMP VIEW ViewQuery6 (id_docente, id_insegn, anno) AS
    SELECT P.id, IE.id_insegn, SUBSTRING(IE.annoaccademico, 1, 4)::INT 
    FROM InsErogato AS IE JOIN CorsoInFacolta AS CIF ON IE.id_corsostudi = CIF.id_corsostudi JOIN Facolta AS F ON CIF.id_facolta = F.id JOIN Docenza AS D ON D.id_inserogato = IE.id JOIN Persona AS P ON P.id = D.id_persona
    WHERE F.nome ILIKE 'scienze matematiche fisiche e naturali' AND IE.modulo = 0

SELECT DISTINCT VQ.id_insegn, I.nomeins, VQ.id_insegn, P.nome, P.cognome
FROM ViewQuery6 AS VQ JOIN ViewQuery6 AS VQ1 ON VQ.id_docente = VQ1.id_docente AND VQ1.id_insegn = VQ.id_insegn JOIN Persona AS P ON VQ.id_docente = P.id JOIN Insegn AS I ON I.id = VQ.id_insegn
WHERE VQ.anno = (VQ1.anno + 1)
ORDER BY I.nomeins

-- Esercizio 7

SELECT P.id, P.nome, P.cognome, COUNT(IE.id) AS Insegnamenti
FROM InsErogato AS IE JOIN Docenza AS D ON D.id_inserogato = IE.id JOIN Persona AS P ON P.id = D.id_persona
WHERE IE.annoaccademico = '2005/2006'
GROUP BY P.id

UNION 
(
    SELECT P.id, P.nome, P.cognome, 0
    FROM Persona AS P JOIN Docenza AS D ON P.id = D.id_persona

    EXCEPT 
    (
        SELECT P.id, P.nome, P.cognome, 0
        FROM InsErogato AS IE JOIN Docenza AS D ON D.id_inserogato = IE.id JOIN Persona AS P ON P.id = D.id_persona
        WHERE IE.annoaccademico = '2005/2006'
    )
)

-- Alternativa

CREATE TEMP VIEW ViewQuery7(docente_id, docente_cognome, docente_nome, conteggio) AS
    SELECT P.id, P.cognome, P.nome, COUNT(*)
    FROM Persona AS P
        JOIN Docenza AS D
            ON D.id_persona = P.id
        JOIN InsErogato AS IE
            ON IE.id = D.id_inserogato
    WHERE
        IE.annoaccademico = '2005/2006'
    GROUP BY P.id, P.cognome, P.nome

(
    SELECT DISTINCT P.id, P.cognome, P.nome, 0
    FROM Persona AS P
        JOIN Docenza AS D
            ON D.id_persona = P.id

    EXCEPT

    SELECT VQ.docente_id, VQ.docente_cognome, VQ.docente_nome, 0
    FROM ViewQuery7 AS VQ
)

UNION

SELECT VQ.docente_id, VQ.docente_cognome, VQ.docente_nome, VQ.conteggio
FROM ViewQuery7 AS VQ;