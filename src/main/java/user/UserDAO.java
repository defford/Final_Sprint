package user;

import database.DatabaseConnection;
import models.Admin;
import models.Trainer;
import models.Member;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for user-related database operations.
 * Handles CRUD operations and authentication for users in the Gym Management System.
 */
public class UserDAO {
    private final Connection connection;

    /**
     * Constructs a UserDAO and establishes a database connection.
     * @throws SQLException if a database access error occurs
     */
    public UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Inserts a new user into the database.
     * @param user The User object to insert
     * @return The created User object with userId set, or null if creation failed
     * @throws SQLException if a database access error occurs
     */
    public User createUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (userName, userPassword, userEmail, userPhoneNumber, userAddress, userRole) VALUES (?, ?, ?, ?, ?, ?) RETURNING userId";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getUserRole());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their unique userId.
     * @param userId The user's unique identifier
     * @return The User object, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their username.
     * @param username The user's username
     * @return The User object, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userName = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all User objects
     * @throws SQLException if a database access error occurs
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        }
        return users;
    }

    /**
     * Updates an existing user's details (except password) in the database.
     * @param user The User object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET userName = ?, userEmail = ?, userPhoneNumber = ?, userAddress = ?, userRole = ? WHERE userId = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getUserRole());
            stmt.setInt(6, user.getUserId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates a user's password in the database.
     * @param userId The user's unique identifier
     * @param newPassword The new password to set (will be hashed)
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE Users SET userPassword = ? WHERE userId = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a user from the database.
     * Prevents deletion of admin users.
     * @param userId The user's unique identifier
     * @return true if the user was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if attempting to delete an admin user
     */
    public boolean deleteUser(int userId) throws SQLException {
        // Check if user is admin before deleting
        String checkSql = "SELECT userRole FROM Users WHERE userId = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getString("userRole").equals("ADMIN")) {
                throw new IllegalArgumentException("Cannot delete admin user");
            }
        }

        String sql = "DELETE FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifies a user's password by comparing it to the stored hash.
     * @param username The user's username
     * @param password The plain-text password to verify
     * @return true if the password is correct, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean verifyPassword(String username, String password) throws SQLException {
        String sql = "SELECT userPassword FROM Users WHERE userName = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("userPassword");
                return BCrypt.checkpw(password, hashedPassword);
            }
        }
        return false;
    }

    /**
     * Creates a User object from a ResultSet row.
     * Determines the user role and instantiates the correct subclass.
     * @param rs The ResultSet positioned at the user row
     * @return The User (or subclass) object
     * @throws SQLException if a database access error occurs or role is invalid
     */
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        String role = rs.getString("userRole");
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
                throw new SQLException("Invalid user role: " + role);
        }

        user.setUserId(rs.getInt("userId"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("userPassword"));
        user.setEmail(rs.getString("userEmail"));
        user.setPhoneNumber(rs.getString("userPhoneNumber"));
        user.setAddress(rs.getString("userAddress"));
        user.setUserRole(rs.getString("userRole"));

        return user;
    }
}
