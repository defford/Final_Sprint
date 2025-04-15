package workout;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for workout class-related database operations.
 * Handles CRUD operations for workout classes in the Gym Management System.
 */
public class WorkoutClassDAO {
    private final Connection connection;

    /**
     * Constructs a WorkoutClassDAO and establishes a database connection.
     * @throws SQLException if a database access error occurs
     */
    public WorkoutClassDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Inserts a new workout class into the database.
     * @param workoutClass The WorkoutClass object to insert
     * @return The created WorkoutClass object with workoutClassId set, or null if creation failed
     * @throws SQLException if a database access error occurs
     */
    public WorkoutClass createWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO WorkoutClasses (workoutClassType, workoutClassDescription, trainerId, capacity, scheduleTime, duration) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING workoutClassId";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getWorkoutClassType());
            stmt.setString(2, workoutClass.getWorkoutClassDescription());
            stmt.setInt(3, workoutClass.getTrainerId());
            stmt.setInt(4, 20); // Default capacity
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Current time as default
            stmt.setInt(6, 60); // Default 60 minutes duration

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                workoutClass.setWorkoutClassId(rs.getInt("workoutClassId"));
                return workoutClass;
            }
        }
        return null;
    }

    /**
     * Retrieves a workout class by its unique workoutClassId.
     * @param workoutClassId The unique identifier for the workout class
     * @return The WorkoutClass object, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public WorkoutClass getWorkoutClassById(int workoutClassId) throws SQLException {
        String sql = "SELECT * FROM WorkoutClasses WHERE workoutClassId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, workoutClassId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createWorkoutClassFromResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Retrieves all workout classes assigned to a specific trainer.
     * @param trainerId The trainer's unique identifier
     * @return A list of WorkoutClass objects for the trainer
     * @throws SQLException if a database access error occurs
     */
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();
        String sql = "SELECT * FROM WorkoutClasses WHERE trainerId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                workoutClasses.add(createWorkoutClassFromResultSet(rs));
            }
        }
        return workoutClasses;
    }

    /**
     * Retrieves all workout classes from the database.
     * @return A list of all WorkoutClass objects
     * @throws SQLException if a database access error occurs
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();
        String sql = "SELECT * FROM WorkoutClasses";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                workoutClasses.add(createWorkoutClassFromResultSet(rs));
            }
        }
        return workoutClasses;
    }

    /**
     * Updates an existing workout class's details in the database.
     * @param workoutClass The WorkoutClass object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "UPDATE WorkoutClasses SET workoutClassType = ?, workoutClassDescription = ? " +
                    "WHERE workoutClassId = ? AND trainerId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getWorkoutClassType());
            stmt.setString(2, workoutClass.getWorkoutClassDescription());
            stmt.setInt(3, workoutClass.getWorkoutClassId());
            stmt.setInt(4, workoutClass.getTrainerId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a workout class from the database by its unique workoutClassId and trainerId.
     * @param workoutClassId The unique identifier for the workout class
     * @param trainerId The trainer's unique identifier
     * @return true if the workout class was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteWorkoutClass(int workoutClassId, int trainerId) throws SQLException {
        String sql = "DELETE FROM WorkoutClasses WHERE workoutClassId = ? AND trainerId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, workoutClassId);
            stmt.setInt(2, trainerId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Creates a WorkoutClass object from a ResultSet row.
     * @param rs The ResultSet positioned at the workout class row
     * @return The WorkoutClass object
     * @throws SQLException if a database access error occurs
     */
    private WorkoutClass createWorkoutClassFromResultSet(ResultSet rs) throws SQLException {
        WorkoutClass workoutClass = new WorkoutClass();
        workoutClass.setWorkoutClassId(rs.getInt("workoutClassId"));
        workoutClass.setWorkoutClassType(rs.getString("workoutClassType"));
        workoutClass.setWorkoutClassDescription(rs.getString("workoutClassDescription"));
        workoutClass.setTrainerId(rs.getInt("trainerId"));
        return workoutClass;
    }
}
