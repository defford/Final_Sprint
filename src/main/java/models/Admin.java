package models;

import user.User;

/**
 * Represents an admin user in the Gym Management System.
 * Inherits from the User class and sets the user role to "ADMIN".
 */
public class Admin extends User {
    /**
     * Default constructor for Admin. Sets the user role to "ADMIN".
     */
    public Admin() {
        super();
        setUserRole("ADMIN");
    }

    /**
     * Constructs an Admin with all user fields specified.
     *
     * @param userId      The unique identifier for the user
     * @param userName    The username
     * @param password    The user's password
     * @param email       The user's email address
     * @param phoneNumber The user's phone number
     * @param address     The user's address
     */
    public Admin(int userId, String userName, String password, String email, String phoneNumber, String address) {
        super(userId, userName, password, email, phoneNumber, address, "ADMIN");
    }
}
