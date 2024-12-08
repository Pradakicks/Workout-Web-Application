-- Insert Trainer 1 User Data
INSERT INTO Users (username, name, password, email, profile_image, role)
VALUES ('trainer1', 'John Doe', 'password123', 'johndoe@example.com', 'profile1.jpg', 'trainer');

-- Insert Trainer 2 User Data
INSERT INTO Users (username, name, password, email, profile_image, role)
VALUES ('trainer2', 'Jane Smith', 'password123', 'janesmith@example.com', 'profile2.jpg', 'trainer');

-- Insert Trainer 3 User Data
INSERT INTO Users (username, name, password, email, profile_image, role)
VALUES ('trainer3', 'Mike Johnson', 'password123', 'mikejohnson@example.com', 'profile3.jpg', 'trainer');

-- Insert Trainer 4 User Data
INSERT INTO Users (username, name, password, email, profile_image, role)
VALUES ('trainer4', 'Sarah Lee', 'password123', 'sarahlee@example.com', 'profile4.jpg', 'trainer');

-- Get user_id for each trainer
SELECT user_id FROM Users WHERE username = 'trainer1';
SELECT user_id FROM Users WHERE username = 'trainer2';
SELECT user_id FROM Users WHERE username = 'trainer3';
SELECT user_id FROM Users WHERE username = 'trainer4';

-- Insert Trainer 1 Data
INSERT INTO Trainers (user_ID, trainer_Title, trainer_Contact, services)
VALUES (1, 'Certified Personal Trainer', '555-1234', 'Personal Training, Nutrition Coaching');

-- Insert Trainer 2 Data
INSERT INTO Trainers (user_ID, trainer_Title, trainer_Contact, services)
VALUES (2, 'Strength Coach', '555-5678', 'Group Classes, Strength Training');

-- Insert Trainer 3 Data
INSERT INTO Trainers (user_ID, trainer_Title, trainer_Contact, services)
VALUES (3, 'Yoga Instructor', '555-9876', 'Yoga, Meditation');

-- Insert Trainer 4 Data
INSERT INTO Trainers (user_ID, trainer_Title, trainer_Contact, services)
VALUES (4, 'Fitness Coach', '555-6543', 'HIIT, Weight Loss Coaching');

SELECT * FROM Users;
SELECT * FROM Trainers;