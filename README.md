# Money Conversion App

## Project Description

The **Money Conversion App** is a RESTful API built with **Spring Boot** that allows users to convert amounts between different currencies using predefined exchange rates.
The app provides CRUD operations for managing currency exchange rates and supports operations like viewing, adding, updating, and deleting exchange rates.
The app consists of 2 services which communicate with each other.
myBank service communicates with myExchange service to convert the currency of a transaction if needed. 

### Tech Stack
- **Java 22**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## How to Install and Run the Project

### Prerequisites
- Java 17 or higher
- PostgreSQL
- Maven

### Installation Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/MilanM2001/money-conversion-app.git
    ```

2. Navigate to the project directory:

    ```bash
    cd money-conversion-app
    ```

3. Update the PostgreSQL database configuration in `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/moneyconversion
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    ```

4. Run the application using Maven:

    ```bash
    ./mvnw spring-boot:run
    ```

5. The API will be available at `http://localhost:8080`.
