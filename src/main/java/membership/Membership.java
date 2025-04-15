package membership;

/**
 * Represents a membership in the Gym Management System.
 * Stores membership details such as type, description, cost, and associated user.
 */
public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int userId;

    /**
     * Default constructor for Membership.
     */
    public Membership() {}

    /**
     * Constructs a Membership with all fields specified.
     *
     * @param membershipId          The unique identifier for the membership
     * @param membershipType        The type of membership (e.g., Monthly, Annual)
     * @param membershipDescription The description of the membership
     * @param membershipCost        The cost of the membership
     * @param userId                The userId of the member who owns this membership
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int userId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.userId = userId;
    }

    // Getters and Setters

    /**
     * Gets the unique identifier for the membership.
     * @return the membershipId
     */
    public int getMembershipId() {
        return membershipId;
    }

    /**
     * Sets the unique identifier for the membership.
     * @param membershipId the membershipId to set
     */
    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * Gets the type of membership.
     * @return the membershipType
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * Sets the type of membership.
     * @param membershipType the membershipType to set
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * Gets the description of the membership.
     * @return the membershipDescription
     */
    public String getMembershipDescription() {
        return membershipDescription;
    }

    /**
     * Sets the description of the membership.
     * @param membershipDescription the membershipDescription to set
     */
    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    /**
     * Gets the cost of the membership.
     * @return the membershipCost
     */
    public double getMembershipCost() {
        return membershipCost;
    }

    /**
     * Sets the cost of the membership.
     * @param membershipCost the membershipCost to set
     */
    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    /**
     * Gets the userId of the member who owns this membership.
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the userId of the member who owns this membership.
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns a string representation of the Membership object.
     * @return a string with membership details
     */
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", userId=" + userId +
                '}';
    }
}
