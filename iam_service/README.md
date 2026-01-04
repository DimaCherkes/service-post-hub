# IAM Service

## Service Overview

The **IAM (Identity and Access Management) Service** is the core component of the Service Post Hub platform. It acts as the primary backend for user management, authentication, and the main content domain (Posts and Comments).

## Core Responsibilities

*   **Authentication & Authorization:**
    *   Implements secure access using **JWT (JSON Web Tokens)**.
    *   Manages User Registration, Login, and Token Refresh flows.
    *   Enforces Role-Based Access Control (RBAC) with roles: `SUPER_ADMIN`, `ADMIN`, `USER`.
*   **User Management:**
    *   Full CRUD operations for User profiles.
    *   Soft-delete functionality for users.
*   **Content Management:**
    *   Management of **Posts**: Create, Read, Update, Delete (CRUD) and Search.
    *   Management of **Comments**: Create, Read, Update, Delete (CRUD) and Search.

## Key Entities

*   **User:** Stores credentials (hashed), profile info, and registration status.
*   **Role:** Defines system permissions (e.g., `ROLE_USER`, `ROLE_ADMIN`).
*   **Post:** User-generated content with title and body.
*   **Comment:** Feedback attached to posts.
*   **RefreshToken:** Secure persistence for maintaining long-term sessions.

## Configuration

### Database
The service connects to the `iam_db` database but restricts its operations to the **`v1_iam_service`** schema. Migration scripts are handled by Flyway in `src/main/resources/db/migration`.

### Application Properties
Key configuration parameters are in `application.properties`:

*   `server.port`: **8189**
*   `jwt.secret`: Secret key for signing tokens.
*   `jwt.lifetime`: Access token validity duration.
*   `jwt.expiration`: Refresh token validity duration.

## API Endpoints

The service exposes a comprehensive REST API documented via Swagger/OpenAPI.

### Authentication
*   **POST /auth/login:** Authenticate the user and returns an access/refresh token.
*   **GET /auth/refresh/token:** Generates new access token using provided refresh token.
*   **POST /auth/register:** Creates new user and returns authentication details.

### User Management
* **GET /users/{id}:** Get a user by their ID.
* **POST /users/create:** Create a new user.
* **PUT /users/{id}:** Update an existing user.
* **DELETE /users/{id}:** Soft delete a user.
* **GET /users/all:** Get a paginated list of all users.
* **POST /users/search:** Search for users based on specified criteria

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