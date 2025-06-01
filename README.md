<h1 align="center">BANCKSERVICE</h1>
<p align="center"><em>Empowering seamless banking experiences, effortlessly secure.</em></p>

<p align="center">
  <img alt="Last Commit" src="https://img.shields.io/github/last-commit/Torrescjua/banckservice" />
  <img alt="Java" src="https://img.shields.io/badge/Language-Java_98.5%25-007396?logo=java&logoColor=white" />
  <img alt="Languages" src="https://img.shields.io/badge/languages-2-blue" />
</p>

<p align="center">
  <img alt="Docker" src="https://img.shields.io/badge/Tools-Docker-blue">
  <img alt="XML" src="https://img.shields.io/badge/Tools-XML-green">
</p>

---

## Table of Contents

- [Overview](#overview)
- [Getting&nbsp;Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
    - [Using&nbsp;Docker](#using-docker)
    - [Using&nbsp;Maven](#using-maven)
  - [Configuration](#configuration)
  - [Usage](#usage)
  - [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

**Banckservice** is a Spring Boot micro‑service that handles account management and payment authorisation.  
It exposes a RESTful API secured with JWT and IP–whitelisting, backed by a MySQL database and packaged with a multi‑stage Docker build for tiny images.

### ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🐳 **Multi‑Stage Docker Build** | Small final image & fast deployments |
| ⚙️ **Spring Boot 3.5** | Modern, production‑ready Java stack |
| 📡 **RESTful Controllers** | Endpoints for authentication, retail purchases & conciliation |
| 🔒 **JWT + IP Filter** | Token‑based auth plus `hasIpAddress()` rules for B2B calls |
| 💾 **JPA (Hibernate 6)** | Entities for `CuentaBanco`, `Compra`, `Transaccion`, etc. |
| 🛠 **CI‑ready** | Standard Maven lifecycle & unit tests |

---

## Getting Started

### Prerequisites

| Tool | Version |
|------|---------|
| **JDK** | 17 + |
| **Maven** | 3.6 + |
| **Docker** | 20.10 + (optional) |
| **MySQL** | 8 + (or compatible) |
| **Git** | any |

### Installation

```bash
# 1 · Clone the repository
git clone https://github.com/Torrescjua/banckservice.git
cd banckservice
```

#### Using Docker

```bash
# 2 · Build image
docker build -t banckservice:latest .

# 3 · Run container (linking to a MySQL instance)
docker run -d --name banckservice   -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/bankdb   -e SPRING_DATASOURCE_USERNAME=<DB_USER>   -e SPRING_DATASOURCE_PASSWORD=<DB_PASS>   -e JWT_SECRET=<your_jwt_secret>   -p 8080:8080   banckservice:latest
```

#### Using Maven

```bash
# 2 · Install dependencies & build
mvn clean package -DskipTests

# 3 · Run
java -jar target/banckservice-*.jar
```

### Configuration

All tunables live in **`src/main/resources/application.properties`** or can be overridden via env‑vars:

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/bankdb` | JDBC URL |
| `spring.datasource.username` | `root` | DB user |
| `spring.datasource.password` | `password` | DB password |
| `jwt.secret` | _none_ | Secret used to sign tokens |
| `jwt.expiration-in-ms` | `3600000` | Token TTL (1 h) |
| `security.retail.ip` | `10.43.102.241` | Allowed IP for `/api/retail/**` |
| `security.conciliacion.ip` | `10.43.96.39` | Allowed IP for `/api/conciliacion/**` |

---

## Usage

### Authentication

```bash
POST /api/auth/register   # create user
POST /api/auth/login      # obtain JWT
```

Add the returned token to subsequent requests:

```
Authorization: Bearer <token>
```

### Retail Purchase – Happy Path

```http
POST /api/retail/compras
X-Forwarded-For: 10.43.102.241
Content-Type: application/json

{
  "clienteCedula": 12345678,
  "monto": 25000.0
}
```

<details>
<summary>Sample success response (200)</summary>

```json
{
  "accepted": true,
  "message": "Compra aceptada",
  "nuevoSaldo": 75000.0
}
```
</details>

<details>
<summary>Sample failure response (400)</summary>

```json
{
  "accepted": false,
  "message": "Saldo insuficiente",
  "nuevoSaldo": 5000.0
}
```
</details>

---

## Testing

```bash
# Run unit & integration tests
mvn test
```

_Test coverage includes service logic, repository CRUD and controller endpoints (MockMvc)._

---

## Contributing

1. Fork the project  
2. Create a feature branch `git checkout -b feat/AmazingThing`  
3. Commit your changes `git commit -m 'feat: add AmazingThing'`  
4. Push to the branch `git push origin feat/AmazingThing`  
5. Open a pull request 🚀

---

## License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for more information.
