# 🎫 Ticketing System Demo

A full-stack ticket management application built to demonstrate secure authentication, role-based access control, and modern backend architecture using **Java 21** and **Spring Boot**.

This project showcases a complete issue tracking workflow, including ticket lifecycle management, user roles, and JWT-based authentication.

> ⚠️ This project is intended for demonstration and portfolio purposes only.

---

## 🚀 Features

- Create, update, and delete tickets
- Comment system per ticket
- Ticket status management (Open, In Progress, Closed)
- Role-based access control (Admin / User)
- JWT Authentication with Spring Security
- Responsive and modern frontend design
- RESTful API architecture

---

## 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Gradle 8.5

### Frontend

- Angular (served on port 4200)
- Responsive UI design

---

## 👥 Roles & Permissions

### Admin

- View system settings
- Manage users
- Create projects and tickets

### User

- Create projects
- Create and manage tickets

---

## 🔐 Authentication

JWT-based authentication via Spring Security.

### Example Login Request

```bash
curl -X POST http://localhost:8080/api/login \
-d "username=admin&password=admin" -v
```

### Access Protected Endpoint

```bash
curl -X GET http://localhost:8080/api/users \
-H "Authorization: Bearer <TOKEN>" -v
```

---

## 🧪 Installation & Running the Project

### 1️⃣ Install Frontend Dependencies

```bash
npm install
```

### 2️⃣ Start Frontend

```bash
npm start
```

Frontend runs at:

```
http://localhost:4200/
```

### 3️⃣ Start Backend

```bash
./gradlew bootRun
```

Backend runs at:

```
http://localhost:8080/
```

---

## 🔑 Demo Credentials

Admin:

```
username: admin
password: admin
```

User:

```
username: User 1
password: TestPassword1234
```

---

## 🧪 Run Tests

```bash
./gradlew test
```

---

## 📂 Project Structure

- `backend/` – Spring Boot REST API
- `frontend/` – Angular application
- JWT-based authentication layer
- Role-based authorization

---

## 👨‍💻 Team

Backend:
Riccardo Landolfo

Frontend:
Dobrawa Kiefer

---

# 🎯 What This Project Demonstrates

- Secure REST API design
- Stateless authentication with JWT
- Role-based authorization
- Clean separation between frontend and backend
- Modern Java backend architecture
- Responsive UI integration
