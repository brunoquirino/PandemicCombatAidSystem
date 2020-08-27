# PandemicCombatAidSystem
Projeto para o Sistema de Combate de Pandemias

## Banco de Dados 

- Foi utilizado postgres 9.4x
- As configurações de conexão banco estão no arquivo applicaton.properties
- O setup inicial do banco de dados está no arquivo src/main/resources/migrations/V1_0__Setup_Inicial.sql

#db:pcas 
#user:pcas 
#password:pcas00

## Rest Services 

- Os serviços rest estão configurados no arquivo br.com.bruno.pcas.api.resource.HospitalResource


### GET /hospitais

- Lista todos os hospitais cadastrados e seus recursos

### POST /hospitais

- Inclui hospitais e seus recursos

### PUT /hospitais

- Atualiza informações de um hospital

### GET /hospitais/{id}

- Obtem um hospital pelo seu ID

### GET /hospitais/ocupacao/{id}

- Obtem o percentual de ocupação do hospital por ID

### PUT /hospitais/trocar 

- Faz a troca de recursos entre hospitais

## Relatórios

Porcentagem de hospitais com ocupação maior que 90%.

- GET /hospitais/relatorios/hospitaisocupacaomaior90

Porcentagem de hospitais com ocupação menor que 90%.

- GET /hospitais/relatorios/hospitaisocupacaomenor90

Quantidade média de cada tipo de recurso por hospital (Ex: 2 tomógrafos por hospital).

- GET /hospitais/relatorios/quantidaderecursoshospitais

Pontos perdidos devido a traidores.

- *?

Hospital em super-lotação (ocupação maior que 90%) a mais tempo.

- GET /hospitais/relatorios/hospitaisocupacaomaior90tempo

Hospital em abaixo de super-lotação (ocupação maior que 90%) a mais tempo.

- GET /hospitais/relatorios/hospitaisocupacaomenor90tempo

Histórico de negociação.

- GET /hospitais/relatorios/historicotransacoes
