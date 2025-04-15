import user.User;
import user.UserService;
import membership.MembershipService;
import workout.WorkoutClassService;
import membership.Membership;
import workout.WorkoutClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Gym Management System.
 * Handles user interaction, authentication, and menu navigation for different user roles (Admin, Trainer, Member).
 */
public class WorkoutApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static UserService userService;
    private static MembershipService membershipService;
    private static WorkoutClassService workoutClassService;
    private static User currentUser;

    /**
     * Entry point for the Gym Management System application.
     * Initializes services and launches the main menu.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            initializeServices();
            showMainMenu();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * Initializes the core service objects for user, membership, and workout class management.
     *
     * @throws SQLException if a database connection error occurs
     */
    private static void initializeServices() throws SQLException {
        userService = new UserService();
        membershipService = new MembershipService();
        workoutClassService = new WorkoutClassService();
    }

    /**
     * Displays the main menu for login, registration, or exit.
     * Handles user input and routes to the appropriate logic.
     */
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n\n╭--------------------------------------╮");
            System.out.println("│ Welcome to the Gym Management System │");
            System.out.println("╰--------------------------------------╯\n");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles user login by prompting for credentials and authenticating.
     * On successful login, shows the role-specific menu.
     */
    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            currentUser = userService.login(username, password);
            System.out.println("Welcome, " + currentUser.getUserName() + "!");
            showRoleSpecificMenu();
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    /**
     * Handles new user registration by prompting for details and creating the user.
     * Allows registration as either a Member or a Trainer.
     */
    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.println("Select role:");
        System.out.println("1. Member");
        System.out.println("2. Trainer");
        System.out.print("Choose role (1-2): ");
        
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String role = roleChoice == 1 ? "MEMBER" : "TRAINER";

        try {
            userService.registerUser(username, password, email, phoneNumber, address, role);
            System.out.println("Registration successful! Please login.");
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Displays the menu corresponding to the current user's role (Admin, Trainer, Member).
     * Loops until the user logs out.
     */
    private static void showRoleSpecificMenu() {
        while (currentUser != null) {
            System.out.println("\n=== " + currentUser.getUserRole() + " Menu ===");
            switch (currentUser.getUserRole()) {
                case "ADMIN":
                    showAdminMenu();
                    break;
                case "TRAINER":
                    showTrainerMenu();
                    break;
                case "MEMBER":
                    showMemberMenu();
                    break;
            }
        }
        System.out.println("Logged out successfully!");
    }

    /**
     * Displays and handles the Admin menu options: view users, delete user, view revenue, logout.
     */
    private static void showAdminMenu() {
        System.out.println("1. View all users");
        System.out.println("2. Delete user");
        System.out.println("3. View total revenue");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (choice) {
                case 1:
                    List<User> users = userService.getAllUsers();
                    users.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter user ID to delete: ");
                    int userId = scanner.nextInt();
                    if (userService.deleteUser(userId)) {
                        System.out.println("User deleted successfully.");
                    }
                    break;
                case 3:
                    double revenue = membershipService.calculateTotalRevenue();
                    System.out.printf("Total Revenue: $%.2f%n", revenue);
                    break;
                case 4:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays and handles the Trainer menu options: create/view/update/delete workout classes, purchase membership, logout.
     */
    private static void showTrainerMenu() {
        System.out.println("1. Create workout class");
        System.out.println("2. View my classes");
        System.out.println("3. Update class");
        System.out.println("4. Delete class");
        System.out.println("5. Purchase membership");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (choice) {
                case 1:
                    createWorkoutClass();
                    break;
                case 2:
                    List<WorkoutClass> classes = workoutClassService.getWorkoutClassesByTrainerId(currentUser.getUserId());
                    classes.forEach(System.out::println);
                    break;
                case 3:
                    updateWorkoutClass();
                    break;
                case 4:
                    deleteWorkoutClass();
                    break;
                case 5:
                    purchaseMembership();
                    break;
                case 6:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays and handles the Member menu options: view classes, purchase membership, view memberships, logout.
     */
    private static void showMemberMenu() {
        System.out.println("1. View available classes");
        System.out.println("2. Purchase membership");
        System.out.println("3. View my memberships");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (choice) {
                case 1:
                    List<WorkoutClass> classes = workoutClassService.getAllWorkoutClasses();
                    classes.forEach(System.out::println);
                    break;
                case 2:
                    purchaseMembership();
                    break;
                case 3:
                    List<Membership> memberships = membershipService.getMembershipsByUserId(currentUser.getUserId());
                    memberships.forEach(System.out::println);
                    break;
                case 4:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the Trainer to create a new workout class.
     *
     * @throws SQLException if a database error occurs
     */
    private static void createWorkoutClass() throws SQLException {
        System.out.print("Enter class type: ");
        String type = scanner.nextLine();
        System.out.print("Enter class description: ");
        String description = scanner.nextLine();

        WorkoutClass workoutClass = workoutClassService.createWorkoutClass(type, description, currentUser.getUserId());
        System.out.println("Workout class created: " + workoutClass);
    }

    /**
     * Prompts the Trainer to update an existing workout class.
     * Only allows updating classes owned by the current Trainer.
     *
     * @throws SQLException if a database error occurs
     */
    private static void updateWorkoutClass() throws SQLException {
        System.out.print("Enter class ID to update: ");
        int classId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        WorkoutClass workoutClass = workoutClassService.getWorkoutClassById(classId);
        if (workoutClass.getTrainerId() != currentUser.getUserId()) {
            System.out.println("You can only update your own classes.");
            return;
        }

        System.out.print("Enter new class type: ");
        workoutClass.setWorkoutClassType(scanner.nextLine());
        System.out.print("Enter new class description: ");
        workoutClass.setWorkoutClassDescription(scanner.nextLine());

        if (workoutClassService.updateWorkoutClass(workoutClass)) {
            System.out.println("Workout class updated successfully.");
        }
    }

    /**
     * Prompts the Trainer to delete one of their workout classes.
     *
     * @throws SQLException if a database error occurs
     */
    private static void deleteWorkoutClass() throws SQLException {
        System.out.print("Enter class ID to delete: ");
        int classId = scanner.nextInt();
        
        if (workoutClassService.deleteWorkoutClass(classId, currentUser.getUserId())) {
            System.out.println("Workout class deleted successfully.");
        }
    }

    /**
     * Handles the purchase of a membership for the current user.
     *
     * @throws SQLException if a database error occurs
     */
    private static void purchaseMembership() throws SQLException {
        System.out.println("Available Membership Types:");
        System.out.println("1. Monthly ($50)");
        System.out.println("2. Annual ($500)");
        System.out.print("Choose membership type (1-2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String type = choice == 1 ? "Monthly" : "Annual";
        String description = choice == 1 ? "30-day membership" : "365-day membership";
        double cost = choice == 1 ? 50.0 : 500.0;

        Membership membership = membershipService.createMembership(type, description, cost, currentUser.getUserId());
        System.out.println("Membership purchased successfully: " + membership);
    }
}
