# Insurance Premium Calculation Service

## About the Project
The service calculates insurance premiums based on annual mileage, vehicle type, and registration region. 
It uses a CSV file for regional classification and applies specific factors for mileage, vehicle type, and region to determine the premium. 
Additionally, it provides an HTTP API for third-party integrations to request premium calculations.

This service calculates an insurance premium based on:

- Annual mileage
- Vehicle type
- Registration region

A CSV file (postcodes.csv) is used for regional classification, containing key fields such as state, country, city, postal code, and district.

Users can enter their estimated mileage, vehicle type, and postal code to calculate the premium. The calculated premium and user inputs are stored persistently.

Formula for premium calculation:

#### **Mileage Factor** × **Vehicle Type Factor** × **Region Factor**


## Features
- Web application for applicants to calculate premiums and store their inputs.
- HTTP API for third-party integration to request premium calculations.

## Microservices Architecture with BFF (Backend for Frontend) Pattern
This system is built using a **mono-repo microservices architecture**, where all services are maintained in a single repository for consistency, simplified dependency management, and easier collaboration. Each microservice is designed for **flexibility, scalability, and independent deployment** while ensuring efficient internal communication.  
The BFF pattern is designed to streamline frontend development and ensure easier future integrations with web applications by providing a simplified, optimized backend interface.

In this architecture:

- **Mono-Repo Microservices**: Each service is modular but part of the same repository, improving maintainability and development efficiency.
  
- Microservices focus on specific tasks, such as data processing, premium calculation, and communication with external systems.

- **BFF (Backend for Frontend)**: The **Public API Microservice** acts as the only entry point for frontend applications, optimizing communication and reducing complexity, making it easier for developers to interact with the backend without needing to be aware of the underlying complexities.

## Microservices in this System

### **Public Api Microservice**: Manages the public API
- Exposes a RESTful API to allow third-party systems or users to interact with the service.
- Handles incoming requests for premium calculations from external users or systems.
- Validates incoming requests and forwards them to the Premium Microservice for premium calculation.

### **Premium Microservice**: Calculates and processes insurance premiums
- Integrates with the Data Microservice to retrieve necessary external data for premium calculations.
- Calculates insurance premiums based on mileage, vehicle type, and region.
- Handles the core business logic for calculating insurance premiums based on various factors (e.g., mileage, vehicle type, region).

### **Data Microservice**: Manages external data sources
- Retrieves and processes data to compute vehicle, and region factors used in the premium calculation.
- Manages external data sources and resources, ensuring consistency and integrity.
- Provides processed data and calculated factors to other services (e.g., Premium Microservice) for final premium calculation.

## Attention ⚠️
### Internal Communication
Other microservices(Premium and Data) also expose RESTful APIs; however, these APIs are designed for internal communication within the system. 
To ensure security and proper access control, authentication needs to be added for these APIs in future implementations.



## Technologies and Libraries Used

- **Java 21**
- **Maven**
- **Spring Boot**
- **Lombok**
- **Spring Data JPA**
- **Spring Cloud Open Feign**
- **PostgreSQL**
- **JUnit5 & Mockito**
- **Docker & Docker Compose**
- **Postman**


## Installation
### Prerequisites

- **JDK 21**
- **Docker Desktop**

### Steps
1. Clone the repository:

```bash
git clone https://github.com/neslisezen/insurance-premium-calculation-system.git
```

2. Build the project:

```bash
mvn clean install
```

3. Run the docker-compose file:

```bash
docker-compose up --build
```




## Configuration
## Attention ⚠️
If you don't use docker compose file,
please add your environment variables or edit the `application.properties` file to set up the database and other details:

For Public API Microservice:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name_for_public_api
spring.datasource.username=your_username
spring.datasource.password=your_password
```
For Data Microservice:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name_for_data_service
spring.datasource.username=your_username
spring.datasource.password=your_password
```
---
### API Testing with Postman

For convenience, Postman collections have been created for testing the APIs. You can find the collection in the
following path:

```plaintext
postman-collection/ipcs - Insurance Premium Calculation Service.postman_collection.json
```
---
## Example Requests
### Calculate Insurance Premium
```http
POST http://localhost:8080/api/v1/public
Content-Type: application/json
X-Source: internalApp

{
    "vehicle": "BUS",
    "mileage": "10000",
    "postcode": "24539"
}
```
Returns:
```http
{
    "data": {
        "id": 1,
        "insurancePremium": 12.0
    },
    "message": "Calculation successful",
    "success": true
}
```

### List All User Inputs
```http
GET http://localhost:8080/api/v1/public/all
```
Returns:
```http
[
    {
        "id": 1,
        "vehicle": "pkw",
        "mileage": 10000,
        "postcode": "56075",
        "insurancePremium": 6.5,
        "source": "internalApp",
        "date": "2025-02-14T17:00:37.536489"
    },
    {
        "id": 2,
        "vehicle": "SUV",
        "mileage": 1200,
        "postcode": "53227",
        "insurancePremium": 3.90625,
        "source": "externalApp",
        "date": "2025-02-14T17:03:18.722667"
    }
]
```
---

## Running Tests

You can run tests using the following command:

```bash
mvn test
```

---

## Contact


- **Email:** neslihansezen9@gmail.com
- **GitHub:** [neslisezen](https://github.com/neslisezen)
- **LinkedIn:** [Neslihan Sezen](https://linkedin.com/in/neslihansezen)
