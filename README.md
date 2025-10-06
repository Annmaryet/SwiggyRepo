# 🍔 Swiggy Clone - Full-Stack Food Delivery Application

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![React](https://img.shields.io/badge/React-18+-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)

A comprehensive full-stack web application that clones the core functionalities of Swiggy, a popular food delivery service. This project demonstrates a complete development cycle, from a robust RESTful backend built with Spring Boot to a dynamic and responsive frontend powered by ReactJS.

## 📸 Screenshots

*(**Note to you, the developer:** Create a folder named `screenshots` in your project's root directory and add your actual application screenshots there. Name them as suggested below.)*

| Login Page | Home Page (Restaurants) | Menu Page |
| :---: | :---: | :---: |
| ![Login Page](screenshots/login-page.png) | ![Home Page](screenshots/home-page.png) | ![Menu Page](screenshots/menu-page.png) |
| **Cart** | **Order History** | **Admin Dashboard** |
| ![Cart](screenshots/cart-page.png) | ![Order History](screenshots/orders-page.png) | ![Admin Dashboard](screenshots/admin-page.png) |

## ✨ Features

This project implements the end-to-end workflow of a food delivery application.

### Core Features
* **Secure User Authentication**: JWT-based registration and login system with password hashing (BCrypt).
* **Role-Based Access Control**: Differentiates between regular users (`ROLE_USER`) and administrators (`ROLE_ADMIN`).
* **Full CRUD Functionality**: Users and Admins can Create, Read, Update, and Delete various entities like Restaurants, Food Items, etc.
* **Real-time Notifications**: Users receive notifications for key events like order confirmation and status updates.

### Backend Features (Spring Boot)
* **RESTful API**: A well-structured API for all frontend interactions.
* **Spring Security**: Manages authentication and secures endpoints.
* **JPA & Hibernate**: Handles all database operations with a relational MySQL database.
* **Centralized Exception Handling**: Provides consistent and meaningful error responses.
* **Clean Monorepo Structure**: Backend and Frontend code are neatly organized in the same repository.
* **Swagger API Documentation**: Integrated Swagger UI for easy API testing and documentation.

### Frontend Features (ReactJS)
* **Single-Page Application (SPA)**: A fast and seamless user experience built with React.
* **Component-Based Architecture**: Reusable and maintainable UI components.
* **State Management**: Utilizes React Context API for managing global state like user authentication and the shopping cart.
* **API Integration**: Uses Axios for all communication with the backend REST API.
* **Routing**: `react-router-dom` for navigating between different pages.
* **Responsive Design**: A user-friendly interface that works on various screen sizes.

---
## 🛠️ Technology Stack

| Category | Technology |
| :--- | :--- |
| **Backend** | Java 17+, Spring Boot, Spring Security, Spring Data JPA, Hibernate, MySQL, Lombok, JWT |
| **Frontend**| ReactJS, Vite, JavaScript (ES6+), Axios, React Router, CSS |
| **Database** | MySQL |
| **Build Tools** | Maven (Backend), npm/Vite (Frontend) |

---
## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites
* Java JDK 17 or newer
* Apache Maven
* Node.js and npm
* MySQL Server

### Backend Setup
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Ranjith0731/SwiggyClone.git](https://github.com/Ranjith0731/SwiggyClone.git)
    cd SwiggyClone
    ```
2.  **Configure the database:**
    * Open `backend/src/main/resources/application.properties`.
      spring.datasource.url=jdbc:mysql://localhost:3306/SwiggyClone
      spring.datasource.username=root
      spring.datasource.password=12345
3.  **Run the backend server:**
    ```bash
    cd backend
    mvn spring-boot: run
    ```
    The backend will start on `http://localhost:8082`.

### Frontend Setup
1.  **Navigate to the frontend directory:**
    ```bash
    cd frontend
    ```
2.  **Install dependencies:**
    ```bash
    npm install
    ```
3.  **Start the frontend development server:**
    ```bash
    npm run dev
    ```
    The frontend application will be available at `http://localhost:5173`.

---
## 📋 API Endpoints

A summary of the available API endpoints. For detailed information and testing, visit the [Swagger UI](http://localhost:8082/swagger-ui.html) once the backend is running.

<details>
<summary><strong>Click to expand API Endpoint List</strong></summary>

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user. |
| `POST` | `/api/auth/login` | Authenticate a user and get a JWT. |
| `GET` | `/api/users` | Get a list of all users (Admin only). |
| `GET` | `/api/users/{id}` | Get a user by their ID. |
| `POST`| `/api/admin/restaurants`| Add a new restaurant (Admin only). |
| `GET` | `/api/restaurants` | Get a list of all restaurants. |
| `POST`| `/api/cart/add` | Add an item to the user's cart. |
| `GET` | `/api/cart/{userId}` | Get the user's current cart. |
| `POST`| `/api/orders/user/{userId}`| Create an order from the user's cart. |
| `GET` | `/api/orders/user/{userId}`| Get the order history for a user. |
| `POST`| `/api/payments/process` | Process the payment for an order. |

</details>

---

## 👨‍💻 Author

**D G Ranjith Kumar**
* GitHub: [@Ranjith0731](https://github.com/Ranjith0731)
* LinkedIn: https://www.linkedin.com/in/ranjithkumar36
