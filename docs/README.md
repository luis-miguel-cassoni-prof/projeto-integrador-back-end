# Achados e Perdidos da UNIFIO

*Projeto Integrador de Engenharia de Software · Back-end*

O Achados e Perdidos da UNIFIO aproxima quem perdeu um objeto no campus de quem o encontrou. A comunidade acadêmica pode publicar e consultar anúncios com a descrição do item, a data, o local e os meios de contato para combinar a devolução.

## Objetivo do back-end

A API autentica os usuários e gerencia as publicações de itens perdidos e encontrados. Cada anúncio reúne o autor, o local da ocorrência e os contatos que ele escolheu disponibilizar. A API também registra se a publicação está ativa ou finalizada.

## Tecnologias utilizadas

| Tecnologia | Uso no projeto |
| --- | --- |
| Java 21 e Spring Boot | Desenvolvimento da API REST |
| Spring Security | Autenticação e controle de acesso |
| Spring Data JPA, Hibernate e MySQL | Mapeamento das entidades e persistência dos dados |
| Jakarta Validation | Validação dos dados recebidos pela API |
| Maven Wrapper | Resolução de dependências e execução da aplicação |
| Docker Compose e Dev Containers | Configuração do ambiente de desenvolvimento com Java e MySQL |

## Como executar

O repositório inclui um Dev Container que configura o Java, inicia o MySQL e fornece à API as credenciais do banco.

1. Instale o **Git**, o **Docker** com suporte a Compose e o **VS Code** com a extensão **Dev Containers**. Inicie o Docker.

2. Clone o repositório e abra a pasta no VS Code.

   ```bash
   git clone https://github.com/luis-miguel-cassoni-prof/projeto-integrador-back-end.git
   cd projeto-integrador-back-end
   code .
   ```

3. No VS Code, pressione `F1`, selecione **Dev Containers: Reopen in Container** e aguarde a preparação do ambiente.

4. No terminal integrado do contêiner, inicie a API.

   ```bash
   cd /workspaces/pi
   bash mvnw spring-boot:run
   ```

5. Quando o log confirmar a inicialização do Spring Boot, use `http://localhost:8080` como endereço base da API.

## Diagrama de classes

O diagrama apresenta as cinco entidades do sistema, seus relacionamentos e os enums que definem a permissão do usuário, o tipo e o status de cada publicação.

![Diagrama de classes do Achados e Perdidos da UNIFIO](./docs/mermaid-diagram.png)

## Integrantes

| Nome | RA |
| --- | --- |
| Luís Miguel Cassoni | 273195 |
| Luiz Henrique Azevedo Campese | 273740 |
| Vinicius Pavani Tossi | 271145 |
| Vinicius Francisco dos Santos Ramos | 272586 |
