# 🍔 Swiggy Clone - Backend API

## 🚀 Live Demo

* **Live API Base URL**: `https://swiggyclone-backend-production-1234.up.railway.app`
* **API Documentation (Swagger UI)**: `https://swiggyclone-backend-production-1234.up.railway.app/swagger-ui.html`


![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

This repository contains the source code for a robust and scalable backend service that emulates the core business logic of Swiggy, a popular food delivery platform. The project is built with Java and the Spring Boot framework, focusing on clean code, RESTful API design, and security.

## 🏛️ Architectural Overview

This project is built upon a classic **Layered Architecture** to ensure a clean separation of concerns, maintainability, and scalability.

* **Controller Layer**: Exposes the RESTful API endpoints and handles incoming HTTP requests. It is responsible for request validation and delegating business logic to the service layer.
* **Service Layer**: Contains the core business logic of the application. It orchestrates operations between different models and handles transactions.
* **Repository Layer (Data Access)**: Manages all database interactions using Spring Data JPA. It abstracts the underlying data source and provides a clean interface for data manipulation.
* **Security**: Handled by Spring Security, with JWTs used for stateless, secure authentication and authorization on protected endpoints.

## ✨ Key Features

* **JWT-Based Authentication & Authorization**: Secure, stateless authentication using JSON Web Tokens.
* **Role-Based Access Control (RBAC)**: Differentiates permissions between standard users (`ROLE_USER`) and administrators (`ROLE_ADMIN`).
* **Comprehensive CRUD Operations**: Full Create, Read, Update, and Delete functionality for all major entities including Users, Restaurants, and Food Items.
* **Complete Order Lifecycle Management**:
    * Shopping Cart management (add, remove, view items).
    * Seamless order creation from the user's cart.
    * Payment processing simulation.
* **Event-Driven Notifications**: A system to generate notifications for key events like order confirmation and status changes.
* **Centralized Exception Handling**: Provides consistent, meaningful error responses across the API.
* **API Documentation with Swagger/OpenAPI**: Integrated Swagger UI for interactive API documentation and easy testing.

## 🛠️ Technology Stack

| Category | Technology |
| :--- | :--- |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security 6.x, JSON Web Tokens (JWT) |
| **Database** | Spring Data JPA, Hibernate, MySQL 8.x |
| **API & Documentation** | Spring Web (REST Controllers), SpringDoc OpenAPI (Swagger UI) |
| **Build Tool** | Apache Maven |
| **Utilities** | Lombok, Spring Boot DevTools |

---
## 🚀 Environment Setup & Installation

Follow these instructions to get the backend service running on your local machine for development and testing.

### Prerequisites
* **Java JDK 17** or newer
* **Apache Maven** 3.8+
* **MySQL Server** 8.0+

### 1. Clone the Repository
```bash
git clone [https://github.com/Ranjith0731/SwiggyClone.git](https://github.com/Ranjith0731/SwiggyClone.git)
cd SwiggyClone/backend
```

### 2. Database Configuration
1.  Access your MySQL instance and create a new database schema.
    ```sql
    CREATE DATABASE swiggyclone;
    ```
2.  Navigate to the configuration file at `src/main/resources/application.properties`.
3.  Update the datasource properties with your local MySQL username and password.

    ```properties
    # --------------------------------------------------
    # DATABASE CONFIGURATION
    # --------------------------------------------------
    spring.datasource.url=jdbc:mysql://localhost:3306/swiggyclone
    spring.datasource.username=root
    spring.datasource.password=12345
    
    # Hibernate Properties
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

### 3. Build and Run the Application
You can run the application using Maven or directly from your IDE.

* **Using Maven:**
    ```bash
    mvn spring-boot:run
    ```
* **Using an IDE (IntelliJ, Eclipse):**
    * Import the project as a Maven project.
    * Locate the main application class `SwiggyCloneApplication.java` and run it.

The application will start on `http://localhost:8082`.

---
## 📄 API Documentation & Usage

This project uses **SpringDoc OpenAPI** to generate live API documentation. Once the application is running, you can access the interactive Swagger UI to view all endpoints, see their models, and execute test requests directly from your browser.

* **Swagger UI URL**: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

<details>
<summary><strong>Click to expand API Endpoint Summary</strong></summary>

| HTTP Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user. | Public |
| `POST` | `/api/auth/login` | Authenticate a user and get a JWT. | Public |
| `GET` | `/api/users` | Get a list of all users. | Admin only |
| `GET` | `/api/users/{id}` | Get a user by their ID. | Admin/Owner |
| `POST` | `/api/admin/restaurants` | Add a new restaurant. | Admin only |
| `GET` | `/api/restaurants` | Get a list of all restaurants. | Public |
| `POST` | `/api/cart/add` | Add an item to the user's cart. | User |
| `GET` | `/api/cart/{userId}` | Get the user's current cart. | User |
| `POST` | `/api/orders/user/{userId}` | Create an order from the user's cart. | User |
| `GET` | `/api/orders/user/{userId}` | Get the order history for a user. | User |
| `POST` | `/api/payments/process` | Process the payment for an order. | User |

</details>

---

## 📁 Project Structure

The project follows the standard Maven directory structure, with a logical package structure to separate concerns.

```
src/main/java/com/example/SwiggyClone
├── controller/   # REST API controllers
├── dto/          # Data Transfer Objects
├── entity/       # JPA entities (database models)
├── exception/    # Custom exception classes
├── repository/   # Spring Data JPA repositories
├── service/
│   ├── impl/     # Service layer implementations
│   └── inter/    # Service layer interfaces
├── config/       # Spring configuration (e..g., Security)
├── util/         # Utility classes (e.g., JwtUtil)
└── SwiggyCloneApplication.java
```

---
## 👨‍💻 Author

**D G Ranjith Kumar**
* GitHub: [@Ranjith0731](https://github.com/Ranjith0731)
* LinkedIn: [https://www.linkedin.com/in/ranjithkumar36](https://www.linkedin.com/in/ranjithkumar36)
