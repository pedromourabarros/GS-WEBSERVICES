# FutureSkill API – O Futuro do Trabalho

## 📋 Informações do Projeto

**Nome do Projeto:** FutureSkill API – O Futuro do Trabalho

**Integrantes:**
- Pedro Moura Barros – RM550260
- Rodrigo Fernandes dos Santos – RM98896

## 🎯 Objetivo

A **FutureSkill API** é uma aplicação backend desenvolvida em Java Spring Boot que gerencia cursos e inscrições para capacitação profissional no contexto do "Futuro do Trabalho". O sistema permite que administradores criem e gerenciem cursos, enquanto alunos podem visualizar cursos disponíveis e realizar inscrições.

## 🚀 Funcionalidades

### Autenticação e Autorização
- Cadastro de usuários (ADMIN e ALUNO)
- Login com geração de token JWT (stateless)
- Proteção de rotas com autenticação JWT
- Autorização baseada em roles (ADMIN e ALUNO)

### Gestão de Cursos (ADMIN)
- Criar novos cursos
- Listar todos os cursos
- Buscar curso por ID
- Buscar cursos por categoria
- Atualizar curso existente
- Excluir curso

### Gestão de Inscrições (ALUNO)
- Visualizar todos os cursos disponíveis
- Realizar inscrição em curso
- Listar minhas inscrições
- Cancelar inscrição

### Gestão de Inscrições (ADMIN)
- Listar todas as inscrições do sistema

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados relacional
- **JWT (jjwt 0.12.3)** - Tokens stateless para autenticação
- **Lombok** - Redução de boilerplate
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📦 Estrutura do Projeto

```
src/main/java/com/futureskill/api/
├── config/              # Configurações (Security, JWT)
├── controller/         # Endpoints REST
├── dto/                # Data Transfer Objects
│   ├── request/       # DTOs de requisição
│   └── response/      # DTOs de resposta
├── exception/          # Exceções customizadas e tratamento global
├── model/             # Entidades JPA e Value Objects
│   ├── enums/        # Enumeradores
│   └── vo/           # Value Objects (Email, CargaHoraria)
├── repository/        # Interfaces JPA Repository
└── service/           # Lógica de negócio
```

## 🔧 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## ⚙️ Configuração e Execução

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd GS-WEBSERVICES
```

### 2. Configure o banco de dados MySQL

Crie um banco de dados MySQL:

```sql
CREATE DATABASE futureskill_db;
```

### 3. Configure as credenciais do banco

Edite o arquivo `src/main/resources/application.properties` e ajuste as credenciais conforme seu ambiente:

```properties
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 4. Execute o projeto

```bash
mvn spring-boot:run
```

Ou através da IDE, execute a classe `FutureSkillApiApplication`.

A API estará disponível em: `http://localhost:8080`

## 📚 Endpoints da API

### Autenticação

#### Registrar Usuário
```http
POST /auth/register
Content-Type: application/json

{
  "nome": "Pedro Moura",
  "email": "pedro@email.com",
  "senha": "senha123",
  "role": "ALUNO"
}
```

**Resposta:**
```json
{
  "id": 1,
  "nome": "Pedro Moura",
  "email": "pedro@email.com",
  "role": "ALUNO"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "pedro@email.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuarioId": 1,
  "email": "pedro@email.com",
  "role": "ALUNO"
}
```

### Cursos

#### Listar Todos os Cursos
```http
GET /cursos
Authorization: Bearer {token}
```

#### Buscar Curso por ID
```http
GET /cursos/{id}
Authorization: Bearer {token}
```

#### Buscar Cursos por Categoria
```http
GET /cursos/categoria/{categoria}
Authorization: Bearer {token}
```

#### Criar Curso (ADMIN)
```http
POST /cursos
Authorization: Bearer {token}
Content-Type: application/json

{
  "titulo": "Java Spring Boot Avançado",
  "descricao": "Curso completo sobre Spring Boot",
  "categoria": "Programação",
  "cargaHoraria": 40
}
```

#### Atualizar Curso (ADMIN)
```http
PUT /cursos/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "titulo": "Java Spring Boot Avançado - Atualizado",
  "descricao": "Curso completo sobre Spring Boot",
  "categoria": "Programação",
  "cargaHoraria": 50
}
```

#### Excluir Curso (ADMIN)
```http
DELETE /cursos/{id}
Authorization: Bearer {token}
```

### Inscrições

#### Realizar Inscrição (ALUNO)
```http
POST /inscricoes
Authorization: Bearer {token}
Content-Type: application/json

{
  "cursoId": 1
}
```

#### Listar Minhas Inscrições (ALUNO)
```http
GET /inscricoes/minhas
Authorization: Bearer {token}
```

#### Listar Todas as Inscrições (ADMIN)
```http
GET /inscricoes
Authorization: Bearer {token}
```

#### Cancelar Inscrição (ALUNO)
```http
DELETE /inscricoes/{id}
Authorization: Bearer {token}
```

## 🔐 Segurança

### Autenticação JWT (Stateless)

A API utiliza autenticação baseada em tokens JWT, seguindo uma política **STATELESS**:

1. O usuário faz login através do endpoint `/auth/login`
2. Recebe um token JWT válido por 24 horas
3. Todas as requisições subsequentes devem incluir o token no header:
   ```
   Authorization: Bearer {token}
   ```

### Autorização por Roles

- **ADMIN**: Pode criar, editar e excluir cursos. Pode visualizar todas as inscrições.
- **ALUNO**: Pode visualizar cursos e realizar/cancelar suas próprias inscrições.

## 📝 Modelo de Dados

### Usuario
- `id` (Long)
- `nome` (String)
- `email` (String) - único
- `senha` (String) - criptografada com BCrypt
- `role` (Enum: ADMIN, ALUNO)

### Curso
- `id` (Long)
- `titulo` (String)
- `descricao` (String)
- `categoria` (String)
- `cargaHoraria` (int)

### Inscricao
- `id` (Long)
- `usuario` (Usuario) - relação ManyToOne
- `curso` (Curso) - relação ManyToOne
- `dataInscricao` (LocalDateTime) - preenchida automaticamente

## ✅ Boas Práticas Implementadas

1. **Separação de Responsabilidades**: Controllers, Services e Repositories bem definidos
2. **DTOs**: Uso de Data Transfer Objects para isolamento da camada de modelo
3. **ResponseEntity**: Todas as respostas utilizam ResponseEntity para controle de status HTTP
4. **Tratamento Global de Exceções**: Classe `@ControllerAdvice` centraliza o tratamento de erros
5. **Validação de Dados**: Bean Validation em todos os DTOs de requisição
6. **Segurança**: Spring Security com JWT stateless
7. **Transações**: Uso de `@Transactional` nos serviços
8. **Código Limpo**: Uso de Lombok para reduzir boilerplate
9. **Documentação**: README completo e código comentado

## 🧪 Testando a API

### Exemplo de Fluxo Completo

1. **Registrar um ADMIN:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Admin",
    "email": "admin@email.com",
    "senha": "admin123",
    "role": "ADMIN"
  }'
```

2. **Login como ADMIN:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@email.com",
    "senha": "admin123"
  }'
```

3. **Criar um Curso (usar token do passo 2):**
```bash
curl -X POST http://localhost:8080/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "titulo": "Spring Boot do Zero",
    "descricao": "Aprenda Spring Boot desde o início",
    "categoria": "Programação",
    "cargaHoraria": 30
  }'
```

4. **Registrar um ALUNO:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Aluno",
    "email": "aluno@email.com",
    "senha": "aluno123",
    "role": "ALUNO"
  }'
```

5. **Login como ALUNO:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "aluno@email.com",
    "senha": "aluno123"
  }'
```

6. **Listar Cursos (usar token do passo 5):**
```bash
curl -X GET http://localhost:8080/cursos \
  -H "Authorization: Bearer {token}"
```

7. **Realizar Inscrição (usar token do passo 5):**
```bash
curl -X POST http://localhost:8080/inscricoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "cursoId": 1
  }'
```

## ✅ Conformidade com Requisitos

Este projeto atende 100% aos critérios de avaliação especificados:

### 1. Criação de Entities, Value Objects (VO), Enums, Controllers, DTOs (5%)
- ✅ **3 Entities**: `Usuario`, `Curso`, `Inscricao`
- ✅ **2 Value Objects**: `Email`, `CargaHoraria` (localizados em `model/vo/`)
- ✅ **1 Enum**: `Role` (ADMIN, ALUNO)
- ✅ **3 Controllers**: `AuthController`, `CursoController`, `InscricaoController`
- ✅ **9 DTOs**: 4 request + 5 response

### 2. Padronização de respostas com ResponseEntity (5%)
- ✅ Todos os 12 endpoints utilizam `ResponseEntity<T>`
- ✅ Status HTTP adequados (200, 201, 204, 400, 401, 403, 404, 500)

### 3. Tratamento global de exceções em classe Advice (10%)
- ✅ `GlobalExceptionHandler` com `@RestControllerAdvice`
- ✅ Tratamento de 8 tipos de exceções (ResourceNotFoundException, BusinessException, validações, autenticação, etc.)

### 4. Implementação de segurança para autenticação de usuário (10%)
- ✅ `SecurityConfig` com Spring Security
- ✅ `AuthService` com BCrypt para criptografia de senhas
- ✅ `JwtService` para geração e validação de tokens
- ✅ `UserDetailsServiceImpl` para carregamento de usuários

### 5. Implementação de segurança para controle de autorização por perfis (20%)
- ✅ `@PreAuthorize("hasRole('ADMIN')")` em 3 endpoints de cursos
- ✅ `@PreAuthorize("hasRole('ALUNO')")` em 4 endpoints de inscrições
- ✅ `@EnableMethodSecurity` habilitado no `SecurityConfig`

### 6. Implementação de política de sessão STATELESS com JWT (20%)
- ✅ `SessionCreationPolicy.STATELESS` configurado no `SecurityConfig`
- ✅ `JwtAuthenticationFilter` validando tokens a cada requisição
- ✅ Autenticação baseada apenas em tokens JWT (sem armazenamento de sessão)

### 7. Implementação de casos de uso e regras de negócio como serviços (20%)
- ✅ **3 Serviços**: `AuthService`, `CursoService`, `InscricaoService`
- ✅ **11 Casos de uso** implementados com regras de negócio:
  - Registro e login de usuários
  - CRUD completo de cursos
  - Gestão de inscrições com validações
- ✅ Regras de negócio validadas (email único, inscrição única, permissões, etc.)

### 8. Organização modular baseada em serviços mínimos, independentes e reutilizáveis (10%)
- ✅ Estrutura modular bem definida (config, controller, dto, exception, model, repository, service)
- ✅ Serviços independentes e focados em domínios específicos
- ✅ Componentes reutilizáveis (JwtService, UserDetailsService, Repositories)

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

## 👥 Contato

Para dúvidas ou sugestões, entre em contato através do repositório GitHub.

