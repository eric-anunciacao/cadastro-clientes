# [Cadastro de Clientes](https://github.com/eric-anunciacao/cadastro-clientes)

Este é um MVP de cadastro de clientes, com a possibilidade de:

* Criação de novos clientes;
* Atualização de clientes existentes;
* Listar os clientes de forma paginada;
* Consultar por atributos cadastrais do cliente;
* Informar a idade do cliente a partir da data de nascimento informada;


## Design

Essa API foi desenvolvida utilizando Java 11, Spring Boot e MySQL. 

O conceito de design de software utilizado na construção da aplicação foi Clean Architecture. Com isso, é possível identificar alguns conceitos de SOLID, tais como:
* **Single Responsibility Principle (SRP)**, onde cada classe deve ter apenas um motivo para mudar. É possível identificar esse princípio nas classes que implementam os Casos de Uso;
* **Interface Segregation Principle (ISP)**, onde as interfaces criadas foram refinadas para que sejam específicas do cliente. É possível ver esse princípio nas interfaces de Caso de Uso, onde cada Caso de Uso possui apenas um método público;
* **Dependency Inversion Principle (DIP)**, onde módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender da abstração. Esse princípio pode ser visto na comunicação entre as camadas do projeto, que se comunicam umas com as outras através do contrato disponibilizado pelas suas interfaces.

Também é possível implementar o DDD, partindo do princípio que a entidade de domínio Cliente é o ponto principal da aplicação e responsável pelas regras principais do cliente, como por exemplo, calcular a idade a partir da sua data de nascimento.

A ideia principal foi utilizar o mínimo de frameworks possíveis, visando o controle por parte do desenvolvedor. Esse ponto fica visível nas classes do tipo Mapper e Builder.

Como boas práticas de desenvolvimento, é possível identificar:
* Todas as classes, métodos, atributos e variáveis foram criados com nomes que revelam a intenção;
* A maiorida dos métodos não possuem mais do que 10 linhas de código;
* A aplicação foi desenvolvida seguindo o TDD;

Os testes unitários e integrados estão sendo executados com o banco de dados H2 e possuem uma cobertura de código de 95%. Em alguns casos, foi utilizado o Mockito para simular o retorno dos métodos.

Para diminuir a exposição dos dados do cliente, ao invés de utilizar o CPF como uma chave, o identificador único de cada cliente é um código hash gerado aleatoriamente. No entanto, o CPF é considerado único e existe uma validação para que o mesmo CPF não seja utilizado para mais de um cliente.

## Pré-requisitos

Para executar a aplicação, é necessário ter configurado na máquina as seguintes ferramentas:

* Maven
* Java 11
* Docker

## Execução da aplicação

Utilizando o Docker, é possível criar a estrutura de banco de dados e levantar a aplicação seguindo os passos abaixo:

1. Construir a aplicação com Maven executando o comando `mvn clean install`
2. Acessar o diretório principal do projeto (onde encontra-se o arquivo `docker-compose.yaml`)
3. Executar o comando `docker-compose up --build` para iniciar a aplicação
4. Ao concluir o passo anterior, você poderá testar o acesso da aplicação utilizando a Collection do Postman `cadastro-clientes.postman_collection.json` que encontra-se no diretório raiz do projeto.