-- Spese
DROP TABLE Spese IF EXISTS;
CREATE TABLE Spese (
    Data DATE,
    Voce varchar(30),
    Importo DECIMAL (6,2),
    PRIMARY KEY (Data, Voce)
);