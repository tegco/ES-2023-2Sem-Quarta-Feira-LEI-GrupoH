# Gestão de Horários

Este projeto é uma aplicação Spring Boot para gerenciamento de horários. Ele permite:
- Carregar arquivos CSV de horários
- Converter os horários para formato JSON e armazená-los
- Visualizar os horários em formato HTML

## Dependências

- Spring Web: Para criar uma aplicação web com Spring MVC
- Thymeleaf: Para processar e renderizar templates HTML
- Apache Commons CSV: Para manipular arquivos CSV
- Jackson Databind (Incluido no Spring Web): Para manipular arquivos JSON

## Executando a aplicação

Para executar a aplicação usando a sua IDE, abra a classe `GestaoHorariosApplication` e execute o método `main`.

Também pode executar a aplicação através da linha de comando: `./mvnw spring-boot:run`

## Estrutura do projeto

![(folder structure image)](folderstructure.svg)