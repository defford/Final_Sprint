package user;

/**
 * Represents a user in the Gym Management System.
 * This is the base class for all user roles (Admin, Trainer, Member).
 * Stores user identity, credentials, contact information, and role type.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String userRole;

    /**
     * Default constructor for User.
     */
    public User() {}

    /**
     * Constructs a User with all fields specified.
     *
     * @param userId      The unique identifier for the user.
     * @param userName    The user's name or username.
     * @param password    The user's password (hashed).
     * @param email       The user's email address.
     * @param phoneNumber The user's phone number.
     * @param address     The user's address.
     * @param userRole    The user's role (e.g., ADMIN, TRAINER, MEMBER).
     */
    public User(int userId, String userName, String password, String email, String phoneNumber, String address, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }

    // Getters and Setters

    /**
     * Gets the user's unique identifier.
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's name or username.
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's name or username.
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password (hashed).
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password (hashed).
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email address.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number.
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's role (e.g., ADMIN, TRAINER, MEMBER).
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Sets the user's role (e.g., ADMIN, TRAINER, MEMBER).
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Returns a string representation of the User object.
     * @return a string with user details
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
