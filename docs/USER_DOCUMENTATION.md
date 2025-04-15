# Gym Management System - User Documentation

## 1. Overview

Welcome to the Gym Management System! This program helps manage various aspects of a gym, including user accounts, memberships, and workout classes. It's designed to be used by different types of people associated with the gym: Members, Trainers, and Administrators.

**How it Works:**

The application runs in your computer's console or terminal. When you start it, it presents menus with numbered options. You interact with the system by typing the number corresponding to the action you want to perform and pressing Enter.

The system keeps track of all information (like user details, class schedules, and membership payments) by storing it in a database.

**What it Does:**

*   **User Management:** Allows users to register for new accounts and log in. Administrators can view all users and delete accounts if necessary.
*   **Role-Based Access:** Provides different menus and capabilities depending on whether you are logged in as an Admin, Trainer, or Member.
*   **Membership Management:** Allows users to purchase memberships. Admins can track total revenue from memberships.
*   **Workout Class Management:** Trainers can create, view, update, and delete workout classes they are assigned to teach. Members can view available classes and enroll in them.

## 2. Classes and Interactions

The system is built using several components (Java classes) that work together:

*   **`WorkoutApp`:** This is the main program you interact with. It displays the menus, takes your input, and tells the other parts of the system what to do.
*   **`User` / `Admin` / `Member` / `Trainer`:** These represent the different people using the system. `User` is the general type, while `Admin`, `Member`, and `Trainer` are specific roles with different permissions. Your role determines which menus you see after logging in.
*   **`UserService` / `UserDAO`:** These work together to handle everything related to user accounts.
    *   `UserService`: Contains the logic for actions like registering a new user or checking login details.
    *   `UserDAO`: Directly talks to the database to save or retrieve user information.
*   **`Membership` / `MembershipService` / `MembershipDAO`:** Similar to the user classes, these handle membership information.
    *   `MembershipService`: Logic for purchasing or checking memberships, calculating revenue.
    *   `MembershipDAO`: Saves and retrieves membership details from the database.
*   **`WorkoutClass` / `WorkoutClassService` / `WorkoutClassDAO`:** These manage workout classes.
    *   `WorkoutClassService`: Logic for creating, updating, deleting, or viewing classes and handling enrollments.
    *   `WorkoutClassDAO`: Saves and retrieves class schedules and details from the database.
*   **`DatabaseConnection`:** A utility component that establishes the connection to the system's database, allowing the DAOs to store and fetch data.

**Interaction Flow:** When you choose an option in the `WorkoutApp` menu (e.g., "Register"), the `WorkoutApp` calls the appropriate Service (e.g., `UserService`). The Service performs checks and then uses its DAO (e.g., `UserDAO`) to interact with the database via the `DatabaseConnection`.

## 3. Class Description (Conceptual)

*   The `WorkoutApp` uses the `Service` classes.
*   Each `Service` class uses a corresponding `DAO` (Data Access Object) class.
*   The `DAO` classes interact with the `Database`.
*   The `DAO` classes also work with the `Model` classes (like `User`, `Membership`, `WorkoutClass`) to structure the data.

## 4. How to Start and Use the System

**Starting the Application:**

1.  Make sure you have Java installed on your computer.
2.  You will typically run the application from your development environment (like IntelliJ IDEA or Eclipse) or by executing a command in your terminal/console if the program has been packaged (e.g., into a JAR file). The main file to run is `WorkoutApp.java`.

**Using the Application:**

1.  **Main Menu:** When the application starts, you'll see:
    ```
    ╭--------------------------------------╮
    │ Welcome to the Gym Management System │
    ╰--------------------------------------╯

    1. Login
    2. Register
    3. Exit
    Choose an option:
    ```
2.  **Registering (New Users):**
    *   Choose option `2`.
    *   Follow the prompts to enter your desired `username`, `password`, `email`, `phone number`, and `address`.
    *   Select your role: `1` for Member, `2` for Trainer.
    *   If successful, you'll see a "Registration successful!" message. You can now log in.
3.  **Logging In:**
    *   Choose option `1`.
    *   Enter the `username` and `password` you registered with.
    *   If successful, you'll see a welcome message and a menu specific to your role.
4.  **Using Role Menus:**
    *   **Admin Menu:** Options typically include viewing all users, deleting users, and viewing total revenue.
    *   **Trainer Menu:** Options typically include creating new workout classes, viewing the classes you teach, updating class details, deleting classes, and managing your own membership.
    *   **Member Menu:** Options typically include viewing available workout classes, enrolling in classes, viewing your enrolled classes, and managing your membership.
    *   Follow the on-screen prompts for each option. Enter the corresponding number and press Enter.
5.  **Logging Out:** In your role-specific menu, there will be a "Logout" option. Choosing this will return you to the main menu.
6.  **Exiting:** From the main menu, choose option `3` to close the application.

If you encounter any errors (e.g., "Login failed", "Registration failed"), the system will usually provide a brief message explaining the problem. Make sure you enter information correctly as prompted.
