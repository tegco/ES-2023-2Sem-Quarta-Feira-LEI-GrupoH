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

gestaohorarios
├───data                     # Diretório para armazenamento dos arquivos JSON
├───src
│   ├───main
│   │   ├───java
│   │   │   └───pt.iscte-iul.gestaohorarios
│   │   │       ├───GestaoDeHorariosApplication.java  # Aplicação principal do Spring Boot
│   │   │       ├───controller     # Controladores para gerenciar solicitações HTTP
│   │   │       │   └───HorariosController.java
│   │   │       ├───model          # Modelos para representar entidades (p.ex., Horario)
│   │   │       │   └───Horario.java
│   │   │       └───service        # Serviços para executar a lógica do negócio
│   │   │           └───ConversorCSVJSON.java
│   │   ├───resources
│   │   │   ├───static             # Arquivos CSS, JavaScript e imagens
│   │   │   ├───templates          # Templates HTML Thymeleaf
│   │   │   └───application.properties   # Arquivo de configuração do Spring Boot
│   ├───test # Pasta de teste (a princípio não usado neste projeto)
│   │   └───java
│   │       └───pt.iscte-iul.gestaohorarios
│   │           ├───controller
│   │           ├───model
│   │           ├───service
│   │           └───repository
└───pom.xml                    # Arquivo de configuração e dependências do Maven