# 📚 Sistema de Biblioteca

Este projeto é um sistema de gerenciamento de biblioteca feito em Java, utilizando estrutura modular com classes organizadas por funcionalidades. Ele permite o controle de usuários, funcionários, livros e diretoria, bem como o login e gerenciamento dos dados da biblioteca via arquivos JSON.

> ⚙️ Este projeto foi produzido como forma de **treinamento e prática de programação orientada a objetos com Java**.

---
## 🚀 Funcionalidades

- Cadastro e gerenciamento de livros
- Registro de usuários e funcionários
- Acesso diferenciado por tipo de usuário (diretor, funcionário, usuário)
- Login com controle de acesso
- Armazenamento e leitura de dados via arquivos JSON
- Interface de linha de comando intuitiva
---
## 🛠️ Tecnologias Utilizadas

- Java 11+
- Maven
- JSON para persistência de dados
- Estrutura baseada em pacotes (`livro`, `pessoas`, `sistema`, `util`, `jsonData`)
---
## 📂 Estrutura do Projeto

```
sitemaBiblioteca/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   ├── Main.java
│                   ├── livro/           # Classes relacionadas aos livros
│                   ├── pessoas/         # Diretor, Funcionário, Usuário
│                   ├── sistema/         # Lógica do sistema e login
│                   ├── util/            # Funções auxiliares e controle de acesso
│                   └── jsonData/        # Leitura e escrita de dados JSON
├── biblioteca.json
├── diretor.json
├── funcionarios.json
├── usuarios.json
├── pom.xml
└── .gitignore
```
---
## ▶️ Como Executar

1. Clone este repositório:

```bash
git clone https://github.com/seu-usuario/sitemaBiblioteca.git
cd sitemaBiblioteca
```

2. Compile o projeto com Maven:

```bash
mvn clean compile
```

3. Execute o projeto:

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 📝 Requisitos

- Java JDK 11 ou superior
- Maven 3.6+

---

## 🔐 Estrutura dos Arquivos JSON

Os dados são salvos em arquivos JSON diretamente no diretório raiz:

- `biblioteca.json` – Dados gerais da biblioteca
- `diretor.json` – Informações do diretor
- `funcionarios.json` – Lista de funcionários cadastrados
- `usuarios.json` – Lista de usuários da biblioteca

> ⚠️ Esses arquivos são ignorados no `.gitignore` para proteger dados sensíveis durante o versionamento.
---
## 👨‍💻 Autor

Desenvolvido por Caio Klöppel.  
Contato: caiokloppelbr@gmail.com
