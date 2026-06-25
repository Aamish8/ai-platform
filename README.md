# AI Platform

A microservices-based AI platform built with Spring Boot, Spring Security, Redis, MySQL, Docker, and Ollama.

## Architecture

Client (Postman / Frontend)

↓

AI Service

├── JWT Authentication Validation

├── Redis Cache

├── MySQL Chat History

├── Ollama (Llama 3.2)

└── Auth Service Communication

↓

Auth Service

├── User Registration

├── User Login

└── JWT Token Validation

## Features

### Auth Service

* User Registration
* User Login
* JWT Token Generation
* JWT Token Validation
* MySQL Persistence

### AI Service

* Protected AI Endpoints
* Token Validation through Auth Service
* Ollama Integration (Llama 3.2)
* Redis-Based Response Caching
* Chat History Storage
* Paginated Chat History Retrieval

### Infrastructure

* Dockerized Services
* Docker Compose Setup
* MySQL Container
* Redis Container
* Service-to-Service Communication

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* Redis
* Spring AI
* Ollama
* Docker
* Docker Compose
* JWT Authentication

## API Endpoints

### Auth Service

#### Register

POST /api/auth/register

#### Login

POST /api/auth/login

#### Validate Token

POST /api/auth/validate

### AI Service

#### Ask AI

POST /api/ai/ask

#### Chat History

GET /api/ai/history?page=0&size=10

## Running Locally

### Start Ollama

ollama serve

Run model:

ollama run llama3.2

### Start Infrastructure

docker compose up -d

### Start Services

Run Auth Service

Run AI Service

## Example Flow

1. Register a user.
2. Login and obtain JWT token.
3. Call AI Service with Authorization header.
4. AI Service validates token using Auth Service.
5. Query is sent to Ollama.
6. Response is cached in Redis.
7. Chat history is stored in MySQL.
8. History can be retrieved using pagination.

## Future Improvements

* OpenFeign Integration
* API Gateway
* Service Discovery (Eureka)
* Kubernetes Deployment
* Role-Based Access Control
* Monitoring and Logging

## Author

Aamish
