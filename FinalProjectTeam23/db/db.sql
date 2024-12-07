DROP DATABASE IF EXISTS workout_app;
CREATE DATABASE workout_app;
USE workout_app;

-- Users Table
CREATE TABLE Users (
    user_ID CHAR(36) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('client', 'trainer') NOT NULL
);

-- Clients Table
CREATE TABLE Clients (
    client_ID CHAR(36) PRIMARY KEY,
    user_ID CHAR(36) NOT NULL,
    goals TEXT,
    workout_plan JSON,
    FOREIGN KEY (user_ID) REFERENCES Users(user_ID)
);

-- Workout Plans Table
CREATE TABLE Workout_Plan (
    workout_plan_ID CHAR(36) PRIMARY KEY,
    trainer_ID CHAR(36) NOT NULL,
    client_ID CHAR(36),
    plan_details JSON,
    FOREIGN KEY (trainer_ID) REFERENCES Users(user_ID),
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID)
);

-- Trainers Table
CREATE TABLE Trainers (
    trainer_ID CHAR(36) PRIMARY KEY,
    user_ID CHAR(36) NOT NULL,
    trainer_contact VARCHAR(50),
    services JSON,
    workout_plan CHAR(36),
    FOREIGN KEY (user_ID) REFERENCES Users(user_ID),
    FOREIGN KEY (workout_plan) REFERENCES Workout_Plan(workout_plan_ID)
);

-- Streaks Table
CREATE TABLE Streaks (
    streak_ID CHAR(36) PRIMARY KEY,
    client_ID CHAR(36) NOT NULL,
    current_streak INT CHECK (current_streak >= 0),
    longest_streak INT CHECK (longest_streak >= 0),
    last_checkin TIMESTAMP,
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID)
);

-- Reviews Table
CREATE TABLE Reviews (
    review_ID CHAR(36) PRIMARY KEY,
    client_ID CHAR(36) NOT NULL,
    trainer_ID CHAR(36) NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(50),
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID),
    FOREIGN KEY (trainer_ID) REFERENCES Trainers(trainer_ID)
);
