# Projeto Pokedex Ai

---

## Descrição do Projeto

O projeto **Pokedex Ai** tem como objetivo processar imagens enviadas por usuários, analisar seu conteúdo e armazenar informações derivadas sobre Pokémon. O sistema utiliza a API da OpenAI para realizar análises inteligentes em imagens e extrair dados estruturados sobre Pokémon, como nome, tipo e geração. A aplicação é desenvolvida em **Java** com o framework **Spring Boot** (versão 3.3.4), utilizando conceitos como serviços REST, mapeamento de entidades para banco de dados, e cobertura de código com **JUnit** e **Mockito** para testes automatizados.

---

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas, organizada da seguinte forma:

- **application**: Contém a lógica de negócios e os serviços principais da aplicação, incluindo mapeamento de dados e chamadas para adapters.
    - `dto`: Define os objetos de transferência de dados (Data Transfer Objects) como `PokemonInput` e `PokemonOutput`.
    - `mapper`: Classe responsável por converter os DTOs em entidades de domínio, como a `PokemonMapper`.
    - `service`: Contém a lógica de negócio principal, como a classe `PokemonService`, que realiza o processamento e salvamento de Pokémon.

- **domain**: Contém as classes que definem o modelo de domínio e as interfaces que devem ser implementadas por adaptadores e serviços de infraestrutura.
    - `model`: Contém as classes de domínio, como a classe `Pokemon`.
    - `adapter`: Define as interfaces de integração e comunicação externa, como `SendImageAdapterInterface`.

- **infra**: Contém as implementações de infraestrutura como adaptadores de APIs externas e repositórios para acesso ao banco de dados.
    - `http`: Responsável por fazer chamadas HTTP para serviços externos, como a API da OpenAI. Um exemplo é a classe `OpenAiAdapter`.
    - `database`: Implementações de repositórios para manipulação dos dados no banco, como `PokemonRepository`.

- **test**: Contém os testes unitários que validam as funcionalidades das classes principais, utilizando **JUnit** e **Mockito** para simular comportamentos e verificar a integração entre as camadas.

---

## Principais Classes e Funcionalidades

### 1. **PokemonService**
- Esta classe é o principal serviço da aplicação. Ela recebe um arquivo de imagem, delega a análise para um adapter que utiliza a API da OpenAI, mapeia o resultado em uma entidade `Pokemon` e, finalmente, salva os dados no banco de dados.
- **Métodos principais**:
    - `create(PokemonInput dto)`: Processa a imagem e salva a entidade de domínio mapeada.

### 2. **OpenAiAdapter**
- Classe responsável por fazer requisições HTTP à API da OpenAI para análise de imagens. Ela envia a imagem codificada em Base64 e recebe a resposta da API no formato de JSON, que é então interpretada para preencher os atributos do modelo `Pokemon`.

### 3. **PokemonMapper**
- Classe utilitária que converte o DTO `PokemonOutput` em uma entidade de domínio `Pokemon`. A conversão é necessária para que a resposta da API da OpenAI seja persistida no banco de dados corretamente.

### 4. **PokemonRepository**
- Interface que estende o `JpaRepository` para persistência e recuperação de dados no banco de dados. Implementa os métodos padrão para operações CRUD com a entidade `Pokemon`.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.4**
- **JUnit 5** e **Mockito** para testes unitários
- **OpenAI API** para processamento de imagens
- **MySQL** para persistência de dados
- **Jacoco** para cobertura de código

---

## Endpoints

### 1. **POST /create**
- Recebe uma imagem de Pokémon como um arquivo multipart e processa para identificar as informações do Pokémon.
- Exemplo de requisição via `curl`:
  ```bash
  curl --location 'http://localhost:8080/create' \
  --form 'file=@"/path/to/your/pokemon-image.jpg"'
  ```

---

## Como Rodar o Projeto

   ```bash
   git clone https://github.com/seu-usuario/pokedex-ai.git
   cd pokedex-ai
   ./mvnw clean install
   ./mvnw spring-boot:run
```

---

## Cobertura de Código
O projeto utiliza Jacoco para gerar relatórios de cobertura de código. A cobertura atual está em torno de 95% das classes principais e 100% dos testes.
## Como Rodar os testes

   ```bash
   ./mvnw test
```