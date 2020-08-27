CREATE TABLE hospitais (
 id bigserial PRIMARY KEY,
 nome text,
 cnpj text,
 endereco text,
 cidade text, 
 uf text, 
 latitude text, 
 longitude text,
 limite_ocupacao integer,
 percentual_ocupacao numeric,
 data_atualizacao timestamp,
 data_inclusao timestamp
);

CREATE TABLE transacoes_historico (
  id bigserial PRIMARY KEY,
  hospital_origem_id bigint not null references hospitais(id),
  hospital_destino_id bigint not null references hospitais(id),
  data_inclusao timestamp
);

CREATE TABLE recursos_tipos (
  id integer PRIMARY KEY,
  nome text,
  pontos integer
);

CREATE TABLE recursos (
  id serial PRIMARY KEY,
  nome text,
  tipo_id integer references recursos_tipos(id),
  hospital_id bigint not null references hospitais(id),
  data_inclusao timestamp
);

CREATE TABLE recursos_solicitados_transacoes (
  recurso_id bigint not null references recursos(id),
  transacao_id bigint not null references transacoes_historico(id),
  CONSTRAINT recursos_solicitados_transacoes_pkey PRIMARY KEY (recurso_id, transacao_id)
);

CREATE TABLE recursos_ofertados_transacoes (
  recurso_id bigint not null references recursos(id),
  transacao_id bigint not null references transacoes_historico(id),
  CONSTRAINT recursos_ofertados_transacoes_pkey PRIMARY KEY (recurso_id, transacao_id)
);

INSERT INTO hospitais(nome, cnpj, endereco, cidade, uf, latitude, longitude, limite_ocupacao, data_inclusao)
VALUES 
('Hospital Geral Santa Isabel', '28431834000170', 'Praça Caldas Brandão, S/N - Tambiá', 'João Pessoa', 'PB', '-7.1230832', '-34.8806481', 40, NOW()),
('Hospital Padre Zé', '91325501000101', 'Av. Des. Boto de Menezes, 657 - Tambiá', 'João Pessoa', 'PB', '-7.1122205', '-34.8718983', 50, NOW()),
('Hospital Samaritano', '25974441000115', 'Av. Santa Júlia, 35 - Torre', 'João Pessoa', 'PB', '-7.1230832', '-34.8806481', 60, NOW()),
('Hospital de Emergência e Trauma Senador Humberto Lucena', '11766268000155', 'R. Orestes Lisboa, SN - Pedro Gondim', 'João Pessoa', 'PB', '-7.1230832', '-34.8806481', 100, NOW()),
('Hospital Pediátrico Moacir Dantas - Unimed', '90591952000110', 'Av. Min. José Américo de Almeida, 1338 - Torre', 'João Pessoa', 'PB', '-7.1230832', '-34.8806481', 30, NOW());

INSERT INTO recursos_tipos (id, nome, pontos) 
VALUES (1, 'Médico', 3), (2, 'Enfermeiro', 3), (3, 'Respirador', 5), (4, 'Tomógrafo', 12), (5, 'Ambulância', 10);

INSERT INTO recursos (nome, tipo_id, hospital_id, data_inclusao)
VALUES
('José Maria da Silva', 1, 1, NOW()),
('Ana Lúcia da Silva', 1, 1, NOW()),
('Cláudio Duarte Vasconcelos', 1, 2, NOW()),
('Pedro Jorge Fagundes', 1, 2, NOW()),
('Maria Eugênia Nogueira', 1, 3, NOW()),
('Marta Moura Brasil', 1, 3, NOW()),
('Augusto José Vargas', 1, 4, NOW()),
('Maria de Lourdes Souza', 1, 4, NOW()),
('Lúcia Menezes Sobrinhho', 1, 5, NOW()),
('José Henrrique Farias', 1, 5, NOW()),
('Caio Cézar', 2, 1, NOW()),
('Vera Maria', 2, 2, NOW()),
('João Pedro', 2, 3, NOW()),
('Maria Antônia', 2, 4, NOW()),
('Henrrique José', 2, 5, NOW()),
('Respirador Samsung', 3, 1, NOW()),
('Respirador Erikson', 3, 1, NOW()),
('Respirador Erikson', 3, 2, NOW()),
('Respirador Samsung', 3, 2, NOW()),
('Respirador Motorola', 3, 3, NOW()),
('Respirador Samsung', 3, 4, NOW()),
('Tomógrafo X44H', 4, 1, NOW()),
('Tomógrafo WWE3', 4, 4, NOW()),
('Fiat Fiorino 01', 5, 1, NOW()),
('Citroen Jumper 01', 5, 2, NOW()),
('Fiat Fiorino 03', 5, 3, NOW()),
('Fiorino 02', 5, 4, NOW()),
('Fiorino 01', 5, 5, NOW());

