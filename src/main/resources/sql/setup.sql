-- Create Users table
CREATE TABLE IF NOT EXISTS Users (
    userId SERIAL PRIMARY KEY,
    userName VARCHAR(50) UNIQUE NOT NULL,
    userPassword VARCHAR(255) NOT NULL,  -- For BCrypt hashed passwords
    userEmail VARCHAR(100) UNIQUE NOT NULL,
    userAddress VARCHAR(255) NOT NULL,
    userPhoneNumber VARCHAR(20) NOT NULL,
    userRole VARCHAR(10) NOT NULL CHECK (userRole IN ('ADMIN', 'TRAINER', 'MEMBER'))
);

-- Insert default admin user (password: admin123)
INSERT INTO Users (userName, userPassword, userEmail, userPhoneNumber, userAddress, userRole)
VALUES ('admin', '$2a$10$um1FBG4IObPrdeyFTJxXf.jbckbLPO8a2HJz5ht/cdBR4b1wawM/C', 'admin@gym.com', '555-0000', '123 Admin St', 'ADMIN')
ON CONFLICT (userName) DO NOTHING;

-- Create Memberships table
CREATE TABLE IF NOT EXISTS Memberships (
    membershipId SERIAL PRIMARY KEY,
    membershipType VARCHAR(50) NOT NULL,
    membershipDescription TEXT,
    membershipCost DECIMAL(10,2) NOT NULL,
    userId INTEGER NOT NULL,
    startDate DATE NOT NULL DEFAULT CURRENT_DATE,
    endDate DATE,
    FOREIGN KEY (userId) REFERENCES Users(userId) ON DELETE CASCADE,
    CHECK (endDate > startDate)
);

-- Create WorkoutClasses table
CREATE TABLE IF NOT EXISTS WorkoutClasses (
    workoutClassId SERIAL PRIMARY KEY,
    workoutClassType VARCHAR(50) NOT NULL,
    workoutClassDescription TEXT,
    trainerId INTEGER NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    scheduleTime TIMESTAMP NOT NULL,
    duration INTEGER NOT NULL CHECK (duration > 0), -- duration in minutes
    FOREIGN KEY (trainerId) REFERENCES Users(userId) ON DELETE CASCADE
);

-- Create ClassEnrollments table
CREATE TABLE IF NOT EXISTS ClassEnrollments (
    enrollmentId SERIAL PRIMARY KEY,
    classId INTEGER NOT NULL,
    memberId INTEGER NOT NULL,
    enrollmentDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (classId) REFERENCES WorkoutClasses(workoutClassId) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Users(userId) ON DELETE CASCADE
);