# 🧪 Guia de Testes - FutureSkill API

## 📋 Pré-requisitos

1. **Java 17** instalado
2. **Maven** instalado
3. **MySQL** instalado e rodando
4. **Postman** ou **cURL** para testar os endpoints

## 🚀 Passo 1: Configurar o Banco de Dados

1. Abra o MySQL e crie o banco de dados (ou deixe o Spring Boot criar automaticamente):
```sql
CREATE DATABASE IF NOT EXISTS futureskill_db;
```

2. Configure as credenciais no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=sua_senha_mysql
```

## 🏃 Passo 2: Executar o Projeto

### Opção 1: Via Maven (Terminal)
```bash
mvn spring-boot:run
```

### Opção 2: Via IDE
- Execute a classe `FutureSkillApiApplication.java`
- Ou clique com botão direito no projeto → Run As → Spring Boot App

A API estará disponível em: **http://localhost:8080**

## 🧪 Passo 3: Testar os Endpoints

### 🔐 1. Autenticação

#### 1.1. Registrar um ADMIN
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Admin Sistema",
    "email": "admin@futureskill.com",
    "senha": "admin123",
    "role": "ADMIN"
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "nome": "Admin Sistema",
  "email": "admin@futureskill.com",
  "role": "ADMIN"
}
```

#### 1.2. Registrar um ALUNO
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senha123",
    "role": "ALUNO"
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 2,
  "nome": "João Silva",
  "email": "joao@email.com",
  "role": "ALUNO"
}
```

#### 1.3. Login como ADMIN
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@futureskill.com",
    "senha": "admin123"
  }'
```

**Resposta esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuarioId": 1,
  "email": "admin@futureskill.com",
  "role": "ADMIN"
}
```

**⚠️ IMPORTANTE:** Copie o `token` retornado! Você precisará dele para as próximas requisições.

#### 1.4. Login como ALUNO
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "senha": "senha123"
  }'
```

**⚠️ IMPORTANTE:** Copie o `token` do ALUNO também!

---

### 📚 2. Gestão de Cursos (ADMIN)

**Substitua `{TOKEN_ADMIN}` pelo token obtido no login do ADMIN.**

#### 2.1. Criar um Curso (ADMIN)
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ADMIN}" \
  -d '{
    "titulo": "Java Spring Boot do Zero",
    "descricao": "Aprenda Spring Boot desde o início até o avançado",
    "categoria": "Programação",
    "cargaHoraria": 40
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "titulo": "Java Spring Boot do Zero",
  "descricao": "Aprenda Spring Boot desde o início até o avançado",
  "categoria": "Programação",
  "cargaHoraria": 40
}
```

#### 2.2. Criar mais cursos (ADMIN)
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ADMIN}" \
  -d '{
    "titulo": "React Avançado",
    "descricao": "Desenvolvimento de aplicações React modernas",
    "categoria": "Frontend",
    "cargaHoraria": 30
  }'
```

```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ADMIN}" \
  -d '{
    "titulo": "DevOps com Docker",
    "descricao": "Containerização e orquestração de aplicações",
    "categoria": "DevOps",
    "cargaHoraria": 25
  }'
```

#### 2.3. Listar Todos os Cursos (Qualquer usuário autenticado)
```bash
curl -X GET http://localhost:8080/cursos \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

**Resposta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Java Spring Boot do Zero",
    "descricao": "Aprenda Spring Boot desde o início até o avançado",
    "categoria": "Programação",
    "cargaHoraria": 40
  },
  {
    "id": 2,
    "titulo": "React Avançado",
    "descricao": "Desenvolvimento de aplicações React modernas",
    "categoria": "Frontend",
    "cargaHoraria": 30
  }
]
```

#### 2.4. Buscar Curso por ID
```bash
curl -X GET http://localhost:8080/cursos/1 \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

#### 2.5. Buscar Cursos por Categoria
```bash
curl -X GET http://localhost:8080/cursos/categoria/Programação \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

#### 2.6. Atualizar Curso (ADMIN)
```bash
curl -X PUT http://localhost:8080/cursos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ADMIN}" \
  -d '{
    "titulo": "Java Spring Boot do Zero - Atualizado",
    "descricao": "Aprenda Spring Boot desde o início até o avançado - Versão 2025",
    "categoria": "Programação",
    "cargaHoraria": 50
  }'
```

#### 2.7. Excluir Curso (ADMIN)
```bash
curl -X DELETE http://localhost:8080/cursos/3 \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

**Resposta esperada (204 No Content)**

---

### 📝 3. Gestão de Inscrições

#### 3.1. Realizar Inscrição (ALUNO)

**Substitua `{TOKEN_ALUNO}` pelo token obtido no login do ALUNO.**

```bash
curl -X POST http://localhost:8080/inscricoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ALUNO}" \
  -d '{
    "cursoId": 1
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "usuarioId": 2,
  "usuarioNome": "João Silva",
  "cursoId": 1,
  "cursoTitulo": "Java Spring Boot do Zero",
  "dataInscricao": "2025-01-15T10:30:00"
}
```

#### 3.2. Tentar Inscrição Duplicada (Deve falhar)
```bash
curl -X POST http://localhost:8080/inscricoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ALUNO}" \
  -d '{
    "cursoId": 1
  }'
```

**Resposta esperada (400 Bad Request):**
```json
{
  "timestamp": "2025-01-15T10:35:00",
  "status": 400,
  "error": "Erro de negócio",
  "message": "Você já está inscrito neste curso",
  "path": "/inscricoes"
}
```

#### 3.3. Listar Minhas Inscrições (ALUNO)
```bash
curl -X GET http://localhost:8080/inscricoes/minhas \
  -H "Authorization: Bearer {TOKEN_ALUNO}"
```

**Resposta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "usuarioId": 2,
    "usuarioNome": "João Silva",
    "cursoId": 1,
    "cursoTitulo": "Java Spring Boot do Zero",
    "dataInscricao": "2025-01-15T10:30:00"
  }
]
```

#### 3.4. Listar Todas as Inscrições (ADMIN)
```bash
curl -X GET http://localhost:8080/inscricoes \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

#### 3.5. Cancelar Inscrição (ALUNO)
```bash
curl -X DELETE http://localhost:8080/inscricoes/1 \
  -H "Authorization: Bearer {TOKEN_ALUNO}"
```

**Resposta esperada (204 No Content)**

---

### ❌ 4. Testes de Erros e Validações

#### 4.1. Tentar Criar Curso sem Autenticação
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Curso Teste",
    "descricao": "Teste",
    "categoria": "Teste",
    "cargaHoraria": 10
  }'
```

**Resposta esperada (401 Unauthorized)**

#### 4.2. Tentar Criar Curso como ALUNO (Deve ser negado)
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ALUNO}" \
  -d '{
    "titulo": "Curso Teste",
    "descricao": "Teste",
    "categoria": "Teste",
    "cargaHoraria": 10
  }'
```

**Resposta esperada (403 Forbidden):**
```json
{
  "timestamp": "2025-01-15T10:40:00",
  "status": 403,
  "error": "Acesso negado",
  "message": "Você não tem permissão para acessar este recurso",
  "path": "/cursos"
}
```

#### 4.3. Tentar Registrar com Email Duplicado
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Outro Admin",
    "email": "admin@futureskill.com",
    "senha": "senha123",
    "role": "ADMIN"
  }'
```

**Resposta esperada (400 Bad Request):**
```json
{
  "timestamp": "2025-01-15T10:45:00",
  "status": 400,
  "error": "Erro de negócio",
  "message": "Email já está em uso",
  "path": "/auth/register"
}
```

#### 4.4. Tentar Login com Credenciais Inválidas
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@futureskill.com",
    "senha": "senha_errada"
  }'
```

**Resposta esperada (401 Unauthorized):**
```json
{
  "timestamp": "2025-01-15T10:50:00",
  "status": 401,
  "error": "Credenciais inválidas",
  "message": "Email ou senha incorretos",
  "path": "/auth/login"
}
```

#### 4.5. Tentar Buscar Curso Inexistente
```bash
curl -X GET http://localhost:8080/cursos/999 \
  -H "Authorization: Bearer {TOKEN_ADMIN}"
```

**Resposta esperada (404 Not Found):**
```json
{
  "timestamp": "2025-01-15T10:55:00",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Curso não encontrado com ID: 999",
  "path": "/cursos/999"
}
```

#### 4.6. Tentar Criar Curso com Dados Inválidos
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {TOKEN_ADMIN}" \
  -d '{
    "titulo": "",
    "descricao": "Teste",
    "categoria": "Teste",
    "cargaHoraria": -10
  }'
```

**Resposta esperada (400 Bad Request) com detalhes de validação**

---

## 🧪 Testando com Postman

### Configuração Inicial

1. **Criar uma Collection** chamada "FutureSkill API"
2. **Criar uma variável de ambiente:**
   - Nome: `base_url`
   - Valor: `http://localhost:8080`
   - Nome: `token_admin`
   - Valor: (será preenchido após login)
   - Nome: `token_aluno`
   - Valor: (será preenchido após login)

### Fluxo de Teste Completo no Postman

1. **Registrar ADMIN** → Copiar resposta
2. **Login ADMIN** → Copiar token e salvar em `token_admin`
3. **Criar Curso** (usar `{{token_admin}}` no header Authorization)
4. **Registrar ALUNO** → Copiar resposta
5. **Login ALUNO** → Copiar token e salvar em `{{token_aluno}}`
6. **Listar Cursos** (usar `{{token_aluno}}`)
7. **Realizar Inscrição** (usar `{{token_aluno}}`)
8. **Listar Minhas Inscrições** (usar `{{token_aluno}}`)
9. **Tentar Criar Curso como ALUNO** (deve retornar 403)

---

## ✅ Checklist de Testes

- [ ] Registrar ADMIN
- [ ] Registrar ALUNO
- [ ] Login ADMIN
- [ ] Login ALUNO
- [ ] Criar Curso (ADMIN)
- [ ] Listar Cursos
- [ ] Buscar Curso por ID
- [ ] Buscar Cursos por Categoria
- [ ] Atualizar Curso (ADMIN)
- [ ] Excluir Curso (ADMIN)
- [ ] Realizar Inscrição (ALUNO)
- [ ] Tentar Inscrição Duplicada (deve falhar)
- [ ] Listar Minhas Inscrições (ALUNO)
- [ ] Listar Todas as Inscrições (ADMIN)
- [ ] Cancelar Inscrição (ALUNO)
- [ ] Testar acesso negado (ALUNO tentando criar curso)
- [ ] Testar autenticação (requisição sem token)
- [ ] Testar validações (dados inválidos)
- [ ] Testar recursos não encontrados (404)

---

## 🐛 Troubleshooting

### Erro: "Cannot connect to database"
- Verifique se o MySQL está rodando
- Verifique as credenciais no `application.properties`
- Verifique se o banco `futureskill_db` existe

### Erro: "Port 8080 already in use"
- Altere a porta no `application.properties`: `server.port=8081`
- Ou pare o processo que está usando a porta 8080

### Erro: "401 Unauthorized"
- Verifique se o token está sendo enviado no header: `Authorization: Bearer {token}`
- Verifique se o token não expirou (24 horas)
- Faça login novamente para obter um novo token

### Erro: "403 Forbidden"
- Verifique se o usuário tem a role correta (ADMIN ou ALUNO)
- Verifique se está usando o token correto para a operação

---

## 📊 Verificando no Banco de Dados

Você pode verificar os dados diretamente no MySQL:

```sql
-- Ver usuários
SELECT * FROM usuarios;

-- Ver cursos
SELECT * FROM cursos;

-- Ver inscrições
SELECT * FROM inscricoes;
```

---

**Boa sorte com os testes! 🚀**

