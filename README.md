# 🛒 Product Inventory System 

## 🧩 Description
A secure **Spring Boot REST API** for managing products.  
Users have their **personal inventory**, and all CRUD operations are protected using **JWT authentication**. Passwords are securely hashed with **BCrypt**.

Each product stored includes:  
`id`, `userId` (owner), `name`, `price`, `quantity`.

---

## ⚙️ Features
- **POST /auth/register** → Register a new user (**200 OK**)
- **POST /auth/login** → Login and receive JWT token (**200 OK**)

- **GET /products** → List all products (**200 OK**)
- **GET /products/my** → List only the authenticated user’s products (**200 OK**)
- **POST /products** → Create a new product (**authenticated users only**) (**200 OK**)
- **PUT /products/{id}** → Update a product (only if owned by user) (**200 OK**)
- **DELETE /products/{id}** → Delete a product (only if owned by user) (**200 OK**)

---

## 📝 Rules
- Users **cannot edit or delete** someone else’s products.
- All requests include **validation** for required fields and constraints.

---

## 💡 Concepts Used
- **Spring Boot** for building RESTful backend services
- **JWT (JSON Web Tokens)** for authentication & authorization
- **BCrypt** for password hashing
- **Spring Data JPA** for ORM and database persistence
- **DTO Mapping** → JSON → DTO → Entity
- **Custom Exceptions** (`RuntimeException` for simplicity)
- **Global Exception Handling** for clean API error responses
- **Security Layer** → `JwtAuthenticationFilter` validates JWT and sets Spring Security context
- **Clean Layered Architecture**  
  `Controller → Service → Repository → Entity → DTO → Security → Exception`

---

## 🔁 User & Product Flow
1. **User Registration**
    - POST `/auth/register` with name, email, and password
    - Password is hashed and user saved
    ```json
   {
    "username": "JohnDoe",
    "email": "john@example.com",
    "password": "secret123"
   }

    ```
   **Response**
    ```json
    {
        "message": "User registered successfully"
    }
    ```

2. **User Login**
    - POST `/auth/login` with email and password
    ```json
    {
    "username": "JohnDoe",
    "email": "john@example.com",
    "password": "secret123"
}
    ```
   **Response**
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR..."
    }
    ```

3. **Access Product Routes**
    - Send JWT in the `Authorization` header:
      ```
      Authorization: Bearer <your_jwt_token>
      ```
    - Only authenticated users can access `/products` routes
    - Users can **create, view, edit, and delete** only their own products

4. **Create Product**
    - POST `/products` with JSON body:
    ```json
    {
        "name": "Laptop",
        "price": 1200.50,
        "quantity": 3
    }
    ```
   **Response**
    ```json
   {
    "id": 1,
    "name": "Laptop",
    "price": 1200.5,
    "quantity": 3,
    "userId": 1
}
    ```

---

## 🧠 Learning Focus
- JWT-based **stateless authentication**
- User-based **resource ownership and authorization**
- Password security with **BCrypt**
- RESTful CRUD operations for a protected resource
- Mapping between **DTOs and entities**
- Exception handling for clean API responses

---

## 🧑‍💻 Developed By
**Name:** Sajid Hussain  
**Project:** Product Inventory System   
**Language:** ☕ Java  
**Date:** 27 November 2025
