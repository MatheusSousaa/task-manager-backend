# ⚡ TaskFlow API • Back-end

API REST de alto desempenho para gerenciamento de tarefas, desenvolvida com **Java 26**, **Spring Boot 4** e banco de dados relacional **PostgreSQL 18**.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 26
- **Framework:** Spring Boot 4.1.1
- **Persistência de Dados:** Spring Data JPA / Hibernate
- **Banco de Dados:** PostgreSQL 18
- **Produtividade:** Project Lombok
- **Validação:** Jakarta Bean Validation
- **Gerenciador de Dependências:** Apache Maven (com Maven Wrapper `mvnw`)

---

## 🏛️ Arquitetura em Camadas

O projeto segue o padrão corporativo de separação de responsabilidades:

```text
com.taskmanager
├── controller/   # Endpoints REST (HTTP GET, POST, PUT, PATCH, DELETE) com suporte a CORS
├── dto/          # Records imutáveis para transferência de dados e validações
├── exception/    # Tratamento customizado de exceções de negócio
├── model/        # Entidades JPA mapeadas para tabelas PostgreSQL e Enums
├── repository/   # Interfaces Spring Data JPA para comunicação SQL
└── service/      # Camada de lógica de negócio e transações (@Transactional)
```

---

## 📡 Endpoints da API

Base URL: `http://localhost:8080/api/tarefas`

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/tarefas` | Lista todas as tarefas (com filtro opcional `?status=...`) |
| `GET` | `/api/tarefas/{id}` | Busca uma tarefa específica por ID |
| `POST` | `/api/tarefas` | Cria uma nova tarefa com validações de campos |
| `PUT` | `/api/tarefas/{id}` | Atualiza título, descrição ou prioridade da tarefa |
| `PATCH` | `/api/tarefas/{id}/status` | Alterna o status da tarefa (`PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`) |
| `DELETE` | `/api/tarefas/{id}` | Remove a tarefa do banco de dados |

---

## 🚀 Como Executar Localmente

### 1. Pré-requisitos
- **Java 21+** instalado (desenvolvido no Java 26)
- **PostgreSQL 18** rodando na porta `5432` com banco `tarefas_db` criado:
  ```sql
  CREATE DATABASE tarefas_db;
  ```

### 2. Configuração (`src/main/resources/application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tarefas_db
spring.datasource.username=postgres
spring.datasource.password=12345
```

### 3. Rodar a Aplicação
No terminal dentro da pasta `backend`:
```powershell
.\mvnw.cmd spring-boot:run
```
A API iniciará na porta **`8080`**.

---

## 👤 Autor
Desenvolvido por **Matheus Sousa** ([@MatheusSousaa](https://github.com/MatheusSousaa)) com assistência de IA.
