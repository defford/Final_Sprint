package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the database connection to the Gym Management System's PostgreSQL database.
 * Provides a method to obtain a JDBC connection using preset credentials.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/gym_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "jordan1234";

    /**
     * Establishes and returns a connection to the PostgreSQL database.
     *
     * @return a Connection object to the gym_management database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}