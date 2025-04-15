package workout;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for business logic related to workout classes in the Gym Management System.
 * Handles creation, retrieval, update, and deletion of workout classes.
 */
public class WorkoutClassService {
    private final WorkoutClassDAO workoutClassDAO;

    /**
     * Constructs a WorkoutClassService and initializes the WorkoutClassDAO.
     * @throws SQLException if a database access error occurs
     */
    public WorkoutClassService() throws SQLException {
        this.workoutClassDAO = new WorkoutClassDAO();
    }

    /**
     * Creates a new workout class with the specified details.
     * @param type        The type of workout class (e.g., Yoga, HIIT)
     * @param description The description of the workout class
     * @param trainerId   The trainer's unique identifier
     * @return The created WorkoutClass object
     * @throws SQLException if a database access error occurs
     */
    public WorkoutClass createWorkoutClass(String type, String description, int trainerId) throws SQLException {
        WorkoutClass workoutClass = new WorkoutClass();
        workoutClass.setWorkoutClassType(type);
        workoutClass.setWorkoutClassDescription(description);
        workoutClass.setTrainerId(trainerId);

        return workoutClassDAO.createWorkoutClass(workoutClass);
    }

    /**
     * Retrieves a workout class by its unique workoutClassId.
     * @param workoutClassId The unique identifier for the workout class
     * @return The WorkoutClass object
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if the workout class is not found
     */
    public WorkoutClass getWorkoutClassById(int workoutClassId) throws SQLException {
        WorkoutClass workoutClass = workoutClassDAO.getWorkoutClassById(workoutClassId);
        if (workoutClass == null) {
            throw new IllegalArgumentException("Workout class not found");
        }
        return workoutClass;
    }

    /**
     * Retrieves all workout classes assigned to a specific trainer.
     * @param trainerId The trainer's unique identifier
     * @return A list of WorkoutClass objects for the trainer
     * @throws SQLException if a database access error occurs
     */
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        return workoutClassDAO.getWorkoutClassesByTrainerId(trainerId);
    }

    /**
     * Retrieves all workout classes from the database.
     * @return A list of all WorkoutClass objects
     * @throws SQLException if a database access error occurs
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutClassDAO.getAllWorkoutClasses();
    }

    /**
     * Updates an existing workout class's details.
     * Verifies that the class exists and belongs to the trainer.
     * @param workoutClass The WorkoutClass object with updated details
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if unauthorized to update
     */
    public boolean updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        // Verify that the workout class exists and belongs to the trainer
        WorkoutClass existingClass = workoutClassDAO.getWorkoutClassById(workoutClass.getWorkoutClassId());
        if (existingClass == null || existingClass.getTrainerId() != workoutClass.getTrainerId()) {
            throw new IllegalArgumentException("Unauthorized to update this workout class");
        }
        return workoutClassDAO.updateWorkoutClass(workoutClass);
    }

    /**
     * Deletes a workout class by its unique workoutClassId and trainerId.
     * Verifies that the class exists and belongs to the trainer.
     * @param workoutClassId The unique identifier for the workout class
     * @param trainerId      The trainer's unique identifier
     * @return true if the workout class was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if unauthorized to delete
     */
    public boolean deleteWorkoutClass(int workoutClassId, int trainerId) throws SQLException {
        // Verify that the workout class exists and belongs to the trainer
        WorkoutClass existingClass = workoutClassDAO.getWorkoutClassById(workoutClassId);
        if (existingClass == null || existingClass.getTrainerId() != trainerId) {
            throw new IllegalArgumentException("Unauthorized to delete this workout class");
        }
        return workoutClassDAO.deleteWorkoutClass(workoutClassId, trainerId);
    }
}
