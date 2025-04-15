# Individual Report: Gym Management System Development Challenges

This report outlines some of the key challenges encountered during the development of the Gym Management System project.

## 1. Learning and Configuring Maven

A significant learning curve involved becoming familiar with Apache Maven. As I was not previously accustomed to this build automation and dependency management tool, understanding its core concepts, lifecycle, and the structure of the `pom.xml` file required dedicated effort. Specifically:

*   **Dependency Management**: Grasping how Maven handles project dependencies, including finding the correct artifacts (like the PostgreSQL driver and jBCrypt) and managing their versions.
*   **Build Lifecycle**: Understanding the different phases (e.g., `clean`, `compile`, `install`, `package`) and how plugins like `exec-maven-plugin` and `sql-maven-plugin` integrate into this lifecycle.
*   **Configuration**: Setting up project properties and plugin configurations within the `pom.xml` file.

Overcoming this involved consulting Maven documentation and examples to effectively utilize it for building and running the application.

## 2. DAO and Service Layer Interaction

Designing the interaction between the Data Access Object (DAO) layer and the Service layer presented a conceptual challenge. The goal was to create a clear separation of concerns, where:

*   **DAO Layer**: Responsible solely for database interactions (CRUD operations) using JDBC and SQL.
*   **Service Layer**: Responsible for implementing business logic, coordinating calls to DAOs, handling data validation, and managing transactions if necessary.

Figuring out the appropriate flow of data and control between these layers – how the `WorkoutApp` interacts with Services, which in turn utilize DAOs – took some research and experimentation to solidify the pattern. This ensures the application remains maintainable and modular.

## 3. Integrating PostgreSQL Logic in Java

Embedding PostgreSQL interaction logic within the Java application, primarily within the DAO classes, required understanding Java Database Connectivity (JDBC). Key aspects included:

*   **Establishing Connections**: Managing database connections reliably using `DatabaseConnection.java`.
*   **Prepared Statements**: Learning to use `PreparedStatement` effectively to prevent SQL injection vulnerabilities and efficiently execute parameterized queries.
*   **Handling ResultSets**: Processing data returned from the database (`ResultSet`) and mapping it to Java objects (like `User`, `Membership`, `WorkoutClass`).
*   **Exception Handling**: Properly managing `SQLException` and other potential database-related errors.

This involved studying JDBC best practices and applying them to structure the database interaction code cleanly within the DAO methods.

---

Overall, while these areas presented challenges, working through them provided valuable experience in Java application architecture, build tools, and database integration.
