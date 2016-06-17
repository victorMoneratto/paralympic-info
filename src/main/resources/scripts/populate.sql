INSERT INTO LOCALIDADE VALUES ('VELÓDROMO OLÍMPICO DO RIO - PARQUE OLÍMPICO DA BARRA', 'AV. EMB. ABELARDO BUENO');
INSERT INTO LOCALIDADE VALUES ('CENTRO OLÍMPICO DE HIPISMO', 'AV. DUQUE DE CAXIAS');
INSERT INTO LOCALIDADE VALUES ('CENTRO OLÍMPICO DE TÊNIS – PARQUE OLÍMPICO DA BARRA', 'AV. EMB. ABELARDO BUENO');
INSERT INTO LOCALIDADE VALUES ('ESTÁDIO OLÍMPICO', 'R. ARQUIAS CORDEIRO');
INSERT INTO LOCALIDADE VALUES ('ARENA DA JUVENTUDE – PARQUE OLÍMPICO DE DEODORO', 'AV. DUQUE DE CAXIAS');
INSERT INTO LOCALIDADE VALUES ('ARENA CARIOCA 3 – PARQUE OLÍMPICO DA BARRA', 'AV. EMB. ABELARDO BUENO');


INSERT INTO MODALIDADE VALUES ('CICLISMO DE PISTA MASCULINO INDIVIDUAL', 'MASCULINO', 'PONTOS',
                               'O ciclismo para atletas com deficiência se originou no início dos anos 1980, na disciplina de estrada. Os avanços tecnológicos tornaram o esporte mais inclusivo e as competições cada vez mais disputadas.',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('TENIS EM CADEIRA DE RODAS MASCULINO INDIVIDUAL', 'MASCULINO', 'PONTOS',
                               'Muitos historiadores apontam o século XIII como data da provável origem do tênis. Mas no formato moderno, só surgiu depois, já no fim do século XIX, quando foi desenhado, patenteado e comercializado em 1873 por um major do exército inglês. ',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('ESGRIMA EM CADEIRA DE RODAS MASCULINO', 'MASCULINO', 'PONTOS',
                               'A esgrima em cadeira de rodas foi apresentada nos Jogos de Stoke Mandeville, em 1954, por Sir Ludwig Guttmann, e fez parte de todos os Jogos Paralímpicos desde primeira edição, disputada no ano de 1960 em Roma.',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('HIPISMO', 'MISTO', 'PONTOS',
                               ' Os esportes equestres foram usados originalmente para reabilitação e recreação de pessoas com deficiência e hoje é amplamente usado em jogos paralimpicos.',
                               'Time');
INSERT INTO MODALIDADE VALUES ('TENIS EM CADEIRA DE RODAS MISTO TIME', 'MISTO', 'PONTOS',
                               'Muitos historiadores apontam o século XIII como data da provável origem do tênis. Mas no formato moderno, só surgiu depois, já no fim do século XIX, quando foi desenhado, patenteado e comercializado em 1873 por um major do exército inglês. ',
                               'Time');
INSERT INTO MODALIDADE VALUES ('CICLISMO DE PISTA FEMININO INDIVIDUAL', 'FEMININO', 'PONTOS',
                               'O ciclismo para atletas com deficiência se originou no início dos anos 1980, na disciplina de estrada. Os avanços tecnológicos tornaram o esporte mais inclusivo e as competições cada vez mais disputadas.',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('TENIS EM CADEIRA DE RODAS FEMININO INDIVIDUAL', 'FEMININO', 'PONTOS',
                               'Muitos historiadores apontam o século XIII como data da provável origem do tênis. Mas no formato moderno, só surgiu depois, já no fim do século XIX, quando foi desenhado, patenteado e comercializado em 1873 por um major do exército inglês. ',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('ATLETISMO FEMININO', 'FEMININO', 'PONTOS',
                               'O atletismo foi um dos oito esportes a figurar nos primeiros Jogos Paralímpicos, realizados no ano de 1960 em Roma, quando os atletas competiram em um total de 25 eventos valendo medalha.',
                               'Individual');
INSERT INTO MODALIDADE VALUES ('ATLETISMO MASCULINO', 'MASCULINO', 'PONTOS',
                               'O atletismo foi um dos oito esportes a figurar nos primeiros Jogos Paralímpicos, realizados no ano de 1960 em Roma, quando os atletas competiram em um total de 25 eventos valendo medalha.',
                               'Individual');

INSERT INTO DELEGACAO VALUES ('COMITÊ OLÍMPICO DO BRASIL'); --Brasil
INSERT INTO DELEGACAO VALUES ('COMITATO OLIMPICO NAZIONALE ITALIANO'); --Italia
INSERT INTO DELEGACAO VALUES ('COMITÉ NATIONAL OLYMPIQUE ET SPORTIF FRANÇAIS'); --França
INSERT INTO DELEGACAO VALUES ('UNITED STATES OLYMPIC COMMITTEE'); --USA
INSERT INTO DELEGACAO VALUES ('SWISS OLYMPIC ASSOCIATION'); --Suiça
INSERT INTO DELEGACAO VALUES ('NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE'); -- Holanda

INSERT INTO PAIS VALUES ('Brasil', 'BRA', 'br', 'COMITÊ OLÍMPICO DO BRASIL');
INSERT INTO PAIS VALUES ('Itália', 'ITA', 'it', 'COMITATO OLIMPICO NAZIONALE ITALIANO');
INSERT INTO PAIS VALUES ('França', 'FRA', 'fr', 'COMITÉ NATIONAL OLYMPIQUE ET SPORTIF FRANÇAIS');
INSERT INTO PAIS VALUES ('Estados Unidos da América', 'EUA', 'us', 'UNITED STATES OLYMPIC COMMITTEE');
INSERT INTO PAIS VALUES ('Suíça', 'SUI', 'ch', 'SWISS OLYMPIC ASSOCIATION');
INSERT INTO PAIS VALUES ('Holanda', 'HOL', 'nl', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE');

INSERT INTO ARBITRO VALUES (1, 'Paulo Augusto', 'Brasil');
INSERT INTO ARBITRO VALUES (2, 'Marcos Netto', 'Brasil');
INSERT INTO ARBITRO VALUES (3, 'Noah James', 'Estados Unidos da América');
INSERT INTO ARBITRO VALUES (4, 'Enzo Agarelli', 'Itália');
INSERT INTO ARBITRO VALUES (5, 'Apollon Beaumont', 'França');

INSERT INTO ATLETA VALUES
  (1, 'Esther Vergeer', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE', to_date('18/06/1981', 'dd/mm/yyyy'),
   1.76, 72.3, 'FEMININO', 'https://goo.gl/qhWMvd'); -- TENIS
INSERT INTO ATLETA VALUES (5, 'Chantal Vadierendonck', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE',
                           to_date('31/01/1965', 'dd/mm/yyyy'), 1.75, 80.3, 'FEMININO',
                           'http://goo.gl/bgT3Ql'); -- TENIS
INSERT INTO ATLETA VALUES
  (6, 'Beth Arnoult', 'UNITED STATES OLYMPIC COMMITTEE', to_date('31/07/1985', 'dd/mm/yyyy'), 1.65, 60.3, 'FEMININO',
   'http://goo.gl/vJxNg0'); --tenis

INSERT INTO ATLETA VALUES
  (9, 'Lee Pearson', 'SWISS OLYMPIC ASSOCIATION', to_date('11/07/1967', 'dd/mm/yyyy'), 1.85, 80.3, 'MASCULINO',
   'http://goo.gl/oDUCvq'); -- hipismo
INSERT INTO ATLETA VALUES (8, 'Robin Ammerlaan', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE',
                           to_date('26/02/1968', 'dd/mm/yyyy'), 1.76, 72.3, 'MASCULINO',
                           'http://goo.gl/R6vpwz'); -- TENIS
INSERT INTO ATLETA VALUES
  (7, 'Paul Moran', 'UNITED STATES OLYMPIC COMMITTEE', to_date('31/12/1966', 'dd/mm/yyyy'), 1.87, 80.3, 'MASCULINO',
   'http://goo.gl/xbNHF1'); -- tenis
INSERT INTO ATLETA VALUES
  (2, 'David Wagner', 'UNITED STATES OLYMPIC COMMITTEE', to_date('04/03/1974', 'dd/mm/yyyy'), 1.83, 80.3, 'MASCULINO',
   'https://goo.gl/InEfIk'); -- tenis
INSERT INTO ATLETA VALUES
  (3, 'Heinz Frei', 'SWISS OLYMPIC ASSOCIATION', to_date('28/01/1958', 'dd/mm/yyyy'), 1.83, 80.3, 'MASCULINO',
   'http://goo.gl/s1S0MO'); -- atletismo
INSERT INTO ATLETA VALUES
  (4, 'Joop Stokkel', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE', to_date('11/04/1967', 'dd/mm/yyyy'),
   1.75, 80.3, 'MASCULINO', 'http://goo.gl/bgT3Ql'); -- hipismo

INSERT INTO ATLETA_MODALIDADE VALUES (5, 'TENIS EM CADEIRA DE RODAS FEMININO INDIVIDUAL', 'D', 'OURO');
INSERT INTO ATLETA_MODALIDADE VALUES (1, 'TENIS EM CADEIRA DE RODAS FEMININO INDIVIDUAL', 'C', 'PRATA');
INSERT INTO ATLETA_MODALIDADE VALUES (6, 'TENIS EM CADEIRA DE RODAS FEMININO INDIVIDUAL', 'C', NULL);
INSERT INTO ATLETA_MODALIDADE VALUES (8, 'TENIS EM CADEIRA DE RODAS MASCULINO INDIVIDUAL', 'D', NULL);
INSERT INTO ATLETA_MODALIDADE VALUES (2, 'TENIS EM CADEIRA DE RODAS MASCULINO INDIVIDUAL', 'C', NULL);
INSERT INTO ATLETA_MODALIDADE VALUES (3, 'ATLETISMO MASCULINO', '11', NULL);
INSERT INTO ATLETA_MODALIDADE VALUES (4, 'HIPISMO', 'Classe IV', NULL);

INSERT INTO TIMEOLIMPICO VALUES
  (1, 'TENIS EM CADEIRA DE RODAS MISTO EUA', 'UNITED STATES OLYMPIC COMMITTEE', 'TENIS EM CADEIRA DE RODAS MISTO TIME',
   'D', 'MISTO', NULL, '3E');
INSERT INTO TIMEOLIMPICO VALUES
  (2, 'TENIS EM CADEIRA DE RODAS MISTO HOL', 'NEDERLANDS OLYMPISCH COMITÉ* NEDERLANDSE SPORT FEDERATIE',
   'TENIS EM CADEIRA DE RODAS MISTO TIME', 'D', 'MISTO', NULL, '3E');
INSERT INTO TIMEOLIMPICO
VALUES (3, 'ATLETISMO FEMININO SUI', 'SWISS OLYMPIC ASSOCIATION', 'ATLETISMO FEMININO', 'D', 'FEMININO', NULL, '3D');

INSERT INTO ATLETA_TIME VALUES (6, 1);
INSERT INTO ATLETA_TIME VALUES (7, 1);
INSERT INTO ATLETA_TIME VALUES (5, 2);
INSERT INTO ATLETA_TIME VALUES (8, 2);

INSERT INTO PARTIDA VALUES
  (1, to_date('10/06/2016 16:00', 'dd/mm/yyyy hh24:mi'), 'CENTRO OLÍMPICO DE HIPISMO', 'S', NULL, 'INDIVIDUAL',
   'HIPISMO');
INSERT INTO PARTIDA VALUES
  (2, to_date('12/06/2016 17:00', 'dd/mm/yyyy hh24:mi'), 'CENTRO OLÍMPICO DE TÊNIS – PARQUE OLÍMPICO DA BARRA', 'S',
   NULL, 'INDIVIDUAL', 'TENIS EM CADEIRA DE RODAS MASCULINO INDIVIDUAL');
INSERT INTO PARTIDA VALUES
  (3, to_date('12/06/2016 19:00', 'dd/mm/yyyy hh24:mi'), 'CENTRO OLÍMPICO DE TÊNIS – PARQUE OLÍMPICO DA BARRA', 'S',
   NULL, 'TIME', 'TENIS EM CADEIRA DE RODAS MISTO TIME');

INSERT INTO ATLETA_PARTIDA VALUES (1, 4, 2, 35);
INSERT INTO ATLETA_PARTIDA VALUES (1, 9, 1, 45);
INSERT INTO ATLETA_PARTIDA VALUES (2, 8, 1, 30);
INSERT INTO ATLETA_PARTIDA VALUES (2, 2, 2, 20);

INSERT INTO ARBITRO_PARTIDA VALUES (1, 1);
INSERT INTO ARBITRO_PARTIDA VALUES (1, 3);
INSERT INTO ARBITRO_PARTIDA VALUES (1, 4);
INSERT INTO ARBITRO_PARTIDA VALUES (2, 2);
INSERT INTO ARBITRO_PARTIDA VALUES (2, 5);
INSERT INTO ARBITRO_PARTIDA VALUES (3, 1);
INSERT INTO ARBITRO_PARTIDA VALUES (3, 4);

INSERT INTO TIME_PARTIDA VALUES (3, 2, 1, 25);
INSERT INTO TIME_PARTIDA VALUES (3, 1, 2, 15);

-- INSERTS DE TESTE --


-- INSERT INTO ATLETA_PARTIDA VALUES(2,1,1,35); -- Deve dar erro pois é uma modalidade masculina e a atleta é do gênero feminino, logo essa atleta não está na modalidade dessa partida
-- INSERT INTO ATLETA_TIME (1,1); -- Deve dar erro pois o atleta 1 não é da mesma delegacao do time 1
-- INSERT INTO ATLETA_TIME (3,3); -- Deve dar erro pois o time é feminino e o atleta é do gênero masculino
-- INSERT INTO ATLETA_TIME (2,4); -- Deve dar erro pois o atleta é de hipismo e o time é de tenis
-- INSERT INTO PARTIDA VALUES(6, to_date('10/06/2016 16:00','dd/mm/yyyy hh24:mi'), 'CENTRO DE HIPISMO','S',null,'INDIVIDUAL','HIPISMO'); -- Deve dar erro pois não existe essa localidade
-- INSERT INTO PARTIDA VALUES(6, to_date('10/06/2016 16:00','dd/mm/yyyy hh24:mi'), 'CENTRO OLÍMPICO DE HIPISMO','S',null,'UEPA','HIPISMO'); -- Deve dar errado pois não existe tipo UEPA de partida
-- INSERT INTO PAIS VALUES('Brasil', 'BRA', 'br', 'BraBra'); -- Deve dar erro pois não existe uma delegação chamada BraBra
-- INSERT INTO ATLETA VALUES(14,'Luh Peon','OLYMPIC ASSOCIATION',to_date('11/07/1967','dd/mm/yyyy'),1.85,80.3,'MASCULINO','http://goo.gl/oDUCvq'); -- deve dar erro pois não existe essa delegação
-- INSERT INTO ATLETA VALUES(14,'Luh Peon','SWISS OLYMPIC ASSOCIATION',to_date('11/07/1967','dd/mm/yyyy'),1.85,80.3,'MASC','http://goo.gl/oDUCvq'); -- deve dar erro pois não há esse genero no bd
