package workout;

/**
 * Represents a workout class in the Gym Management System.
 * Stores information such as class type, description, and assigned trainer.
 */
public class WorkoutClass {
    private int workoutClassId;
    private String workoutClassType;
    private String workoutClassDescription;
    private int trainerId;

    /**
     * Default constructor for WorkoutClass.
     */
    public WorkoutClass() {}

    /**
     * Constructs a WorkoutClass with all fields specified.
     *
     * @param workoutClassId          The unique identifier for the workout class
     * @param workoutClassType        The type of workout class (e.g., Yoga, HIIT)
     * @param workoutClassDescription The description of the workout class
     * @param trainerId               The trainer's unique identifier
     */
    public WorkoutClass(int workoutClassId, String workoutClassType, String workoutClassDescription, int trainerId) {
        this.workoutClassId = workoutClassId;
        this.workoutClassType = workoutClassType;
        this.workoutClassDescription = workoutClassDescription;
        this.trainerId = trainerId;
    }

    // Getters and Setters

    /**
     * Gets the unique identifier for the workout class.
     * @return the workoutClassId
     */
    public int getWorkoutClassId() {
        return workoutClassId;
    }

    /**
     * Sets the unique identifier for the workout class.
     * @param workoutClassId the workoutClassId to set
     */
    public void setWorkoutClassId(int workoutClassId) {
        this.workoutClassId = workoutClassId;
    }

    /**
     * Gets the type of workout class.
     * @return the workoutClassType
     */
    public String getWorkoutClassType() {
        return workoutClassType;
    }

    /**
     * Sets the type of workout class.
     * @param workoutClassType the workoutClassType to set
     */
    public void setWorkoutClassType(String workoutClassType) {
        this.workoutClassType = workoutClassType;
    }

    /**
     * Gets the description of the workout class.
     * @return the workoutClassDescription
     */
    public String getWorkoutClassDescription() {
        return workoutClassDescription;
    }

    /**
     * Sets the description of the workout class.
     * @param workoutClassDescription the workoutClassDescription to set
     */
    public void setWorkoutClassDescription(String workoutClassDescription) {
        this.workoutClassDescription = workoutClassDescription;
    }

    /**
     * Gets the trainer's unique identifier assigned to this workout class.
     * @return the trainerId
     */
    public int getTrainerId() {
        return trainerId;
    }

    /**
     * Sets the trainer's unique identifier for this workout class.
     * @param trainerId the trainerId to set
     */
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Returns a string representation of the WorkoutClass object.
     * @return a formatted string with workout class details
     */
    @Override
    public String toString() {
    
        return String.format("""
                ╭─ Workout Class #%d ─────────────────
                │ Type: %s
                │ Description: %s
                │ Trainer ID: %d
                ╰───────────────────────────────────""",
                workoutClassId,
                workoutClassType,
                workoutClassDescription,
                trainerId);
    }
}
