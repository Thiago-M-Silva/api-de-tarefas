OBS:
- Fiquei em duvida quanto ao framework, pois no enunciado mencionava "Framework Quarkus*/Spring Boot", 
interpretei que pudesse ser um dos dois, e utilizei o SpringBoot, pois possuo maior familiaridade.

Tecnologias utilizadas:
- Java 20
- SpringBoot 3.0.6
- IDE: IntelliJ Community

Organizacao:
    Dentro de src -> main -> java -> com.api_de_tarefas temos
        - Controller: endpoints para as classes Pessoa e Tarefa
        - Model: Classe, Repository e DTOs de Pessoa e Tarefa, além de um DTO para Departamento
    Testes das classes Controller estao em:
        src -> test -> java -> com.api_de_tarefas -> Controller