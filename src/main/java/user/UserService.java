package user;

import models.Admin;
import models.Trainer;
import models.Member;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for user-related business logic in the Gym Management System.
 * Handles registration, authentication, retrieval, update, and deletion of users.
 */
public class UserService {
    private final UserDAO userDAO;

    /**
     * Constructs a UserService and initializes the UserDAO.
     * @throws SQLException if a database access error occurs
     */
    public UserService() throws SQLException {
        this.userDAO = new UserDAO();
    }

    /**
     * Registers a new user with the specified details and role.
     * Throws an exception if the username is already taken or the role is invalid.
     * @param userName    The user's name or username
     * @param password    The user's password
     * @param email       The user's email address
     * @param phoneNumber The user's phone number
     * @param address     The user's address
     * @param role        The user's role (ADMIN, TRAINER, MEMBER)
     * @return The created User object
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if the username exists or role is invalid
     */
    public User registerUser(String userName, String password, String email, String phoneNumber, String address, String role) throws SQLException {
        // Check if username already exists
        if (userDAO.getUserByUsername(userName) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create appropriate user type based on role
        User user;
        switch (role.toUpperCase()) {
            case "ADMIN":
                user = new Admin();
                break;
            case "TRAINER":
                user = new Trainer();
                break;
            case "MEMBER":
                user = new Member();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Set user properties
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);

        // Create user in database
        return userDAO.createUser(user);
    }

    /**
     * Authenticates a user by username and password.
     * @param username The user's username
     * @param password The user's password
     * @return The authenticated User object
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if authentication fails
     */
    public User login(String username, String password) throws SQLException {
        if (!userDAO.verifyPassword(username, password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return userDAO.getUserByUsername(username);
    }

    /**
     * Retrieves a user by their unique userId.
     * @param userId The user's unique identifier
     * @return The User object
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if the user is not found
     */
    public User getUserById(int userId) throws SQLException {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all User objects
     * @throws SQLException if a database access error occurs
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    /**
     * Updates an existing user's details (except password).
     * @param user The User object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateUser(User user) throws SQLException {
        return userDAO.updateUser(user);
    }

    /**
     * Updates a user's password.
     * @param userId The user's unique identifier
     * @param newPassword The new password to set
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        return userDAO.updatePassword(userId, newPassword);
    }

    /**
     * Deletes a user by their userId, preventing deletion of admin users.
     * @param userId The user's unique identifier
     * @return true if the user was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if attempting to delete an admin user
     */
    public boolean deleteUser(int userId) throws SQLException {
        User user = getUserById(userId);
        if (user.getUserRole().equals("ADMIN")) {
            throw new IllegalArgumentException("Cannot delete admin user");
        }
        return userDAO.deleteUser(userId);
    }
}
