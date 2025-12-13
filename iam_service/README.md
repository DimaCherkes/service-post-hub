# IAM Service

## Project Overview

This project is a Java-based Identity and Access Management (IAM) service built with Spring Boot. It provides functionalities for user authentication, authorization, and management. The service uses JSON Web Tokens (JWT) for securing API endpoints and PostgreSQL as its database.

**Key Technologies:**

*   **Backend:** Spring Boot 3.5.6, Java 21
*   **Database:** PostgreSQL
*   **Authentication:** Spring Security, JWT
*   **Database Migration:** Flyway
*   **API Documentation:** SpringDoc OpenAPI

**Architecture:**

The application follows a standard Spring Boot project structure. It includes:

*   **Controllers:** Handle incoming HTTP requests and delegate to services.
*   **Services:** Contain the business logic of the application.
*   **Repositories:** Interact with the database using Spring Data JPA.
*   **Models:** Define the data structures (DTOs, entities).
*   **Security:**  Configuration for JWT-based authentication and authorization.

## Building and Running the Project

To build and run the project, you need to have Java 21 and Maven installed.

### Building

To build the project and create an executable JAR file, run the following command in the project's root directory:

```bash
mvn clean install
```

This will generate a JAR file in the `target` directory.

### Running

To run the application, you can use the following command:

```bash
java -jar target/iam_service-0.0.1-SNAPSHOT.jar
```

Alternatively, you can use the Spring Boot Maven plugin to run the application directly:

```bash
mvn spring-boot:run
```

The application will start on port `8189` by default.

### Docker

The project includes a `Dockerfile` and `docker-compose.yml` for running the application in a Docker container.

To build and run the application with Docker, use the following commands:

```bash
docker-compose up --build
```

## Development Conventions

### API Documentation

The project uses SpringDoc OpenAPI to generate API documentation. Once the application is running, you can access the Swagger UI at the following URL:

[http://localhost:8189/swagger-ui.html](http://localhost:8189/swagger-ui.html)

### Database Migrations

The project uses Flyway for database migrations. The migration scripts are located in the `src/main/resources/db/migration` directory. Flyway will automatically apply the migrations when the application starts.

### Logging

The application uses SLF4J for logging. The logging configuration can be found in the `src/main/resources/application.properties` file. By default, the root logging level is set to `INFO`.

## Database Schema

The database schema is managed by Flyway and the initial schema is defined in the `V1__init.sql` and `V2__add_comments_logic.sql` files. The main tables are:

*   **users:** Stores user information, including username, password, and email.
*   **posts:** Stores blog posts created by users.
*   **comments:** Stores comments on posts.
*   **roles:** Defines the roles available in the system (e.g., SUPER_ADMIN, ADMIN, USER).
*   **users_roles:** A join table that maps users to their roles.
*   **refresh_tokens:** Stores refresh tokens for users.

## API Endpoints

The service exposes the following API endpoints:

### Authentication

*   **POST /auth/login:** Authenticate the user and returns an access/refresh token.
*   **GET /auth/refresh/token:** Generates new access token using provided refresh token.
*   **POST /auth/register:** Creates new user and returns authentication details.

### User Management

*   **GET /users/{id}:** Get a user by their ID.
*   **POST /users/create:** Create a new user.
*   **PUT /users/{id}:** Update an existing user.
*   **DELETE /users/{id}:** Soft delete a user.
*   **GET /users/all:** Get a paginated list of all users.
*   **POST /users/search:** Search for users based on specified criteria.

### Post Management

*   **GET /posts/{id}:** Get a post by its ID.
*   **POST /posts/create:** Create a new post.
*   **PUT /posts/{id}:** Update an existing post.
*   **DELETE /posts/{id}:** Soft delete a post.
*   **GET /posts/all:** Get a paginated list of all posts.
*   **POST /posts/search:** Search for posts based on specified criteria.

### Comment Management

*   **GET /comments/{id}:** Get a comment by its ID.
*   **POST /comments/create:** Create a new comment.
*   **PUT /comments/{id}:** Update an existing comment.
*   **DELETE /comments/{id}:** Soft delete a comment.
*   **GET /comments/all:** Get a paginated list of all comments.
*   **POST /comments/search:** Search for comments based on specified criteria.
