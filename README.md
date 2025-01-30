# LevelUp-Academy_backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-%2385EA2D.svg?style=for-the-badge&logo=Swagger&logoColor=black)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Insomnia](https://img.shields.io/badge/Insomnia-black?style=for-the-badge&logo=insomnia&logoColor=5849BE)

## Descrição

LevelUp-Academy é uma aplicação backend desenvolvida para gerenciar usuários e atividades em uma plataforma educacional.
A API fornece endpoints para a criação, atualização e recuperação de atividades, além do gerenciamento de usuários e
suas interações dentro do sistema.

A plataforma suporta dois tipos de usuários: teachers (professores) e students (alunos). Ambos estão associados a uma
sala de aula, onde professores podem criar atividades e alunos podem enviar respostas.

Os alunos acumulam pontuação ao concluir atividades, permitindo que avancem de nível e obtenham benefícios. Além disso,
um sistema de moeda virtual possibilita a aquisição de vantagens adicionais dentro da plataforma.

## Tecnlogias

### Back-end

- Java 17
- Spring Boot 3.4.1
- Spring Data JPA
- Spring Validation
- Spring Web
- Spring DevTools
- SpringDoc (Swagger)
- Spring Test

### Banco de Dados

- H2 Database
- PostgreSQL
- Flyway

### Outros

- Lombok
- Maven
- Docker
- Insomnia
- Git & GitHub

## Arquitetura de diretórios

```
LevelUp-Academy/
├── .mvn/                       # Configurações do Maven Wrapper
├── collections/                # Coleções adicionais (Testes de Insomnia)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.fiap.hackaton/
│   │   │       ├── controller/           # Controladores da aplicação
│   │   │       ├── domain/
│   │   │       │   ├── dto/              # Objetos de transferência de dados  
│   │   │       │   ├── entity/           # Entidades do banco de dados
│   │   │       │   ├── enums/            # Enumerações
│   │   │       │   └── exception/        # Exceções personalizadas        
│   │   │       ├── infra/
│   │   │       │   ├── doc/              # Documentação da API 
│   │   │       │   ├── exception/        # Tratamento de exceções
│   │   │       │   └── web/              # Configurações da aplicação
│   │   │       ├── repository/           # Classes de repositório
│   │   │       ├── service/              # Serviços da aplicação
│   │   │       │   └── impl/             # Implementações dos serviços
│   │   │       └── ApiApplication        # Classe principal da aplicação
│   │   ├── resources/                    # Arquivos de configuração e recursos
│   │   │   ├── db.migration                  # Scripts de migração do banco de dados
│   │   │   ├── application.properties        # Configurações da aplicação
│   │   │   ├── application-dev.properties    # Configurações da aplicação (ambiente de desenvolvimento)
│   │   │   └── application-prod.properties   # Configurações da aplicação (ambiente de produção)
│   └── test/                    # Testes da aplicação
├── uploads/                     # Arquivos enviados pelos usuários  
├── mvnw                         # Script do Maven Wrapper
└── pom.xml                      # Arquivo de configuração do Maven
```

## Endpoints

> Para mais detalhes, consulte a documentação da API no Swagger após executar o projeto,
> acessando `http://localhost:8001`.

### Cadastrar Usuário

> `POST` /user/signup

| campo | tipo | obriatório | descição                                             
|-------|:----:|:----------:|------------------------------------------------------
| name  | text |    sim     | nome do usuário que está criando a conta             
| email | text |    sim     | email para criar um conta do usuário                 
| senha | text |    sim     | senha para criar uma segurança para conta do usuário 

**Códigos de Respostas**

| código | descrição                                
|--------|------------------------------------------
| 201    | usuário cadastrada                       
| 400    | erro na validação de dados da requisição 

### Cadastrar aluno

> `POST` /student/{userId}

**Códigos de Respostas**

| código | descrição              
|--------|------------------------
| 201    | aluno cadastrado       
| 404    | usuário não encontrado 

### Cadastrar Professor

> `POST` /teacher/{userId}

**Códigos de Respostas**

| código | descrição              
|--------|------------------------
| 201    | professor cadastrado   
| 404    | usuário não encontrado 

### Cadastrar Sala de Aula

> `POST` /classroom/{teacherId}

| campo     | tipo | obriatório | descição                   
|-----------|:----:|:----------:|----------------------------
| subject   | text |    sim     | disciplina da sala de aula 
| dayOfWeek | text |    sim     | dia da semana da aula      
| timeSpot  | text |    sim     | horário da aula            

**Códigos de Respostas**

| código | descrição                
|--------|--------------------------
| 201    | sala criada              
| 404    | professor não encontrado 

### Cadastrar Atividade

> `POST` /activity/{classroomId}

| campo            |  tipo   | obriatório | descição                         
|------------------|:-------:|:----------:|----------------------------------
| name             |  text   |    sim     | nome da atividade                
| description      |  text   |    sim     | enunciado da atividade           
| type             |  enum   |    sim     | tipo da atividade                
| inGroup          | boolean |    sim     | se a atividade é em grupo        
| studentsPerGroup | number  |    sim     | quantidade de alunos por grupo   
| execution        |  enum   |    sim     | aonde a atividade será executada 
| prestigeValue    | number  |    sim     | valor de prestígio da atividade  

#### Tipos de Atividades
- `MULTIPLA_ESCOLHA`
- `QUESTIONARIO`
- `QUIZ`
- `ESTUDO_DIRIGIDO`
- `APRESNTACAO`
- `CACA_PALAVRAS`
- `CAMPEONATO`

#### Tipos de Execução
- `EM_CASA`
- `EM_SALA`

**Códigos de Respostas**

| código | descrição                   
|--------|-----------------------------
| 201    | atividade cadastrada        
| 404    | sala de aula não encontrada 

### Detalhar usuário
> `GET` /user/{userId}

````json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
````

**Códigos de Respostas**

| código | descrição
| --- | ---
| 200 | dados retornados no corpo da resposta
| 404 | usuário não encontrado

### Detalhar aluno
> `GET` /student/{studentId}

````json
{
  "id": 0,
  "name": "string",
  "email": "string",
  "experiencePoints": 0,
  "currentPatent": "string"
}
````

**Códigos de Respostas**

| código | descrição
| --- | ---
| 200 | dados retornados no corpo da resposta
| 404 | aluno não encontrado

### Detalhar professor
> `GET` /teacher/{teacherId}

````json
{
  "teacherId": 0,
  "name": "string",
  "email": "string",
  "classrooms": [
    {
      "subject": "string",
      "dayOfWeek": 0,
      "timeSpot": 0,
      "students": [
        {
          "id": 0,
          "name": "string",
          "currentPatent": "string"
        }
      ],
      "activities": [
        {
          "name": "string",
          "description": "string",
          "prestigeValue": 0
        }
      ]
    }
  ]
}
````

**Códigos de Respostas**

| código | descrição
| --- | ---
| 200 | dados retornados no corpo da resposta
| 404 | professor não encontrado

### Detalhar sala de aula
> `GET` /classroom/{classroomId}

````json
{
  "id": 0,
  "subject": "string",
  "dayOfWeek": 0,
  "timeSpot": 0,
  "teacherName": "string",
  "students": [
    {
      "id": 0,
      "name": "string",
      "currentPatent": "string"
    }
  ],
  "activities": [
    {
      "name": "string",
      "description": "string",
      "prestigeValue": 0
    }
  ]
}
````

**Códigos de Respostas**

| código | descrição
| --- | ---
| 200 | dados retornados no corpo da resposta
| 404 | sala não encontrada

### Detalhar atividade
> `GET` /activity/{activityId}

````json
{
  "id": 0,
  "name": "string",
  "description": "string",
  "type": "string",
  "inGroup": false,
  "studentsPerGroup": 0,
  "execution": "string",
  "prestigeValue": 0
}
````

**Códigos de Respostas**

| código | descrição
| --- | ---
| 200 | dados retornados no corpo da resposta
| 404 | atividade não encontrada

## Como executar

### Requisitos
- Git
- Docker

### Clonar o repositório
```bash
git clone https://github.com/JessMotta/LevelUp-Academy_backend.git
```

### Accessar o diretório
```bash
cd LevelUp-Academy_backend
```

### Criar arquivo `.env` com base no `.env.example`
```.env
POSTGRES_USER=postgres
POSTGRES_HOST=localhost
POSTGRES_DB=testdb
POSTGRES_PASSWORD=password
POSTGRES_PORT=5432
```

### Executar docker-compose
```bash
docker-compose up -d
```

### Acessar a API no navegador
[http://localhost:8001](http://localhost:8001)