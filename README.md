# Gym Management System

A Java-based console application for managing a gym, including users, memberships, and workout classes.

## Features

- User authentication and role-based access control (Admin, Trainer, Member)
- Membership management
- Workout class management
- Class enrollment tracking
- User management

## Project Structure

```
.
├── pom.xml                   # Maven build configuration
├── README.md                 # This file
└── src
    ├── main
    │   ├── java                # Main source code
    │   │   ├── database        # Database connection logic
    │   │   ├── membership      # Membership related classes (Model, DAO, Service)
    │   │   ├── models          # User role models (Admin, Trainer, Member)
    │   │   ├── user            # User related classes (Model, DAO, Service)
    │   │   ├── workout         # Workout class related classes (Model, DAO, Service)
    │   │   └── WorkoutApp.java # Main application entry point
    │   └── resources
    │       └── sql
    │           └── setup.sql   # Database schema setup script
    └── test                    # Unit tests (if any)
```

## Getting Started

### Prerequisites

- **Java**: Version 17 or higher
- **Maven**: Build tool (used for dependency management and running the app)
- **PostgreSQL**: Database system

Key Dependencies (Managed by Maven):
- `org.postgresql:postgresql`: PostgreSQL JDBC Driver
- `org.mindrot:jbcrypt`: Password Hashing
- `ch.qos.logback:logback-classic`: Logging

### Database Setup

1.  **Ensure PostgreSQL is running.**
2.  **Create a PostgreSQL database.** The application expects a database named `postgres` by default, accessible via `jdbc:postgresql://localhost:5432/postgres` with username `postgres` and password `postgres`. You can modify connection details in `pom.xml` under the `sql-maven-plugin` configuration if needed, but it's generally better to configure this externally in a real application.
3.  **Run the SQL setup script:** Execute the commands in `src/main/resources/sql/setup.sql` against your database. This script creates the necessary tables (`Users`, `Memberships`, `WorkoutClasses`, `ClassEnrollments`) and inserts a default admin user.

### Default Admin Login

To access the admin panel, use the following credentials:
- Username: `admin`
- Password: `admin123`

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd Final_Sprint 
    ```
2.  **Build the project:** (This also compiles the code and downloads dependencies)
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    ```bash
    mvn exec:java
    ```

This will start the console application.

## User Roles

### Admin
- View all users
- Delete users
- View total revenue (Note: Revenue calculation logic might need implementation/verification)

### Trainer
- Create/update/delete workout classes
- View assigned classes

### Member
- Purchase gym membership
- Enroll in workout classes
- View personal information and schedule
