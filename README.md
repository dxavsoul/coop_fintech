# CorFinance — Core Banking System for Fintech

## Overview

**CorFinance** is a microservice-based **Core Banking System** built with **Spring Boot** (Java), designed to power cooperative financial institutions and modern fintech companies. It exposes a secure RESTful API that serves as the backend engine for the [**CorFinance Mobile App**](https://github.com/dxavsoul/fintech_app) — a Flutter-based mobile application for end users to manage their finances on the go.

---

## 🏦 What This Core Banking System Can Do

### 👤 Customer Management
- **Register new customers** with full KYC (Know Your Customer) information: name, email, phone, date of birth, address, city, state, postal code, and country.
- Support for multiple **identity document types**: Passport, National ID, Driver's License, and Other.
- Manage **customer lifecycle statuses**: Active, Inactive, Suspended.
- **Forgot password** flow via email (powered by Keycloak identity management).
- Email verification for newly registered customers.

### 🏧 Account Management
- **Create bank accounts** for customers tied directly to their profile.
- Supports multiple **account types**:
  - `CHECKING` — Day-to-day transactional account
  - `SAVINGS` — Interest-bearing savings account
  - `MONEY_MARKET` — High-yield liquid account
  - `CERTIFICATE_OF_DEPOSIT` — Fixed-term deposit account
  - `LOAN` — Loan account
  - `CREDIT` — Credit account
- Retrieve account details by **account number**.
- Admin-only endpoint to **list all accounts** in the system.
- Accounts use **NUBAN** (Nigeria Uniform Bank Account Number) standard numbering.
- Tracks **account balance**, **status** (Active, Inactive, Frozen, Closed), and timestamps for creation and last activity.

### 💳 Debit Card Management
- **Request and issue debit cards** linked to customer accounts.
- Supports major **card networks**: VISA, MasterCard, American Express, Discover, JCB, Diners Club, UnionPay, Maestro, and Verve.
- Automatic **debit card number generation** derived from the account's NUBAN.
- **CVV generation** for card security.
- **Debit card validation** logic.
- Tracks card **status** (Active, Inactive, Blocked, Expired) and **expiry dates**.

### 💸 Transactions (Banking Operations)
- **Deposits** — Credit funds into an account.
- **Withdrawals** — Debit funds from an account.
- **Transfers** — Move funds between accounts.
- **Loan payments** — Process payments against loan accounts.
- Transaction events are routed through **RabbitMQ** message queues:
  - `deposit.transaction`
  - `withdrawal.transaction`
  - `transfer.transaction`
  - `loan.payment`
- Transactions include **geolocation data** (`LocationDto`) for fraud detection and audit purposes.

### 🔔 Notifications
- **Push notifications** to individual users via **Firebase Cloud Messaging (FCM)**.
- **Topic-based broadcast notifications** to groups of users.
- Welcome email queue (`welcomeMailQueue.queue`) for new customer onboarding.

### 🔐 Security & Identity
- **Keycloak** integration for enterprise-grade IAM (Identity and Access Management):
  - User registration and credential management.
  - Password reset flows.
  - JWT-based authentication and authorization.
  - Role-based access control (RBAC) — e.g., `ADMIN` role restrictions.
- **Firebase Authentication** as a secondary identity provider for mobile clients.
- JWT tokens with configurable expiration and secret.
- Spring Security with OAuth2 Resource Server for stateless API protection.

### 💳 Payment Processing
- **Stripe** integration (in progress) for payment processing and card management.

---

## 📱 Mobile App Integration

This backend is the API engine for the [**CorFinance Flutter App**](https://github.com/dxavsoul/fintech_app).

The mobile app connects to this core to provide end users with:

| Mobile Feature | Backend Capability |
|---|---|
| User registration & onboarding | `POST /api/v1/customer/register` |
| Forgot/reset password | `PUT /api/v1/customer/forgot-password` |
| View account details | `GET /api/v1/accounts/account` |
| Create a bank account | `POST /api/v1/accounts/create` |
| Request a debit card | `POST /api/debitcard/cardRequest` |
| Fund transfers | `POST /api/bank/transfer` |
| Withdrawals | `POST /api/bank/withdraw` |
| Push notifications | Firebase FCM (real-time alerts) |

---

## 🧱 Architecture

```
[ Flutter Mobile App (fintech_app) ]
            │
            ▼
[ API Gateway / Backend Gateway Client ]
            │
            ▼
  [ Banking Core — Spring Boot :8081 ]
     ├── Customer Service
     ├── Account Service
     ├── Debit Card Service
     ├── Transaction Service (via RabbitMQ)
     ├── Keycloak (Auth/IAM)
     ├── Firebase (Push Notifications & Auth)
     └── Stripe (Payments)
            │
            ▼
  [ PostgreSQL Database (Neon Cloud) ]
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3 |
| Database | PostgreSQL (Neon Cloud) |
| ORM | Spring Data JPA / Hibernate |
| Authentication | Keycloak + JWT + Firebase Auth |
| Messaging | RabbitMQ |
| Push Notifications | Firebase Cloud Messaging (FCM) |
| Payments | Stripe (planned) |
| API Docs | SpringDoc OpenAPI / Swagger UI |
| Containerization | Docker |
| Mobile Client | Flutter (via [fintech_app](https://github.com/dxavsoul/fintech_app)) |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- PostgreSQL (or use the pre-configured Neon cloud database)
- Keycloak instance running on port `9080`
- RabbitMQ instance

### Run with Docker
```bash
cd banking-core
docker build -t banking-core .
docker run -p 8081:8081 banking-core
```

### API Documentation
Once running, access the Swagger UI at:
```
http://localhost:8081/swagger-ui
```

---

## 📂 Project Structure

```
coop_fintech/
├── banking-core/          # Core banking microservice (Spring Boot)
├── creditcard/            # Credit card microservice (Spring Boot)
├── backend-gateway-client/ # API Gateway
└── common-dto/            # Shared DTOs across services
```

---

## 🔗 Related Repositories

- 📱 **Mobile App**: [github.com/dxavsoul/fintech_app](https://github.com/dxavsoul/fintech_app) — Flutter mobile client that consumes this API.

