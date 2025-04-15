package membership;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for membership-related database operations.
 * Handles CRUD operations and revenue calculations for memberships in the Gym Management System.
 */
public class MembershipDAO {
    private final Connection connection;

    /**
     * Constructs a MembershipDAO and establishes a database connection.
     * @throws SQLException if a database access error occurs
     */
    public MembershipDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Inserts a new membership into the database.
     * @param membership The Membership object to insert
     * @return The created Membership object with membershipId set, or null if creation failed
     * @throws SQLException if a database access error occurs
     */
    public Membership createMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO Memberships (membershipType, membershipDescription, membershipCost, userId, startDate) " +
                    "VALUES (?, ?, ?, ?, CURRENT_DATE) RETURNING membershipId";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getUserId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                membership.setMembershipId(rs.getInt("membershipId"));
                return membership;
            }
        }
        return null;
    }

    /**
     * Retrieves a membership by its unique membershipId.
     * @param membershipId The unique identifier for the membership
     * @return The Membership object, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Membership getMembershipById(int membershipId) throws SQLException {
        String sql = "SELECT * FROM Memberships WHERE membershipId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createMembershipFromResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Retrieves all memberships belonging to a specific user.
     * @param userId The user's unique identifier
     * @return A list of Membership objects for the user
     * @throws SQLException if a database access error occurs
     */
    public List<Membership> getMembershipsByUserId(int userId) throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM Memberships WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(createMembershipFromResultSet(rs));
            }
        }
        return memberships;
    }

    /**
     * Retrieves all memberships from the database.
     * @return A list of all Membership objects
     * @throws SQLException if a database access error occurs
     */
    public List<Membership> getAllMemberships() throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM Memberships";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(createMembershipFromResultSet(rs));
            }
        }
        return memberships;
    }

    /**
     * Updates an existing membership's details in the database.
     * @param membership The Membership object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateMembership(Membership membership) throws SQLException {
        String sql = "UPDATE Memberships SET membershipType = ?, membershipDescription = ?, " +
                    "membershipCost = ? WHERE membershipId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMembershipId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a membership from the database by its unique membershipId.
     * @param membershipId The unique identifier for the membership
     * @return true if the membership was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteMembership(int membershipId) throws SQLException {
        String sql = "DELETE FROM Memberships WHERE membershipId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, membershipId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Calculates the total revenue from all memberships.
     * @return The total revenue as a double
     * @throws SQLException if a database access error occurs
     */
    public double getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(membershipCost) as total_revenue FROM Memberships";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total_revenue");
            }
        }
        return 0.0;
    }

    /**
     * Creates a Membership object from a ResultSet row.
     * @param rs The ResultSet positioned at the membership row
     * @return The Membership object
     * @throws SQLException if a database access error occurs
     */
    private Membership createMembershipFromResultSet(ResultSet rs) throws SQLException {
        Membership membership = new Membership();
        membership.setMembershipId(rs.getInt("membershipId"));
        membership.setMembershipType(rs.getString("membershipType"));
        membership.setMembershipDescription(rs.getString("membershipDescription"));
        membership.setMembershipCost(rs.getDouble("membershipCost"));
        membership.setUserId(rs.getInt("userId"));
        return membership;
    }
}
