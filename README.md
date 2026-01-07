# Service Post Hub

## System Overview

**Service Post Hub** is a modular microservices-based platform designed for content management and user administration. It is built using a modern Java stack and follows a distributed architecture.

The system consists of two primary microservices:

1.  **[IAM Service](./iam_service/README.md)** (Identity and Access Management):
    *   The core service responsible for user authentication, role management, and the main business logic for Posts and Comments.
    *   Acts as the central authority for user data and content.

2.  **[Utils Service](./utils-service/README.md)**:
    *   A support service focused on cross-cutting concerns.
    *   Currently responsible for centralized **Action Logging**, tracking system events, and user activities across the platform.

## Architecture

The project is structured as a multi-module Maven project. Each service is independently deployable but shares a common infrastructure definition via Docker Compose.

*   **Database:** Both services utilize **PostgreSQL**. They share the same database instance (`iam_db`) but operate on strictly separated schemas (`v1_iam_service` and `v1_utils_service`) to ensure data isolation.
*   **Communication:** Services interact via REST APIs.
*   **Infrastructure:** The entire stack is containerized using Docker and orchestrated with Docker Compose.

## Technology Stack

*   **Language:** Java 21
*   **Framework:** Spring Boot 3
    *   Spring Web (REST)
    *   Spring Data JPA (Hibernate)
    *   Spring Security (JWT)
*   **Database:** PostgreSQL 15
*   **Migration:** Flyway
*   **Containerization:** Docker & Docker Compose
*   **Build Tool:** Maven

## Getting Started

### Prerequisites

*   **Java 21**
*   **Maven**
*   **Docker** & **Docker Compose**

### Installation & Running

The easiest way to run the entire system is using Docker Compose.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/DimaCherkes/service-post-hub.git
    cd service-post-hub
    ```

2.  **Build the artifacts:**
    This command compiles both microservices and generates the `.jar` files required for the Docker images.
    ```bash
    mvn clean package -DskipTests
    ```

3.  **Run with Docker Compose:**
    This will start the PostgreSQL database and both microservices.
    ```bash
    docker-compose up -d --build
    ```
    This will start Kafka and Zookeeper. 
    ```bash
    docker-compose -f docker-compose-infra.yml up -d 
    ```

### Accessing the Services

Once the system is up and running:

| Service       | Port | Swagger UI Documentation | Description |
|---------------|------|--------------------------|-------------|
| **IAM Service** | 8189 | [http://localhost:8189/swagger-ui.html](http://localhost:8189/swagger-ui.html) | Main API for Users, Posts, Comments. |
| **Utils Service**| 8185 | [http://localhost:8185/swagger-ui.html](http://localhost:8185/swagger-ui.html) | API for Logs and Utilities. |
