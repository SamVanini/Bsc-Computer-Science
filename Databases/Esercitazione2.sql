SELECT nome, città, giornochiusura
FROM museo
WHERE LOWER(città) = 'verona'

SELECT titolo || ' ' || città as titolo_città
FROM mostra
WHERE LOWER(titolo) LIKE 'R%'

SELECT titolo, (fine - CURRENT_DATE) AS giorni_rimasti
FROM mostra
WHERE  fine > CURRENT_DATE 

SELECT orario.orarioapertura, orario.orariochiusura
FROM museo, orario
WHERE orario.giorno = 'MAR' AND museo.giornochiusura <> 'MAR' AND museo.nome = orario.museo AND museo.città = orario.città

SELECT *
FROM mostra
WHERE prezzoridotto IS NULL

SELECT *
FROM mostra
WHERE  inizio <= CURRENT_DATE AND fine >= CURRENT_DATE
ORDER BY inizio, fine

SELECT SUM(fine - inizio)
FROM mostra
WHERE LOWER(città) = 'verona' AND LOWER(museo) = 'arena'

SELECT AVG(orariochiusura::time - orarioapertura::time)
FROM orario
WHERE LOWER(città) = 'verona' AND LOWER(museo) = 'arena'

SELECT DISTINCT cognomeautore, nomeautore -- COUNT(DISTINCT cognomeautore)
FROM opera