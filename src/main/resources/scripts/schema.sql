-- Deletando as tabelas para agilizar no processo de desenvolvimento do script

DROP TABLE IF EXISTS Localidade CASCADE;
DROP TABLE IF EXISTS Modalidade CASCADE;
DROP TABLE IF EXISTS Partida CASCADE;
DROP TABLE IF EXISTS Delegacao CASCADE;
DROP TABLE IF EXISTS Pais CASCADE;
DROP TABLE IF EXISTS Arbitro CASCADE;
DROP TABLE IF EXISTS Arbitro_Partida CASCADE;
DROP TABLE IF EXISTS Atleta CASCADE;
DROP TABLE IF EXISTS Atleta_Partida CASCADE;
DROP TABLE IF EXISTS TimeOlimpico CASCADE;
DROP TABLE IF EXISTS Time_Partida CASCADE;
DROP TABLE IF EXISTS Atleta_Time CASCADE;
DROP TABLE IF EXISTS Atleta_Modalidade CASCADE;

---------------------------- TABLES ----------------------------\



CREATE TABLE Localidade (
  Nome     VARCHAR(80),
  Endereco VARCHAR(250),
  CONSTRAINT PK_Localidade PRIMARY KEY (Nome)
);

CREATE TABLE Modalidade (
  Nome             VARCHAR(80),
  Genero           VARCHAR(9),
  UnidadePontuacao VARCHAR(15),
  Descricao        VARCHAR(250),
  Tipo             VARCHAR(10),

  CONSTRAINT PK_Modalidade PRIMARY KEY (Nome),
  CONSTRAINT CK_Modalidade1 CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO', 'MISTO')),
  CONSTRAINT CK_Modalidade2 CHECK (UPPER(Tipo) IN ('INDIVIDUAL', 'TIME'))
);

CREATE TABLE Partida (
  Identificador SERIAL,
  DataHora      TIMESTAMP   NOT NULL,
  NomeLocal     VARCHAR(80) NOT NULL,
  Completada    BOOL        NOT NULL,
  Observacao    VARCHAR(250),
  Tipo          VARCHAR(10) NOT NULL,
  Modalidade    VARCHAR(80) NOT NULL,

  CONSTRAINT PK_Partida PRIMARY KEY (Identificador),
  CONSTRAINT FK_Partida FOREIGN KEY (NomeLocal) REFERENCES Localidade (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_Partida UNIQUE (NomeLocal, DataHora),
  CONSTRAINT CK_Partida CHECK (UPPER(Tipo) IN ('INDIVIDUAL', 'TIME'))
);

CREATE TABLE Delegacao (
  Nome VARCHAR(120),

  CONSTRAINT PK_Delegacao PRIMARY KEY (Nome)
);

CREATE TABLE Pais (
  Nome       VARCHAR(120),
  Abreviacao CHAR(3),
  Bandeira   VARCHAR(60),
  Delegacao  VARCHAR(120),

  CONSTRAINT PK_Pais PRIMARY KEY (Nome),
  CONSTRAINT FK_Pais FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Arbitro (
  Identificador SERIAL,
  Nome          VARCHAR(80)  NOT NULL,
  Pais          VARCHAR(120) NOT NULL,

  CONSTRAINT PK_Arbitro PRIMARY KEY (Identificador),
  CONSTRAINT FK_Arbitro FOREIGN KEY (Pais) REFERENCES Pais (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_Arbitro UNIQUE (Nome, Pais)
);

CREATE TABLE Arbitro_Partida (
  Partida INT,
  Arbitro INT,

  CONSTRAINT PK_ArbPart PRIMARY KEY (Partida, Arbitro),
  CONSTRAINT FK_ArbPart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ArbPart2 FOREIGN KEY (Arbitro) REFERENCES Arbitro (Identificador) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Atleta (
  Identificador  SERIAL,
  Nome           VARCHAR(120) NOT NULL,
  Delegacao      VARCHAR(120) NOT NULL,
  DataNascimento DATE,
  Altura         FLOAT,
  Peso           FLOAT,
  Genero         VARCHAR(9),
  Foto           VARCHAR(60),

  CONSTRAINT PK_Atleta PRIMARY KEY (Identificador),
  CONSTRAINT FK_Atleta FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_Atleta UNIQUE (Nome, Delegacao),
  CONSTRAINT CK_Atleta CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO'))
);


CREATE TABLE Atleta_Partida (
  Partida       INT,
  Atleta        INT,
  Classificacao INT,
  Pontuacao     FLOAT,

  CONSTRAINT PK_AtlPart PRIMARY KEY (Partida, Atleta),
  CONSTRAINT FK_AtlPart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_AtlPart2 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE TimeOlimpico (
  Identificador         SERIAL,
  Nome                  VARCHAR(120) NOT NULL,
  Delegacao             VARCHAR(120) NOT NULL,
  Modalidade            VARCHAR(80),
  Categoria             VARCHAR(10),
  Genero                VARCHAR(10),
  MedalhaGanha          VARCHAR(6),
  GrauDeComprometimento VARCHAR(10),

  CONSTRAINT PK_Time PRIMARY KEY (Identificador),
  CONSTRAINT FK_Time1 FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_Time2 FOREIGN KEY (Modalidade) REFERENCES Modalidade (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CK_Time1 CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO', 'MISTO')),
  CONSTRAINT CK_Time2 CHECK (UPPER(MedalhaGanha) IN ('OURO', 'PRATA', 'BRONZE')),
  CONSTRAINT UN_Time UNIQUE (Nome, Delegacao),
  CONSTRAINT UN_Time1 UNIQUE (MedalhaGanha, Modalidade)
);

CREATE TABLE Time_Partida (
  Partida       INT,
  TimeOlimp     INT,
  Classificacao INT,
  Pontuacao     FLOAT,
  CONSTRAINT PK_TimePart PRIMARY KEY (Partida, TimeOlimp),
  CONSTRAINT FK_TimePart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_TimePart2 FOREIGN KEY (TimeOlimp) REFERENCES TimeOlimpico (Identificador) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Atleta_Time (
  Atleta    INT,
  TimeOlimp INT,

  CONSTRAINT PK_AtlTime PRIMARY KEY (Atleta, TimeOlimp),
  CONSTRAINT FK_AltTime1 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_AltTime2 FOREIGN KEY (TimeOlimp) REFERENCES TimeOlimpico (Identificador) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Atleta_Modalidade (
  Atleta       INT,
  Modalidade   VARCHAR(80),
  Categoria    VARCHAR(10),
  MedalhaGanha VARCHAR(6),

  CONSTRAINT PK_AtlMod PRIMARY KEY (Atleta, Modalidade),
  CONSTRAINT FK_AtlMod1 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_AtlMod2 FOREIGN KEY (Modalidade) REFERENCES Modalidade (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CK_AtlMod CHECK (UPPER(MedalhaGanha) IN ('OURO', 'PRATA', 'BRONZE')),
  CONSTRAINT UN_AtlMod UNIQUE (MedalhaGanha, Modalidade)
);

---------------------------- VIEWS -------------------------------
DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION "uuid-ossp";
CREATE OR REPLACE VIEW Medalha AS
  SELECT
    uuid_generate_v4() AS uuid,
    T.*
  FROM (SELECT
          NULL         AS Time,
          Atleta,
          Nome,
          Modalidade,
          MedalhaGanha AS Medalha
        FROM Atleta_Modalidade
          JOIN Atleta ON Atleta = Identificador
        WHERE MedalhaGanha IS NOT NULL

        UNION

        SELECT
          Identificador AS Time,
          NULL          AS Atleta,
          Nome,
          Modalidade,
          MedalhaGanha  AS Medalha
        FROM TimeOlimpico
        WHERE MedalhaGanha IS NOT NULL) T;


CREATE OR REPLACE VIEW Atleta_Time_Info AS
  SELECT Atleta_Time.*, Atleta.Nome as NomeAtleta, TimeOlimpico.Nome as NomeTime, Atleta.Delegacao
  FROM Atleta_Time JOIN Atleta ON Atleta_Time.Atleta = Atleta.Identificador
  JOIN TimeOlimpico ON Atleta_Time.TimeOlimp = TimeOlimpico.Identificador
---------------------------- TRIGGERS ----------------------------

SELECT * FROM Atleta_Time_Info