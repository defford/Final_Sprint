package models;

import user.User;

/**
 * Represents a member user in the Gym Management System.
 * Inherits from the User class and sets the user role to "MEMBER".
 */
public class Member extends User {
    /**
     * Default constructor for Member. Sets the user role to "MEMBER".
     */
    public Member() {
        super();
        setUserRole("MEMBER");
    }

    /**
     * Constructs a Member with all user fields specified.
     *
     * @param userId      The unique identifier for the user
     * @param userName    The username
     * @param password    The user's password
     * @param email       The user's email address
     * @param phoneNumber The user's phone number
     * @param address     The user's address
     */
    public Member(int userId, String userName, String password, String email, String phoneNumber, String address) {
        super(userId, userName, password, email, phoneNumber, address, "MEMBER");
    }
}
