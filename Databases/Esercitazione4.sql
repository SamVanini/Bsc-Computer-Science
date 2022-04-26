-- Esercizio 1
SELECT Risultato.id_persona, P.nome, P.cognome
FROM (
    SELECT RisultatoDocenti.id_persona
    FROM (
        SELECT DISTINCT D.id_persona, IE.id_corsostudi
        FROM Docenza AS D
            JOIN InsErogato AS IE
                ON IE.id = D.id_inserogato
        WHERE
            IE.annoaccademico = '2010/2011'
    ) AS RisultatoDocenti(id_persona)
    GROUP BY RisultatoDocenti.id_persona
    HAVING COUNT(*) >= 2
    ) AS Risultato(id_persona)
    JOIN Persona AS P
        ON P.id = Risultato.id_persona
ORDER BY Risultato.id_persona;

-- Esercizio 2
SELECT DISTINCT R1.id_persona, P.cognome, P.nome
FROM (
    SELECT DISTINCT D.id_persona, I.nomeins
    FROM Docenza AS D
        JOIN InsErogato AS IE
            ON IE.id = D.id_inserogato
        JOIN Insegn AS I
            ON I.id = IE.id_insegn    
    WHERE
        IE.annoaccademico = '2010/2011'
) AS R1(id_persona, nomeins)
    JOIN Persona AS P
        ON P.id = R1.id_persona
WHERE
    ROW(R1.id_persona, R1.nomeins) NOT IN (
        SELECT DISTINCT D.id_persona, I.nomeins
        FROM Docenza AS D
            JOIN InsErogato AS IE
                ON IE.id = D.id_inserogato
            JOIN Insegn AS I
                ON I.id = IE.id_insegn    
        WHERE
            IE.annoaccademico = '2009/2010'
    )

-- Esercizio 3
SELECT PL.abbreviazione, PD.discriminante, PD.inizio, PD.fine, R1.lezInPeriodo
FROM PeriodoDid AS PD
    JOIN PeriodoLez AS PL
        ON PL.id = PD.id
    JOIN (
        SELECT IIP.id_periodoLez, COUNT(*) -- Questi sono conteggi in più che non servono
        FROM InsInPeriodo AS IIP
        GROUP BY IIP.id_periodoLez
    ) AS R1(id_periodoLez, lezInPeriodo)
        ON R1.id_periodoLez = PL.id
WHERE
    PD.annoaccademico = '2010/2011' AND
    (
        PD.descrizione ILIKE 'I semestre%' OR
        PD.descrizione ILIKE 'Primo semestre'
    )
ORDER BY PD.inizio, PD.fine;

-- Esercizio 4
SELECT CS.nome, COUNT(IE.id) AS num_ins
FROM CorsoStudi AS CS
    JOIN InsErogato AS IE
        ON IE.id_corsostudi = CS.id
WHERE
    CS.id NOT IN (
        SELECT CS1.id
        FROM CorsoStudi AS CS1
            JOIN CorsoInFacolta AS CIF
                ON CS1.id = CIF.id_corsostudi
            JOIN Facolta AS F
                ON F.id = CIF.id_facolta
        WHERE
            F.nome = 'Medicina e Chirurgia'
    )
    AND IE.annoaccademico = '2010/2011'
    AND IE.hamoduli <> '0'
GROUP BY CS.nome
ORDER BY CS.nome;