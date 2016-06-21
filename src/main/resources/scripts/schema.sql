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
  Modalidade    VARCHAR(80) NOT NULL,

  CONSTRAINT PK_Partida PRIMARY KEY (Identificador),
  CONSTRAINT FK_Partida FOREIGN KEY (NomeLocal) REFERENCES Localidade (Nome) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_Partida UNIQUE (NomeLocal, DataHora)
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
  CONSTRAINT FK_AtlPart2 FOREIGN KEY (Atleta) REFERENCES Atleta (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_TimePart UNIQUE (PARTIDA, CLASSIFICACAO)
);

CREATE TABLE TimeOlimpico (
  Identificador         SERIAL,
  Nome                  VARCHAR(120) NOT NULL,
  Delegacao             VARCHAR(120) NOT NULL,
  Modalidade            VARCHAR(80),
  Categoria             VARCHAR(10),
  Genero                VARCHAR(9),
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
  CONSTRAINT FK_TimePart2 FOREIGN KEY (TimeOlimp) REFERENCES TimeOlimpico (Identificador) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UN_TimePart UNIQUE (PARTIDA, CLASSIFICACAO)
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
DROP EXTENSION IF EXISTS uuid-ossp;
CREATE EXTENSION uuid-ossp;
DROP VIEW Medalha;
CREATE OR REPLACE VIEW Medalha AS
  SELECT
    uuid_generate_v4() AS uuid,
    NULL               AS Time,
    Atleta,
    'INDIVIDUAL'       AS Tipo,
    Nome,
    Modalidade,
    MedalhaGanha       AS Medalha
  FROM Atleta_Modalidade
    JOIN Atleta ON Atleta = Identificador
  WHERE MedalhaGanha IS NOT NULL

  UNION

  SELECT
    uuid_generate_v4() AS uuid,
    Identificador      AS Time,
    NULL               AS Atleta,
    'TIME'             AS Tipo,
    Nome,
    Modalidade,
    MedalhaGanha       AS Medalha
  FROM TimeOlimpico
  WHERE MedalhaGanha IS NOT NULL;

DROP VIEW Atleta_Time_Info;
CREATE OR REPLACE VIEW Atleta_Time_Info AS
  SELECT
    Atleta_Time.*,
    Atleta.Nome       AS NomeAtleta,
    TimeOlimpico.Nome AS NomeTime,
    Atleta.Delegacao
  FROM Atleta_Time
    JOIN Atleta ON Atleta_Time.Atleta = Atleta.Identificador
    JOIN TimeOlimpico ON Atleta_Time.TimeOlimp = TimeOlimpico.Identificador;

DROP VIEW Participante_Partida;
CREATE OR REPLACE VIEW Participante_Partida AS
  SELECT
    uuid_generate_v4() AS uuid,
    Atleta,
    NULL               AS Time,
    'INDIVIDUAL'       AS Tipo,
    Nome,
    Delegacao,
    Partida,
    Classificacao,
    Pontuacao
  FROM Atleta_Partida
    JOIN Atleta
      ON Atleta_Partida.Atleta = Atleta.Identificador
  UNION
  SELECT
    uuid_generate_v4() AS uuid,
    NULL               AS Atleta,
    TimeOlimp          AS Time,
    'Time'             AS Tipo,
    Nome,
    Delegacao,
    Partida,
    Classificacao,
    Pontuacao
  FROM Time_Partida
    JOIN TimeOlimpico
      ON Time_Partida.TimeOlimp = TimeOlimpico.Identificador;

---------------------------- TRIGGERS ----------------------------
-- *********************************************************
-- Trigger para inserção na view Participante_Atleta
-- *********************************************************
CREATE OR REPLACE FUNCTION participante_partida_new()
  RETURNS TRIGGER AS $participante_partida_new$
BEGIN
  IF (TG_OP = 'INSERT')
  THEN
    IF (NEW.Atleta IS NOT NULL AND NEW.Time IS NULL)
    THEN

      INSERT INTO Atleta_Partida (Partida, Atleta, Classificacao, Pontuacao)
      VALUES (NEW.Partida, NEW.Atleta, NEW.Classificacao, NEW.Pontuacao);

      RETURN NEW;
    ELSIF (NEW.Time IS NOT NULL AND NEW.Atleta IS NULL)
      THEN

        INSERT INTO Time_Partida (Partida, TimeOlimp, Classificacao, Pontuacao)
        VALUES (NEW.Partida, NEW.Time, NEW.Classificacao, NEW.Pontuacao);

        RETURN NEW;
    END IF;
  ELSIF (TG_OP = 'UPDATE')
    THEN
      IF (OLD.Atleta IS NOT NULL AND OLD.Time IS NULL)
      THEN
        UPDATE Atleta_Partida
        SET Partida = NEW.Partida, Atleta = NEW.Atleta, Classificacao = NEW.Classificacao, Pontuacao = NEW.Pontuacao
        WHERE Partida = OLD.Partida AND Atleta = OLD.Atleta;

        RETURN OLD;
      ELSIF (OLD.Time IS NOT NULL AND OLD.Atleta IS NULL)
        THEN

          UPDATE Time_Partida
          SET Partida = NEW.Partida, TimeOlimp = NEW.Time, Classificacao = NEW.Classificacao,
            Pontuacao = NEW.Pontuacao
          WHERE Partida = OLD.Partida AND TimeOlimp = OLD.Time;

          RETURN OLD;
      END IF;
  ELSIF (TG_OP = 'DELETE')
    THEN

      IF (old.Atleta IS NOT NULL AND old.Time IS NULL)
      THEN

        DELETE FROM Atleta_Partida
        WHERE Atleta = OLD.Atleta AND Partida = OLD.Partida;
        IF NOT FOUND
        THEN RETURN NULL; END IF;

        RETURN OLD;
      ELSIF (old.Time IS NOT NULL AND old.Atleta IS NULL)
        THEN

          DELETE FROM Time_Partida
          WHERE TimeOlimp = OLD.Time AND Partida = OLD.Partida;
          IF NOT FOUND
          THEN RETURN NULL; END IF;

          RETURN OLD;
      END IF;
  END IF;
  RETURN NULL;
END;
$participante_partida_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS participante_partida_new ON Participante_Partida;
CREATE TRIGGER participante_partida_new
INSTEAD OF INSERT OR UPDATE OR DELETE ON Participante_Partida
FOR EACH ROW EXECUTE PROCEDURE participante_partida_new();

-- *********************************************************
-- Trigger para inserção na view Medalha
-- *********************************************************
CREATE OR REPLACE FUNCTION medalha_new()
  RETURNS TRIGGER AS $medalha_new$
BEGIN
  IF (TG_OP = 'INSERT')
  THEN
    IF (NEW.Atleta IS NOT NULL AND NEW.Time IS NULL)
    THEN

      UPDATE Atleta_Modalidade
      SET MedalhaGanha = NEW.Medalha
      WHERE Atleta = NEW.Atleta AND Modalidade = NEW.Modalidade;

      IF NOT FOUND
      THEN RAISE EXCEPTION 'Atleta não participa da modalidade';
      END IF;

      RETURN NEW;
    ELSIF (NEW.Time IS NOT NULL AND NEW.Atleta IS NULL)
      THEN

        UPDATE TimeOlimpico
        SET MedalhaGanha = NEW.Medalha
        WHERE Identificador = NEW.TIME AND Modalidade = NEW.Modalidade;

        IF NOT FOUND
        THEN RAISE EXCEPTION 'Time não participa da modalidade';
        END IF;

        RETURN NEW;
    END IF;
  ELSIF (TG_OP = 'DELETE')
    THEN
      IF (OLD.Atleta IS NOT NULL AND OLD.Time IS NULL)
      THEN
        UPDATE Atleta_Modalidade
        SET MedalhaGanha = NULL
        WHERE Atleta = OLD.Atleta AND Modalidade = OLD.Modalidade;

        RETURN OLD;
      ELSIF (OLD.Time IS NOT NULL AND OLD.Atleta IS NULL)
        THEN
          UPDATE TimeOlimpico
          SET MedalhaGanha = NULL
          WHERE Identificador = OLD.Time AND Modalidade = OLD.Modalidade;

          RETURN OLD;
      END IF;
  END IF;

  RETURN NULL;
END;
$medalha_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS medalha_new ON Medalha;
CREATE TRIGGER medalha_new
INSTEAD OF INSERT OR DELETE ON Medalha
FOR EACH ROW EXECUTE PROCEDURE medalha_new();

-- *********************************************************
-- Atleta participa de uma modalidade de genero compativel
-- *********************************************************
CREATE OR REPLACE FUNCTION atleta_modalidade_new()
  RETURNS TRIGGER AS $atleta_modalidade_new$
DECLARE
  TIPO_MODALIDADE   VARCHAR(10);
  GENERO_MODALIDADE VARCHAR(9);
  GENERO_ATLETA     VARCHAR(9);
BEGIN

  TIPO_MODALIDADE = (SELECT TIPO
                     FROM Modalidade
                     WHERE Modalidade.Nome = NEW.Modalidade);
  IF UPPER(TIPO_MODALIDADE) <> 'INDIVIDUAL'
  THEN
    RAISE EXCEPTION 'O atleta não pode ser inscrito numa modalidade de times';
  END IF;

  GENERO_MODALIDADE = (SELECT GENERO
                       FROM MODALIDADE
                       WHERE MODALIDADE.Nome = NEW.MODALIDADE);
  IF (UPPER(GENERO_MODALIDADE) <> 'MISTO')
  THEN
    GENERO_ATLETA = (SELECT GENERO
                     FROM ATLETA
                     WHERE ATLETA.Identificador = NEW.ATLETA);
    IF UPPER(GENERO_ATLETA) <> UPPER(GENERO_MODALIDADE)
    THEN
      RAISE EXCEPTION 'Não são aceitos atletas desse gênero na modalidade';
    END IF;
  END IF;

  RETURN NEW;
END;
$atleta_modalidade_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS atleta_modalidade_new ON Atleta_Modalidade;
CREATE TRIGGER atleta_modalidade_new BEFORE INSERT OR UPDATE ON Atleta_Modalidade
FOR EACH ROW EXECUTE PROCEDURE atleta_modalidade_new();

-- *************************************************************
-- Time participa de uma modalidade para times, do mesmo genero
-- *************************************************************
CREATE OR REPLACE FUNCTION time_new()
  RETURNS TRIGGER AS $time_new$
DECLARE
  TIPO_MODALIDADE   VARCHAR(10);
  GENERO_MODALIDADE VARCHAR(10);
BEGIN

  TIPO_MODALIDADE = (SELECT TIPO
                     FROM Modalidade
                     WHERE Modalidade.Nome = NEW.Modalidade);
  IF UPPER(TIPO_MODALIDADE) <> 'TIME'
  THEN
    RAISE EXCEPTION 'O time não pode ser inscrito numa modalidade individual';
  END IF;

  GENERO_MODALIDADE = (SELECT Genero
                       FROM Modalidade
                       WHERE Modalidade.Nome = NEW.Modalidade);
  IF UPPER(NEW.Genero) <> UPPER(GENERO_MODALIDADE)
  THEN
    RAISE EXCEPTION 'Não são aceitos times desse gênero na modalidade';
  END IF;

  RETURN NEW;
END;
$time_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS time_new ON TimeOlimpico;
CREATE TRIGGER time_new BEFORE INSERT OR UPDATE ON TimeOlimpico
FOR EACH ROW EXECUTE PROCEDURE time_new();

-- *********************************************************
-- Atleta participa de uma partida da sua modalidade
-- *********************************************************
CREATE OR REPLACE FUNCTION atleta_partida_new()
  RETURNS TRIGGER AS $atleta_partida_new$
DECLARE
  PARTIDA_MODALIDADE VARCHAR(80);
  NUM_ATLETA         INTEGER;
BEGIN
  PARTIDA_MODALIDADE = (SELECT MODALIDADE
                        FROM PARTIDA
                        WHERE PARTIDA.Identificador = NEW.PARTIDA);
  NUM_ATLETA = (SELECT COUNT(ATLETA)
                FROM Atleta_Modalidade AS AM
                WHERE AM.Atleta = NEW.ATLETA AND AM.Modalidade = PARTIDA_MODALIDADE);
  IF NUM_ATLETA = 0
  THEN
    RAISE EXCEPTION 'O atleta não participa da modalidade da partida';
  END IF;

  RETURN NEW;
END;
$atleta_partida_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS atleta_partida_new ON Atleta_Modalidade;
CREATE TRIGGER atleta_partida_new BEFORE INSERT OR UPDATE ON Atleta_Partida
FOR EACH ROW EXECUTE PROCEDURE atleta_partida_new();

-- *********************************************************
-- Time participa de uma partida da sua modalidade
-- *********************************************************
CREATE OR REPLACE FUNCTION time_partida_new()
  RETURNS TRIGGER AS $time_partida_new$
DECLARE
  PARTIDA_MODALIDADE VARCHAR(80);
  TIME_MODALIDADE    VARCHAR(80);
BEGIN
  PARTIDA_MODALIDADE = (SELECT MODALIDADE
                        FROM PARTIDA
                        WHERE PARTIDA.Identificador = NEW.PARTIDA);
  TIME_MODALIDADE = (SELECT MODALIDADE
                     FROM TimeOlimpico AS T
                     WHERE T.Identificador = NEW.TIMEOLIMP);
  IF UPPER(PARTIDA_MODALIDADE) <> UPPER(TIME_MODALIDADE)
  THEN
    RAISE EXCEPTION 'O time não participa da modalidade da partida';
  END IF;

  RETURN NEW;
END;
$time_partida_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS time_partida_new ON Time_Partida;
CREATE TRIGGER time_partida_new BEFORE INSERT OR UPDATE ON Time_Partida
FOR EACH ROW EXECUTE PROCEDURE time_partida_new();

-- ****************************************************************
-- Atleta participa de time de mesma delegação e genero compativel
-- ****************************************************************
CREATE OR REPLACE FUNCTION atleta_time_new()
  RETURNS TRIGGER AS $atleta_time_new$
DECLARE
  ATLETA_DELEGACAO VARCHAR(120);
  TIME_DELEGACAO   VARCHAR(120);
  ATLETA_GENERO    VARCHAR(9);
  TIME_GENERO      VARCHAR(9);
BEGIN
  ATLETA_DELEGACAO = (SELECT Delegacao
                      FROM Atleta
                      WHERE ATLETA.Identificador = NEW.Atleta);
  TIME_DELEGACAO = (SELECT Delegacao
                    FROM TimeOlimpico AS T
                    WHERE T.Identificador = NEW.TIMEOLIMP);
  IF UPPER(ATLETA_DELEGACAO) <> UPPER(TIME_DELEGACAO)
  THEN
    RAISE EXCEPTION 'O atleta não pertence à mesma delegação do time';
  END IF;

  TIME_GENERO = (SELECT Genero
                 FROM TimeOlimpico AS T
                 WHERE T.Identificador = NEW.TIMEOLIMP);
  IF UPPER(TIME_GENERO) <> 'MISTO'
  THEN
    ATLETA_GENERO = (SELECT Genero
                     FROM Atleta
                     WHERE ATLETA.Identificador = NEW.Atleta);
    IF UPPER(TIME_GENERO) <> UPPER(ATLETA_GENERO)
    THEN
      RAISE EXCEPTION 'O gênero do atleta é incompatível com o do time';
    END IF;
  END IF;


  RETURN NEW;
END;
$atleta_time_new$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS atleta_time_new ON Atleta_Time;
CREATE TRIGGER atleta_time_new BEFORE INSERT OR UPDATE ON Atleta_Time
FOR EACH ROW EXECUTE PROCEDURE atleta_time_new();