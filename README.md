# E-Banking Microservices Application

A microservices-based e-banking application developed using **Java, Spring Boot, and Spring Cloud**. This project demonstrates how to decompose a backend application into independent services, expose REST APIs, centralize request routing through an API Gateway, and manage service registration and discovery.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [1. Customer Service](#1-customer-service)
- [2. E-Bank Service](#2-e-bank-service)
- [3. API Gateway](#3-api-gateway)
- [4. Discovery Service](#4-discovery-service)
- [API Endpoints](#api-endpoints)
- [Running the Application](#running-the-application)
- [Key Learning Objectives](#key-learning-objectives)

## Overview

The application is organized into four main components:

- **Customer Service:** Manages customer information and exposes customer-related REST endpoints.
- **E-Bank Service:** Manages bank account information and exposes account-related REST endpoints.
- **API Gateway:** Provides a single entry point for client requests and routes them to the appropriate microservice.
- **Discovery Service:** Provides service registration and discovery, allowing microservices to register themselves and be located by other components.

The goal is to understand how independent Spring Boot applications can work together to build a modular and maintainable backend system.

## Architecture

The following picture illustrates the architecture of the application.

<img width="2560" height="1462" alt="image" src="https://github.com/user-attachments/assets/cb13de80-47a3-4adf-aa23-7e47193521f4" />


### Architecture Components

| Component | Port | Responsibility |
|---|---:|---|
| Customer Service | `8056` | Customer management |
| E-Bank Service | `8057` | Bank account management |
| API Gateway | `9999` | Request routing and centralized entry point |
| Discovery Service | `8761` | Service registration and discovery |

### Request Flow

1. The client sends a request to the API Gateway.
2. The gateway routes the request to the appropriate backend service.
3. The Customer Service or E-Bank Service processes the request.
4. The selected service returns the response through the gateway.
5. The Discovery Service maintains information about registered services so that service instances can be located when discovery is configured.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | Building independent microservices |
| Spring Web | Creating RESTful APIs |
| Spring Cloud Gateway | API routing and request forwarding |
| Spring Cloud Netflix Eureka | Service registration and discovery |
| Swagger / OpenAPI | API documentation and testing |
| Maven | Dependency management and project build |
| H2 Database | Lightweight database, where configured |

## 1. Customer Service

The Customer Service manages customer information and provides REST endpoints for accessing and creating customer records.

### Main Components

- `CustomerServiceApplication`: Main entry point of the Customer Service.
- `Customer`: Entity representing a customer.
- `CustomerService`: Service layer responsible for customer-related business logic.
- `CustomerRestController`: REST controller exposing customer endpoints.

### Project Structure

<img width="2315" height="1265" alt="Customer Service project structure" src="https://github.com/user-attachments/assets/5ebe98ad-adf3-4b75-806c-2a984695a7c4" />

### CustomerServiceApplication

<img width="1752" height="1382" alt="CustomerServiceApplication" src="https://github.com/user-attachments/assets/e0592da1-95d2-494a-b600-bbeeca78dde4" />

### CustomerService

<img width="2075" height="1695" alt="CustomerService implementation" src="https://github.com/user-attachments/assets/ee367296-403d-4921-8609-b38346d7eac1" />

### Customer Entity

<img width="1690" height="1397" alt="Customer entity" src="https://github.com/user-attachments/assets/212efd22-5af3-4ba8-adaf-015db8393bdc" />

### CustomerRestController

<img width="3222" height="1240" alt="Customer REST controller" src="https://github.com/user-attachments/assets/9efece32-33c2-44cc-9790-94f9707ca1c3" />

### Testing the Customer API

Retrieve customers directly from the Customer Service:

```http
GET http://localhost:8056/customers
```

<img width="1287" height="1135" alt="Customer API response" src="https://github.com/user-attachments/assets/1d4b00e9-27ea-4437-81dd-0ede03eab25c" />

### Swagger UI

Interactive API documentation:

[http://localhost:8056/swagger-ui/index.html](http://localhost:8056/swagger-ui/index.html)

**GET — Retrieve customers**

<img width="3460" height="1762" alt="Swagger GET customers" src="https://github.com/user-attachments/assets/2ab3d288-a382-48f9-a7cc-a2ff7127cded" />

**POST — Create a customer**

<img width="3470" height="1887" alt="Swagger POST customers" src="https://github.com/user-attachments/assets/3c0d8a6f-76a2-4d6e-b984-ad2370fc7f36" />

**Additional customer endpoint test**

<img width="1835" height="1845" alt="Customer endpoint test" src="https://github.com/user-attachments/assets/9b89f424-5f90-48e3-bea0-4210a0370d68" />

## 2. E-Bank Service

The E-Bank Service manages bank account information and exposes REST endpoints for retrieving accounts.

### Main Responsibilities

- Represent bank accounts using dedicated domain entities.
- Implement account-related business logic.
- Expose account data through REST APIs.
- Separate account management from customer management.

### Project Structure

<img width="3815" height="1557" alt="E-Bank Service project structure" src="https://github.com/user-attachments/assets/e9e9e7ae-46eb-4b44-b73b-4598b8146d2b" />

### EBankServiceApplication

<img width="3830" height="1727" alt="EBankServiceApplication" src="https://github.com/user-attachments/assets/52102ab3-8a65-4094-a5bb-86e0ab76b4e1" />

### Testing the Account API

Retrieve bank accounts directly from the E-Bank Service:

```http
GET http://localhost:8057/accounts
```

<img width="1872" height="2017" alt="Bank accounts API response" src="https://github.com/user-attachments/assets/28270e2e-5004-4dd5-b044-9533764a2fd3" />

## 3. API Gateway

The API Gateway is the single entry point for accessing the application's backend services.

Instead of requiring clients to communicate directly with each microservice, the gateway forwards requests according to its routing configuration.

### Why Use an API Gateway?

- **Single entry point:** Clients only need to know the gateway's address.
- **Routing:** Requests are forwarded to the appropriate backend service.
- **Centralized security:** Authentication and token validation can be integrated at the gateway.
- **Monitoring and logging:** Incoming requests can be tracked centrally.
- **Load balancing:** Requests can be distributed across service instances when properly configured.

These capabilities depend on the actual gateway configuration. Routing is the core functionality demonstrated here.

### Gateway Implementation

<img width="3125" height="1077" alt="API Gateway implementation" src="https://github.com/user-attachments/assets/993e2a05-e88e-43d6-930c-169891a721de" />

### Application Properties

<img width="1035" height="330" alt="API Gateway application properties" src="https://github.com/user-attachments/assets/91efdfce-0e87-4007-b3cf-523b955abdcc" />

### Testing Through the Gateway

The following request demonstrates access to the account endpoint through the gateway:

```http
GET http://localhost:9999/accounts
```

<img width="1575" height="1870" alt="Accounts endpoint accessed through API Gateway" src="https://github.com/user-attachments/assets/1d7be0bb-e170-4742-81b3-89c942f61480" />

This allows clients to access the account service through the gateway instead of calling port `8057` directly.

## 4. Discovery Service

The Discovery Service introduces service registration and discovery into the application using **Spring Cloud Netflix Eureka**.

Rather than relying exclusively on fixed service addresses, backend services can register themselves with Eureka. Other services and the API Gateway can then discover registered instances when configured to use service discovery.

### Main Responsibilities

- Maintain a registry of available microservices.
- Allow services to register themselves.
- Provide a dashboard for viewing registered services.
- Support service discovery for components configured to use Eureka.

### Discovery Service Project Structure

<img width="1890" height="750" alt="Discovery Service project structure" src="https://github.com/user-attachments/assets/2cef7924-fc09-47ad-924e-91223e8c049d" />

### Discovery Service Implementation

<img width="3835" height="1940" alt="Discovery Service implementation" src="https://github.com/user-attachments/assets/216003e3-fc32-4779-a442-50725abbbb97" />

### Application Properties

<img width="1965" height="605" alt="Discovery Service application properties" src="https://github.com/user-attachments/assets/59a3e21b-2586-48fd-a32b-3dc143385e02" />

### Eureka Dashboard

Once the Discovery Service is running, open the Eureka dashboard:

[http://localhost:8761/](http://localhost:8761/)

The dashboard allows you to inspect registered application instances and their availability.

**Important:** To see the Customer Service, E-Bank Service, and API Gateway in the registry, configure them as Eureka clients and ensure that they use the correct Eureka server URL.

## API Endpoints

The following endpoints are available for direct testing based on the current local configuration.

| Service | Method | Endpoint | Description |
|---|---|---|---|
| Customer Service | GET | `http://localhost:8056/customers` | Retrieve customers |
| Customer Service | POST | `http://localhost:8056/customers` | Create a customer |
| E-Bank Service | GET | `http://localhost:8057/accounts` | Retrieve bank accounts |
| API Gateway | GET | `http://localhost:9999/accounts` | Retrieve accounts through the gateway |
| Discovery Service | GET | `http://localhost:8761/` | Open the Eureka dashboard |

## Running the Application

### Prerequisites

- Java Development Kit (JDK)
- Maven or the Maven Wrapper
- An IDE such as IntelliJ IDEA or Visual Studio Code

### Startup Order

For local development, start the services in the following order:

1. **Discovery Service** — start Eureka on port `8761`.
2. **Customer Service** — start the customer API on port `8056`.
3. **E-Bank Service** — start the account API on port `8057`.
4. **API Gateway** — start the gateway on port `9999`.

Starting Eureka first allows the other services to register with it, provided that their Eureka client configurations are enabled and correct.

### Run Each Service

Open a terminal in the directory of each Spring Boot service and run:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, run the main application class from your IDE.

Verify the configured ports and database settings in each service's `application.properties` or `application.yml` file before starting the application.

## Key Learning Objectives

Through this project, the following concepts are explored:

- Designing a backend using microservices architecture.
- Separating business responsibilities into independent services.
- Developing REST APIs with Spring Boot.
- Testing APIs with Swagger UI.
- Configuring centralized routing with Spring Cloud Gateway.
- Registering and discovering services using Eureka.
- Understanding how clients access backend services through a gateway.
- Preparing the application for future improvements such as centralized security, containerization, and automated deployment.

## Future Improvements

Possible next steps include:

- Integrating Spring Security and JWT-based authentication.
- Implementing customer-account communication using OpenFeign or REST clients.
- Adding centralized configuration with Spring Cloud Config.
- Containerizing the services with Docker and Docker Compose.
- Adding automated unit and integration tests.
- Integrating centralized logging and monitoring.
- Configuring load balancing across multiple instances.

---

**Author:** [Chaimae Mouhssine](https://github.com/CHAIMAEMOUHSSINE)

**Repository:** [ebanking-Microservice-application](https://github.com/CHAIMAEMOUHSSINE/ebanking-Microservice-application)
