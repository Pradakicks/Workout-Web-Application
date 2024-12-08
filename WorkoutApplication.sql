DROP DATABASE IF EXISTS WorkoutApplication;
CREATE DATABASE WorkoutApplication;
USE WorkoutApplication;

CREATE TABLE users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    profile_image LONGBLOB,
    role ENUM('client', 'trainer') NOT NULL
);


CREATE TABLE Clients (
    client_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES Users(user_ID)
);

CREATE TABLE Trainers (
    trainer_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    trainer_Title VARCHAR(50),
    trainer_Contact VARCHAR(50),
    services TEXT,
    FOREIGN KEY (user_ID) REFERENCES Users(user_ID)
);

CREATE TABLE Workout_Plan (
    workout_plan_ID INT AUTO_INCREMENT PRIMARY KEY,
    client_ID INT NOT NULL,
    plan_details JSON,
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID)
);

CREATE TABLE Streaks (
    streak_ID INT AUTO_INCREMENT PRIMARY KEY,
    client_ID INT NOT NULL,
    current_streak INT,
    longest_streak INT,
    last_checkin TIMESTAMP,
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID)
);

CREATE TABLE Review (
    review_ID INT AUTO_INCREMENT PRIMARY KEY,
    client_ID INT NOT NULL,
    trainer_ID INT NOT NULL,
    Rating INT,
    Comment TEXT,
    FOREIGN KEY (client_ID) REFERENCES Clients(client_ID),
    FOREIGN KEY (trainer_ID) REFERENCES Trainers(trainer_ID)
);

CREATE TABLE Goals (
    goal_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    goal VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES Users(user_ID)
);