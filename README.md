# Using BDD and Consumer Driven Contracts in a Microservices Architecture

This project is an example of how to apply BDD to the development of an application based on microservices using Consumer
Driven Contract Testing.

You can get more detail about it in the following articles:
* [BDD in a Microservices Environment using Consumer Driven Contract Testing](https://javier.gilpereda.com/microservices/bdd-microservices-environment-using-consumer-driven-contract-testing-1)
* [Generating a Consumer Contract from an Angular application using Pact-JS](https://javier.gilpereda.com/microservices/generating-consumer-contract-angular-application-using-pact-js-bdd-and-cdc)
* [Rest API Gateway using Netflix Zuul and Pact Consumer Driven Contracts](https://javier.gilpereda.com/microservices/rest-api-gateway-using-netflix-zuul-and-pact-consumer-driven-contracts-bdd-and-cdc)
* [Implementing a SpringBoot RestAPI using Pact Consumer Driven Contracts](https://javier.gilpereda.com/microservices/implementing-springboot-restapi-using-pact-consumer-driven-contracts-bdd-and-cdc)

## Project description

The application allows us to browse a product catalogue and to get the details and available offers for 
each of the products in the catalogue.

The application is divided in the following components:
* api-gateway: An API Gateway service built using Spring Boot and Netflix Zuul to insulate the frontend application from
the complexity of the backend microservices.
* ng-frontend: The frontend web applications based on AngularJS 5.
* offers-service: A Spring Boot based microservice to access the available offers for a product.
* product-catalogue: A Spring Boot based microservice to access the product catalogue, in order to get the list of products
and the details of a product.

As example following three use scenarios have been implemented end to end, starting in the frontend application and ending 
in the two backend microservices.
* Get a list of product categories in the home page
* Get a list of products of a category
* Get the offers of a product

It uses [Cucumber](https://cucumber.io) as BDD framework in the frontend application and [Pact](https://docs.pact.io/) as
Consumer Driven Contract framework to translate the requirements for the different scenarios into consumer-provider contracts
that the different microservices must satisfy in order to consider the scenarios fully implemented.

The project is built using gradle and it has four subprojects, one for each of the components. This is made so for demonstrative
purposes, as in a real world application each of this components would have its own code base and would be owned and developed 
by a different team, and the build process would be more decoupled as is here.

Docker compose is used to run a [Pact Broker](https://docs.pact.io/documentation/sharings_pacts.html) used to 
share the pacts between the different services during the build process. Another docker compose application configuration
file can be used to start all the services that compose the application so we can see is in action.

## How to build the project

### Requirements
In order to build and run the demo application you should have previously installed the following requirements:
* Java JDK 8
* Google Chrome
* Docker and Docker Compose

### Build and start the application
To be able to build the application docker has to be running in your computer, so that the pact broker can be started.

To build and start the demo application go to the project's root directory and run:
```bash
# pull the docker images required by the pact broker to avoid timeouts during the application build
docker pull postgres
docker pull dius/pact-broker

# build the application and start it
./gradlew bddPactAppComposeUp
```

Now you can relax and wait until it has finished, which can take some minutes depending on your Internet connection.

To have an idea of how is the build process, this command will execute the following tasks:

* Start a Pact Broker using the [docker-compose/pact-broker-compose.yml](docker-compose/pact-broker-compose.yml) configuration file.
* Build the Web Frontend Application. For this it will:
  * Install all node dependencies
  * Run the unit tests with karma using chrome-headless
  * Run the E2E tests defined in the [features directory](ng-frontend/e2e/features). This step will create the pact required
  by the frontend application in the ng-frontend/pacts directory. The only entry point that the frontend
  application knows to access the API is the api-gateway, and therefore the pact that will be generated will have the 
  frontend application as consumer and the api-gateway as provider
  * Build the application
  * Build a docker container with the Web Frontend Application
  * Publish the generated consumer-provider contract to the pact broker
* Build the the API-Gateway. For this it will:
  * Run the integrations tests that will confirm that all the requests contained in his contract with the frontend application
  are correctly mapped to a supporting microservice. If all the requiests are correctly mapped it will report that the
  contract with the fronted application is fulfilled. In this step a contract will also be generated in api-gateway/build/pacts 
  for each of the backend microservices.
  * The Spring Boot application will be compiled and the corresponding jar will be created.
  * Build a docker container with the generated Spring Boot application
  * Public the generated consumer-provider contracts to the pact broker
* Build the Product Catalogue and the Offers microservices. For this it will:
  * Run the unit tests of each project
  * Run the integration test that will confirm that each of the microservice satisfies its contract with the api-gateway.
* Start all the services using the [docker-compose/application-compose.yml](docker-compose/application-compose.yml) configuration file.

After it has successfully finished we'll be able to access the application in [http://localhost:4200](http://localhost:4200).

We can also access the different contracts between the services accessing the Pact Broker in [http://localhost:1080](http://localhost:1080). 
