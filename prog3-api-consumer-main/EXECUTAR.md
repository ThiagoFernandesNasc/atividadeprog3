# 🚀 Como Executar a API

## Método 1: Script Shell (Recomendado)

O script **prioriza Maven instalado** automaticamente:

```bash
bash rodar-api.sh
```

## Método 2: Maven Normal (Instalado) - Recomendado

Se tiver Maven instalado no sistema:

```bash
mvn clean spring-boot:run
```

Ou apenas executar:
```bash
mvn spring-boot:run
```

## Método 3: Maven Wrapper (Fallback)

Se não tiver Maven instalado, usa o wrapper:

```bash
.\mvnw.cmd spring-boot:run
```

---

## ⏹️ Como Parar a Aplicação

### Método 1: PowerShell
```powershell
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
```

### Método 2: No Terminal (onde está rodando)
Pressione `Ctrl + C`

### Método 3: Task Manager
1. Abra o Gerenciador de Tarefas (Ctrl + Shift + Esc)
2. Procure por processos `java.exe`
3. Finalize o processo

---

## ✅ POST - Criar Usuário

O endpoint POST permite criar usuários informando o endereço de duas formas:

### Endpoint:
```
POST http://localhost:8080/users
```

### Opção 1: Com CEP (busca automática)
```json
{
  "nome": "Maria Silva",
  "email": "maria@example.com",
  "cep": "01310100"
}
```

### Opção 2: Endereço Manual (parte por parte)
```json
{
  "nome": "João Santos",
  "email": "joao@example.com",
  "logradouro": "Rua das Flores, 123",
  "bairro": "Centro",
  "estado": "SP"
}
```

### Opção 3: CEP + Endereço Manual (endereço manual tem prioridade)
```json
{
  "nome": "Pedro Costa",
  "email": "pedro@example.com",
  "cep": "01310100",
  "logradouro": "Avenida Customizada",
  "bairro": "Bela Vista",
  "estado": "SP"
}
```

### Response (201 Created):
```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria@example.com",
  "endereco": {
    "cep": "01310-100",
    "logradouro": "Avenida Paulista",
    "bairro": "Bela Vista",
    "estado": "SP"
  }
}
```

### Exemplo com cURL (CEP):
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria Silva","email":"maria@example.com","cep":"01310100"}'
```

### Exemplo com cURL (Endereço Manual):
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Santos","email":"joao@example.com","logradouro":"Rua das Flores, 123","bairro":"Centro","estado":"SP"}'
```

### O que o POST faz:
✅ Cria um novo usuário
✅ Salva nome e email
✅ Se informar CEP: busca endereço via ViaCEP automaticamente
✅ Se informar logradouro/bairro/estado: salva diretamente (tem prioridade)
✅ Retorna status 201 Created

---

## ✅ PUT Completo Implementado

O endpoint PUT está completo e funcional:

### Endpoint:
```
PUT http://localhost:8080/users/{id}
```

### Request Body (JSON) - Opção 1: Com CEP:
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "cep": "01310100"
}
```

### Request Body (JSON) - Opção 2: Endereço Manual:
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "logradouro": "Rua Nova, 456",
  "bairro": "Jardim Primavera",
  "estado": "RJ"
}
```

### Response (200 OK):
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@example.com",
  "endereco": {
    "cep": "01310-100",
    "logradouro": "Avenida Paulista",
    "bairro": "Bela Vista",
    "estado": "SP"
  }
}
```

### Exemplo com cURL (CEP):
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@example.com","cep":"01310100"}'
```

### Exemplo com cURL (Endereço Manual):
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@example.com","logradouro":"Rua Nova, 456","bairro":"Jardim Primavera","estado":"RJ"}'
```

### O que o PUT faz:
✅ Atualiza nome do usuário
✅ Atualiza email do usuário  
✅ Se informar CEP: atualiza CEP e busca endereço via ViaCEP
✅ Se informar logradouro/bairro/estado: atualiza diretamente (tem prioridade)
✅ Retorna o usuário atualizado com endereço completo

---

## 📝 Todos os Endpoints Disponíveis

- **POST** `/users` - Criar usuário
- **GET** `/users` - Listar todos os usuários
- **GET** `/users/{id}` - Buscar usuário por ID
- **PUT** `/users/{id}` - Atualizar usuário (COMPLETO ✅)
- **DELETE** `/users/{id}` - Deletar usuário

---

## 🌐 Interface Web

Após iniciar a API, acesse:
- **Interface**: http://localhost:8080/
- **API REST**: http://localhost:8080/users

