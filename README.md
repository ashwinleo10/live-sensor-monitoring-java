# Live Sensor Monitoring Dashboard

A full-stack real-time sensor monitoring system built as a backend technical assessment.

The system receives sensor readings from a Java-based simulator, validates and stores the data in MySQL, monitors sensor connectivity, generates alerts for abnormal conditions, and displays live sensor information through a React dashboard.

---

## Features

- User registration and login
- Secure password hashing using BCrypt
- JWT-based authentication
- JWT-protected dashboard APIs
- Logout with JWT token blacklisting
- Sensor reading ingestion API
- Reading validation
- Duplicate reading prevention
- Automatic server-side timestamps
- Latest reading retrieval
- Sensor history with filtering
- Sensor ONLINE/OFFLINE detection
- High Temperature alerts
- High Humidity alerts
- Sensor Disconnected alerts
- Prevention of repeated alerts for the same ongoing condition
- Java sensor simulator
- Temporary sensor disconnection simulation
- React monitoring dashboard
- Automatic dashboard polling every 5 seconds
- Persistent data storage using MySQL

---

## System Architecture

```text
                    ┌──────────────────────┐
                    │   Java Sensor        │
                    │     Simulator        │
                    │                      │
                    │ S1        S2          │
                    └──────────┬───────────┘
                               │
                               │ POST /readings
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot        │
                    │      Backend         │
                    │                      │
                    │ REST APIs             │
                    │ Validation            │
                    │ JWT Authentication    │
                    │ Alert Processing      │
                    │ Sensor Monitoring     │
                    └──────────┬───────────┘
                               │
                               │ JPA / Hibernate
                               ▼
                    ┌──────────────────────┐
                    │        MySQL         │
                    │  sensor_monitoring    │
                    │                      │
                    │ users                 │
                    │ sensor_readings       │
                    │ alerts                │
                    └──────────┬───────────┘
                               │
                               │ Authenticated REST APIs
                               ▼
                    ┌──────────────────────┐
                    │     React Dashboard  │
                    │                      │
                    │ Sensor status         │
                    │ Temperature           │
                    │ Humidity              │
                    │ Alerts                │
                    │ Login / Logout        │
                    └──────────────────────┘
```

The dashboard uses **API polling** for automatic updates. It requests the latest sensor information every 5 seconds.

---

## Technology Stack

### Backend

- Java
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- BCrypt
- MySQL
- Maven

### Sensor Simulator

- Java
- Java HTTP Client
- Scheduled Executor
- JSON requests

### Frontend

- React
- JavaScript
- Vite
- HTML
- CSS
- Fetch API

### Development Tools

- Eclipse
- VS Code
- MySQL
- Postman
- Git
- GitHub

---



---

# Prerequisites

Install the following before running the project:

- Java 17 or later
- Maven
- MySQL 8.x
- Node.js
- npm
- Git

The project was developed and tested with Java 24.0.1, MySQL 8.0.45 and Node.js 22.x.

---

# Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE sensor_monitoring;
```

The application uses Spring Data JPA/Hibernate to create and update the required tables automatically.

The main tables are:

```text
users
sensor_readings
alerts
```

---

# Backend Setup

Navigate to the backend:

```bash
cd live-sensor-monitoring-backend
```

Configure the required environment variables:

```text
DB_PASSWORD
JWT_SECRET
```

These values should be configured in the local development environment and must not be committed to GitHub.

The backend configuration expects:

```properties
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

Start the backend:

```bash
mvn spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

# Authentication

The application provides:

```text
POST /auth/register
POST /auth/login
POST /auth/logout
```

Passwords are never stored as plain text.

During registration, passwords are hashed using BCrypt before being stored in MySQL.

After successful login, the backend generates a JWT.

Protected APIs require:

```text
Authorization: Bearer <JWT>
```

Logout sends the JWT to the backend, where it is added to a token blacklist.

---

# Sensor Simulator

The Java simulator generates readings for:

```text
S1
S2
```

A reading contains:

```json
{
    "reading_id": "unique-reading-id",
    "sensor_id": "S1",
    "temperature": 32.5,
    "humidity": 64.2
}
```

### Reading ranges

Temperature:

```text
20°C - 40°C
```

Humidity:

```text
30% - 80%
```

A new reading is generated every 2 seconds.

Start the simulator from:

```text
sensor-simulator
```

using:

```bash
mvn compile
```

and run:

```text
SensorSimulatorApplication
```

The simulator sends readings to:

```text
http://localhost:8080/readings
```

### Temporary disconnection

The simulator intentionally stops sending readings for S2 for a short period to demonstrate the sensor disconnection functionality.

If the backend becomes unavailable, the simulator catches the communication error and continues running instead of crashing.

---

# API Documentation

## 1. Register

### Request

```http
POST /auth/register
```

Example:

```json
{
    "email": "user@example.com",
    "password": "Password@123"
}
```

### Response

```json
{
    "token": null,
    "message": "Registration successful"
}
```

---

# 2. Login

### Request

```http
POST /auth/login
```

Example:

```json
{
    "email": "user@example.com",
    "password": "Password@123"
}
```

### Response

```json
{
    "token": "<JWT_TOKEN>",
    "message": "Login successful"
}
```

The JWT is then used to access protected APIs.

---

# 3. Logout

### Request

```http
POST /auth/logout
```

Required header:

```text
Authorization: Bearer <JWT_TOKEN>
```

### Response

```json
{
    "token": null,
    "message": "Logout successful"
}
```

The token is blacklisted by the backend.

---

# 4. Create Sensor Reading

### Request

```http
POST /readings
```

Example:

```json
{
    "reading_id": "READING-001",
    "sensor_id": "S1",
    "temperature": 32.5,
    "humidity": 65.0
}
```

### Response

```json
{
    "readingId": "READING-001",
    "sensorId": "S1",
    "temperature": 32.5,
    "humidity": 65.0,
    "timestamp": "2026-11-25T14:43:49.493885"
}
```

The backend assigns the timestamp.

The `reading_id` must be unique.

---

# 5. Latest Readings

### Request

```http
GET /readings/latest
```

Authentication required.

Returns the latest reading for each sensor.

---

# 6. Reading History

### Request

```http
GET /readings/history
```

Authentication required.

### All readings

```text
GET /readings/history
```

### Filter by sensor

```text
GET /readings/history?sensorId=S1
```

### Filter by date range

```text
GET /readings/history?startDate=2026-11-25T00:00:00&endDate=2026-11-25T23:59:59
```

### Filter by sensor and date range

```text
GET /readings/history?sensorId=S1&startDate=2026-11-25T00:00:00&endDate=2026-11-25T23:59:59
```

Date format:

```text
yyyy-MM-ddTHH:mm:ss
```

---

# 7. Sensors

### Request

```http
GET /sensors
```

Authentication required.

Returns the status of S1 and S2.

Example:

```json
[
    {
        "sensorId": "S1",
        "status": "ONLINE",
        "lastReadingTime": "2026-11-25T14:43:49.493885"
    },
    {
        "sensorId": "S2",
        "status": "ONLINE",
        "lastReadingTime": "2026-11-25T14:43:49.506240"
    }
]
```

---

# 8. Alerts

### Request

```http
GET /alerts
```

Authentication required.

Returns generated alerts ordered by timestamp.

Example:

```json
{
    "sensorId": "S1",
    "type": "HIGH_TEMPERATURE",
    "message": "High Temperature",
    "timestamp": "2026-11-25T14:51:06.437859",
    "active": true
}
```

---

# Alert Rules

The backend checks sensor readings and connectivity against the assessment requirements.

### High Temperature

Triggered when:

```text
temperature > 35°C
```

Alert type:

```text
HIGH_TEMPERATURE
```

Message:

```text
High Temperature
```

### High Humidity

Triggered when:

```text
humidity > 75%
```

Alert type:

```text
HIGH_HUMIDITY
```

Message:

```text
High Humidity
```

### Sensor Disconnected

A sensor is considered offline when no valid reading has been received for:

```text
10 seconds
```

Alert type:

```text
SENSOR_DISCONNECTED
```

Message:

```text
Sensor Disconnected
```

### Duplicate Alert Prevention

The backend maintains an active state for alerts.

If the same condition continues across multiple readings, another identical active alert is not created.

When the condition clears, the active alert is deactivated.

If the same condition happens again later, a new alert can be created.

---

# Dashboard

The React dashboard provides:

- Login
- Logout
- Live sensor readings
- Temperature
- Humidity
- ONLINE/OFFLINE status
- Last reading time
- Active alerts
- Automatic updates

The dashboard polls the backend every:

```text
5 seconds
```

This was chosen instead of WebSockets because the assessment explicitly allows API polling for real-time updates.

---

# Running the Dashboard

Navigate to:

```bash
cd live-sensor-monitoring-dashboard
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The Vite development server will display the local URL in the terminal.

Open that URL in the browser.

---

# Recommended Startup Order

Start the components in this order:

### 1. MySQL

Make sure MySQL is running and the database exists:

```text
sensor_monitoring
```

### 2. Spring Boot Backend

Start:

```text
live-sensor-monitoring-backend
```

Verify:

```text
http://localhost:8080
```

### 3. Java Sensor Simulator

Start:

```text
sensor-simulator
```

The simulator begins sending readings every 2 seconds.

### 4. React Dashboard

Start:

```bash
npm run dev
```

Open the dashboard and login using a registered account.

---

# Error Handling

The backend provides structured error responses for invalid requests and authentication failures.

Examples include:

- Missing required fields
- Invalid temperature range
- Invalid humidity range
- Duplicate reading ID
- Duplicate email
- Invalid credentials
- Invalid date format
- Unauthorized access
- Unexpected server errors

Example:

```json
{
    "timestamp": "2026-11-25T14:38:03.5260956",
    "status": 401,
    "error": "Unauthorized",
    "message": "Invalid email or password"
}
```

---

# Security

The project uses:

- BCrypt password hashing
- JWT authentication
- Stateless Spring Security configuration
- Protected dashboard APIs
- Token blacklist for logout
- Environment variables for database password
- Environment variables for JWT secret

Sensitive credentials are not stored in the source code.

---

# Persistence

Sensor readings and alerts are persisted in MySQL.

Because the application uses JPA/Hibernate with MySQL persistence, sensor data remains available after restarting the Spring Boot application.

---

# Assumptions and Technical Decisions

### API Polling

The dashboard uses 5-second API polling instead of WebSockets.

The assessment allows either WebSockets or API polling for live updates, and polling keeps the implementation simple while satisfying the requirement.

### Sensor Reading Authentication

`POST /readings` is intentionally accessible without JWT authentication.

The sensor simulator is an independent machine-like client that sends readings to the backend, and the assessment does not specify credentials for the simulator.

Dashboard data retrieval APIs remain protected by JWT authentication.

### Fixed Sensors

The assessment specifies sensors S1 and S2, so the backend currently monitors these two sensors.

### Offline Detection

The backend checks sensor connectivity every 2 seconds.

A sensor becomes OFFLINE when its latest valid reading is older than 10 seconds.

### Server Timestamp

The timestamp for a reading is assigned by the backend rather than trusting a timestamp supplied by the client.

This ensures the backend controls the recorded reading time.

---

