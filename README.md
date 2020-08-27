# PandemicCombatAidSystem
Projeto para o Sistema de Combate de Pandemias

## Banco de Dados 

- Foi utilizado postgres 9.4x
- As configurações de conexão banco estão no arquivo applicaton.properties
- O setup inicial do banco de dados está no arquivo src/main/resources/migrations/V1_0__Setup_Inicial.sql

## Rest Services 

- Os serviços rest estão configurados no arquivo br.com.bruno.pcas.api.resource.HospitalResource


### GET /hospitais

- Lista todos os hospitais cadastrados e seus recursos

### POST /hospitais

- Inclui hospitais e seus recursos
- Modelo
```bash
{
    "nome": "Hospital Geral Santa Cecília",
    "cnpj": "28431834000170",
    "endereco": "Praça Caldas Brandão, S/N - Tambiá",
    "cidade": "João Pessoa",
    "uf": "PB",
    "latitude": "-7.1230832",
    "longitude": "-34.8806481",
    "limiteOcupacao": 40,
    "percentualOcupacao": 90,
    "dataInclusao": null,
    "recursos": [
        {
            "nome": "Jorge da Cunha",
            "tipo": 1,
            "hospitalID": 1
        },
        {
            "nome": "Ana Lúcia da Silva",
            "tipo": 1,
            "hospitalID": 1
        },
        {
            "nome": "Caio Cézar",
            "tipo": 2,
            "hospitalID": 1
        },
        {
            "nome": "Respirador Samsung",
            "tipo": 3,
            "hospitalID": 1
        },
        {
            "nome": "Tomógrafo X44H",
            "tipo": 4,
            "hospitalID": 1
        },
        {
            "nome": "Fiat Fiorino 01",
            "tipo": 5,
            "hospitalID": 1
        }
    ]
}
```

### PUT /hospitais

- Atualiza informações de um hospital
- Modelo
```bash
{
	"id": 2,
	"percentualOcupacao": 92
}
```

### GET /hospitais/{id}

- Obtem um hospital pelo seu ID

### GET /hospitais/ocupacao/{id}

- Obtem o percentual de ocupação do hospital por ID

### PUT /hospitais/trocar 

- Faz a troca de recursos entre hospitais
- Modelo
 
```bash
{
	"hospitalOrigemID": 2,
	"hospitalDestinoID": 1, 
	"recursosOfertados": [
		{ "tipo": 3 }, { "tipo": 3 }, { "tipo": 2 }
	],
	"recursosSolicitados": [
		{ "tipo": 1 }, { "tipo": 5 }
	]
}
```

## Relatórios

Porcentagem de hospitais com ocupação maior que 90%.

- GET /hospitais/relatorios/hospitaisocupacaomaior90

Porcentagem de hospitais com ocupação menor que 90%.

- GET /hospitais/relatorios/hospitaisocupacaomenor90

Quantidade média de cada tipo de recurso por hospital (Ex: 2 tomógrafos por hospital).

- GET /hospitais/relatorios/quantidaderecursoshospitais

Hospital em super-lotação (ocupação maior que 90%) a mais tempo.

- GET /hospitais/relatorios/hospitaisocupacaomaior90tempo

Hospital em abaixo de super-lotação (ocupação maior que 90%) a mais tempo.

- GET /hospitais/relatorios/hospitaisocupacaomenor90tempo

Histórico de negociação.

- GET /hospitais/relatorios/historicotransacoes
