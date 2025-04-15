package membership;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for business logic related to memberships in the Gym Management System.
 * Handles creation, retrieval, update, deletion, and revenue calculation for memberships.
 */
public class MembershipService {
    private final MembershipDAO membershipDAO;

    /**
     * Constructs a MembershipService and initializes the MembershipDAO.
     * @throws SQLException if a database access error occurs
     */
    public MembershipService() throws SQLException {
        this.membershipDAO = new MembershipDAO();
    }

    /**
     * Creates a new membership with the specified details.
     * @param type        The type of membership (e.g., Monthly, Annual)
     * @param description The description of the membership
     * @param cost        The cost of the membership
     * @param userId      The userId of the member who owns this membership
     * @return The created Membership object
     * @throws SQLException if a database access error occurs
     */
    public Membership createMembership(String type, String description, double cost, int userId) throws SQLException {
        Membership membership = new Membership();
        membership.setMembershipType(type);
        membership.setMembershipDescription(description);
        membership.setMembershipCost(cost); 
        membership.setUserId(userId);

        return membershipDAO.createMembership(membership);
    }

    /**
     * Retrieves a membership by its unique membershipId.
     * @param membershipId The unique identifier for the membership
     * @return The Membership object
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if the membership is not found
     */
    public Membership getMembershipById(int membershipId) throws SQLException {
        Membership membership = membershipDAO.getMembershipById(membershipId);
        if (membership == null) {
            throw new IllegalArgumentException("Membership not found");
        }
        return membership;
    }

    /**
     * Retrieves all memberships belonging to a specific user.
     * @param userId The user's unique identifier
     * @return A list of Membership objects for the user
     * @throws SQLException if a database access error occurs
     */
    public List<Membership> getMembershipsByUserId(int userId) throws SQLException {
        return membershipDAO.getMembershipsByUserId(userId);
    }

    /**
     * Retrieves all memberships from the database.
     * @return A list of all Membership objects
     * @throws SQLException if a database access error occurs
     */
    public List<Membership> getAllMemberships() throws SQLException {
        return membershipDAO.getAllMemberships();
    }

    /**
     * Updates an existing membership's details.
     * @param membership The Membership object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateMembership(Membership membership) throws SQLException {
        return membershipDAO.updateMembership(membership);
    }

    /**
     * Deletes a membership by its unique membershipId.
     * @param membershipId The unique identifier for the membership
     * @return true if the membership was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteMembership(int membershipId) throws SQLException {
        return membershipDAO.deleteMembership(membershipId);
    }

    /**
     * Calculates the total revenue from all memberships.
     * @return The total revenue as a double
     * @throws SQLException if a database access error occurs
     */
    public double calculateTotalRevenue() throws SQLException {
        return membershipDAO.getTotalRevenue();
    }
}
