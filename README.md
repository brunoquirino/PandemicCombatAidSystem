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
#- /hospitais

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