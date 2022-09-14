CREATE DATABASE Bancos;

DROP TABLE IF EXISTS  Billetes;
CREATE TABLE Billetes (
    denominacion INTEGER PRIMARY KEY,
    existencia INTEGER NOT NULL DEFAULT 0,
    fecha SMALLDATETIME DEFAULT CURRENT_TIMESTAMP,
);

INSERT INTO Billetes (denominacion) VALUES (1);
INSERT INTO Billetes (denominacion) VALUES (2);
INSERT INTO Billetes (denominacion) VALUES (5);
INSERT INTO Billetes (denominacion) VALUES (10);
INSERT INTO Billetes (denominacion) VALUES (20);
INSERT INTO Billetes (denominacion) VALUES (50);
INSERT INTO Billetes (denominacion) VALUES (100);
INSERT INTO Billetes (denominacion) VALUES (200);
INSERT INTO Billetes (denominacion) VALUES (500);
INSERT INTO Billetes (denominacion) VALUES (1000);