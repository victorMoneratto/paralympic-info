-- Deletando as tabelas para agilizar no processo de desenvolvimento do script
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE Localidade CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Modalidade CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Partida CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Delegacao CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Pais CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Arbitro CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Arbitro_Partida CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Atleta CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Atleta_Partida CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE TimeOlimpico CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Time_Partida CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Atleta_Time CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE 'DROP TABLE Atleta_Modalidade CASCADE CONSTRAINTS';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942
  THEN
    RAISE;
  END IF;
END;
---------------------------- TABLES ----------------------------

CREATE TABLE Localidade (
  Nome     VARCHAR2(80),
  Endereco VARCHAR2(250),
  CONSTRAINT PK_Localidade PRIMARY KEY (Nome)
);

CREATE TABLE Modalidade (
  Nome             VARCHAR2(80),
  Genero           VARCHAR2(9),
  UnidadePontuacao VARCHAR2(15),
  Descricao        VARCHAR2(250),
  Tipo             VARCHAR2(10),

  CONSTRAINT PK_Modalidade PRIMARY KEY (Nome),
  CONSTRAINT CK_Modalidade1 CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO', 'MISTO')),
  CONSTRAINT CK_Modalidade2 CHECK (UPPER(Tipo) IN ('INDIVIDUAL', 'TIME'))
);

CREATE TABLE Partida (
  Identificador INT,
  DataHora      DATE         NOT NULL,
  NomeLocal     VARCHAR2(80) NOT NULL,
  Completada    CHARACTER(1) NOT NULL,
  Observacao    VARCHAR2(250),
  Tipo          VARCHAR2(10) NOT NULL,
  Modalidade    VARCHAR2(80) NOT NULL,

  CONSTRAINT PK_Partida PRIMARY KEY (Identificador),
  CONSTRAINT FK_Partida FOREIGN KEY (NomeLocal) REFERENCES Localidade (Nome) ON DELETE CASCADE,
  CONSTRAINT UN_Partida UNIQUE (NomeLocal, DataHora),
  CONSTRAINT CK_Partida CHECK (UPPER(Tipo) IN ('INDIVIDUAL', 'TIME') AND (UPPER(Completada) IN ('S', 'N')) )
);

CREATE TABLE Delegacao (
  Nome VARCHAR2(120),

  CONSTRAINT PK_Delegacao PRIMARY KEY (Nome)
);

CREATE TABLE Pais (
  Nome       VARCHAR2(120),
  Abreviacao CHAR(3),
  Bandeira   VARCHAR2(60),
  Delegacao  VARCHAR2(120),

  CONSTRAINT PK_Pais PRIMARY KEY (Nome),
  CONSTRAINT FK_Pais FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE
);


CREATE TABLE Arbitro (
  Identificador INT,
  Nome          VARCHAR2(80)  NOT NULL,
  Pais          VARCHAR2(120) NOT NULL,

  CONSTRAINT PK_Arbitro PRIMARY KEY (Identificador),
  CONSTRAINT FK_Arbitro FOREIGN KEY (Pais) REFERENCES Pais (Nome) ON DELETE CASCADE,
  CONSTRAINT UN_Arbitro UNIQUE (Nome, Pais)
);

CREATE TABLE Arbitro_Partida (
  Partida INT,
  Arbitro INT,

  CONSTRAINT PK_ArbPart PRIMARY KEY (Partida, Arbitro),
  CONSTRAINT FK_ArbPart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE,
  CONSTRAINT FK_ArbPart2 FOREIGN KEY (Arbitro) REFERENCES Arbitro (Identificador) ON DELETE CASCADE
);

CREATE TABLE Atleta (
  Identificador  INT,
  Nome           VARCHAR2(120) NOT NULL,
  Delegacao      VARCHAR2(120) NOT NULL,
  DataNascimento DATE,
  Altura         FLOAT,
  Peso           FLOAT,
  Genero         VARCHAR2(9),
  Foto           VARCHAR2(60),

  CONSTRAINT PK_Atleta PRIMARY KEY (Identificador),
  CONSTRAINT FK_Atleta FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE,
  CONSTRAINT UN_Atleta UNIQUE (Nome, Delegacao),
  CONSTRAINT CK_Atleta CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO'))
);


CREATE TABLE Atleta_Partida (
  Partida       INT,
  Atleta        INT,
  Classificacao INT,
  Pontuacao     FLOAT,

  CONSTRAINT PK_AtlPart PRIMARY KEY (Partida, Atleta),
  CONSTRAINT FK_AtlPart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE,
  CONSTRAINT FK_AtlPart2 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE
);

CREATE TABLE TimeOlimpico (
  Identificador         INT,
  Nome                  VARCHAR2(120) NOT NULL,
  Delegacao             VARCHAR2(120) NOT NULL,
  Modalidade            VARCHAR2(80),
  Categoria             VARCHAR2(10),
  Genero                VARCHAR2(10),
  MedalhaGanha          VARCHAR2(6),
  GrauDeComprometimento VARCHAR2(10),

  CONSTRAINT PK_Time PRIMARY KEY (Identificador),
  CONSTRAINT FK_Time1 FOREIGN KEY (Delegacao) REFERENCES Delegacao (Nome) ON DELETE CASCADE,
  CONSTRAINT FK_Time2 FOREIGN KEY (Modalidade) REFERENCES Modalidade (Nome) ON DELETE CASCADE,
  CONSTRAINT CK_Time1 CHECK (UPPER(Genero) IN ('MASCULINO', 'FEMININO', 'MISTO')),
  CONSTRAINT CK_Time2 CHECK (UPPER(MedalhaGanha) IN ('OURO', 'PRATA', 'BRONZE')),
  CONSTRAINT UN_Time UNIQUE (Nome, Delegacao)
);

-- Esse "Unique Index" é uma solução alternativa ao uso de "constraint unique"
-- em duas colunas. Podemos com o índice ter tuplas com valores repetidos
-- apenas se uma das columas apresentar valor nulo.
-- Exemplo:
-- (1, 'A'), (1, NULL), (1, NULL), (2, 'B')
-- diferente da constraint unique, essas são tuplas válidas na tabela, porém
-- (1, 'A'), (1, 'A')
-- infringiria o índice.
CREATE UNIQUE INDEX UIDX_Time on TimeOlimpico
(nvl2(Modalidade, MedalhaGanha, null), nvl2(MedalhaGanha, Modalidade, null));

CREATE TABLE Time_Partida (
  Partida       INT,
  TimeOlimp     INT,
  Classificacao INT,
  Pontuacao     FLOAT,
  CONSTRAINT PK_TimePart PRIMARY KEY (Partida, TimeOlimp),
  CONSTRAINT FK_TimePart1 FOREIGN KEY (Partida) REFERENCES Partida (Identificador) ON DELETE CASCADE,
  CONSTRAINT FK_TimePart2 FOREIGN KEY (TimeOlimp) REFERENCES TimeOlimpico (Identificador) ON DELETE CASCADE
);


CREATE TABLE Atleta_Time (
  Atleta    INT,
  TimeOlimp INT,

  CONSTRAINT PK_AtlTime PRIMARY KEY (Atleta, TimeOlimp),
  CONSTRAINT FK_AltTime1 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE,
  CONSTRAINT FK_AltTime2 FOREIGN KEY (TimeOlimp) REFERENCES TimeOlimpico (Identificador) ON DELETE CASCADE
);


CREATE TABLE Atleta_Modalidade (
  Atleta       INT,
  Modalidade   VARCHAR2(80),
  Categoria    VARCHAR2(10),
  MedalhaGanha VARCHAR2(6),

  CONSTRAINT PK_AtlMod PRIMARY KEY (Atleta, Modalidade),
  CONSTRAINT FK_AtlMod1 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE,
  CONSTRAINT FK_AtlMod2 FOREIGN KEY (Modalidade) REFERENCES Modalidade (Nome) ON DELETE CASCADE,
  CONSTRAINT CK_AtlMod CHECK (UPPER(MedalhaGanha) IN ('OURO', 'PRATA', 'BRONZE'))
);

-- Esse "Unique Index" é uma solução alternativa ao uso de "constraint unique"
-- em duas colunas. Podemos com o índice ter tuplas com valores repetidos
-- apenas se uma das columas apresentar valor nulo.
-- Exemplo:
-- (1, 'A'), (1, NULL), (1, NULL), (2, 'B')
-- diferente da constraint unique, essas são tuplas válidas na tabela, porém
-- (1, 'A'), (1, 'A')
-- infringiria o índice.
CREATE UNIQUE INDEX UIDX_AtlMod on Atleta_Modalidade
(nvl2(Modalidade, MedalhaGanha, null), nvl2(MedalhaGanha, Modalidade, null));

---------------------------- VIEWS -------------------------------

CREATE OR REPLACE VIEW Medalha AS
  SELECT
    sys_guid() AS uuid,
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
        WHERE MedalhaGanha IS NOT NULL) T
  WITH READ ONLY;

---------------------------- TRIGGERS ----------------------------