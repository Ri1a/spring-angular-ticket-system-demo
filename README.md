# webeC - Graded Exercise

## Project

Java 21
Gradle 8.5

### Team members

Riccardo Landolfo (riccardo.landolfo@students.fhnw.ch)
Dobrawa Kiefer (dobrawa.kiefer@students.fhnw.ch)

### Description

This project outlines the development of a ticketing application, designed to streamline issue tracking and resolution processes. The app allows users to create, update, and delete tickets, each representing a specific issue or task. A key feature of the app is the ability to add comments to tickets. Each ticket can be assigned different statuses, reflecting its current stage in the resolution process, such as "Open," "In Progress," or "Closed." The application is built with a sophisticated and responsive design, ensuring a seamless user experience across various devices.

### Individual

- Sophisticated and Responsive Design
- Authentication & Authorization with JWT and Spring Security

## Installation and run instructions

Install the npm packages with `npm install`

Start the frontend with `npm start`

Start the Spring backend

Navigate to `http://localhost:4200/` and log in with `admin:admin`.

Admin login:
```
curl -X POST http://localhost:8080/api/login \
-d "username=admin&password=admin" -v
```

List users:
```
curl -X GET http://localhost:8080/api/users -H "Authorization: Bearer <TOKEN>" -v
```

### Run application

```
./gradlew bootRun
```

### Run tests

```
./gradlew test
```
