package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import Model.*;

public class DBConnection{
    private static Connection conn;
	
        @SuppressWarnings("CallToPrintStackTrace")
	public DBConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/WorkoutApplication?user=root&password=Rachel99@");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
     // Method to return the database connection
        public static Connection getConnection() {
            return conn;
        }

    public boolean registerUser() {
        return true;
    }

    public void closeConnection() {
        if (conn != null) {
        try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }


    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, name, password, email, profile_image, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);
                            user.setUserId(userId);
                        }
                    }
                return true;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Username already exists.");
            } else {
                e.printStackTrace();
            }
        }       
        return false;
    }
    
    public int authenticateUser(String username, String password) {
        String query = "SELECT user_id, password FROM users WHERE username = ?";
        int userId = -1;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    userId = rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return userId;
    }

    //getClient
    public String getClient(int userId) {
        String query = "SELECT c.client_ID, c.goals, c.workout_plan, u.username, u.email " +
                       "FROM Clients c " +
                       "JOIN Users u ON c.user_ID = u.user_ID " +
                       "WHERE u.user_ID = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String clientId = resultSet.getString("client_ID");
                    String goals = resultSet.getString("goals");
                    String workoutPlan = resultSet.getString("workout_plan");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");

                    return "Client ID: " + clientId + ", Name: " + username + ", Email: " + email +
                           ", Goals: " + goals + ", Workout Plan: " + workoutPlan;
                } else {
                    return "Client not found";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching client details";
        }
    }
    //getStreaks
 // Method to get streak details by clientId
    public String getStreak(int clientId) {
        String query = "SELECT s.streak_ID, s.current_streak, s.longest_streak, s.last_checkin, c.client_ID " +
                       "FROM Streaks s " +
                       "JOIN Clients c ON s.client_ID = c.client_ID " +
                       "WHERE c.client_ID = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, clientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String streakId = resultSet.getString("streak_ID");
                    int currentStreak = resultSet.getInt("current_streak");
                    int longestStreak = resultSet.getInt("longest_streak");
                    Timestamp lastCheckin = resultSet.getTimestamp("last_checkin");

                    return "Streak ID: " + streakId + ", Current Streak: " + currentStreak + 
                           ", Longest Streak: " + longestStreak + ", Last Check-in: " + lastCheckin;
                } else {
                    return "Streak not found for client ID: " + clientId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching streak details";
        }
    }
    
    //getWorkoutplan

    public static List<Review> fetchTrainerReviews(int trainerId) {
        String query = "SELECT reviewId, clientId, trainerId, rating, comment FROM reviews WHERE trainerId = ?";
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, trainerId);  // Set trainerId as an int in the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int reviewId = resultSet.getInt("reviewId");  // Get reviewId as an int
                    int clientId = resultSet.getInt("clientId");  // Get clientId as an int
                    int rating = resultSet.getInt("rating");
                    String comment = resultSet.getString("comment");

                    Review review = new Review(reviewId, clientId, trainerId, rating, comment);  // Pass int values to the constructor
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    
 // Method to get trainer details by userId
    public String getTrainer(int userId) {
        String query = "SELECT t.trainer_ID, t.trainer_contact, t.services, t.workout_plan, u.username, u.email " +
                       "FROM Trainers t " +
                       "JOIN Users u ON t.user_ID = u.user_ID " +
                       "WHERE u.user_ID = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set the userId in the prepared statement
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch trainer details from result set
                    String trainerId = resultSet.getString("trainer_ID");
                    String trainerContact = resultSet.getString("trainer_contact");
                    String services = resultSet.getString("services");
                    String workoutPlan = resultSet.getString("workout_plan");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");

                    // Return formatted trainer details
                    return "Trainer ID: " + trainerId + ", Name: " + username + ", Email: " + email +
                           ", Contact: " + trainerContact + ", Services: " + services + ", Workout Plan: " + workoutPlan;
                } else {
                    return "Trainer not found";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching trainer details";
        }
    }

    //Review Implementation - For CLIENT ONLY
    //Must create a review object before calling this
    public boolean addReview(Review review) {
        String sql = "INSERT INTO Review (client_ID, trainer_ID, Rating, Comment) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, review.getClientId()); 
            statement.setObject(2, review.getTrainerId()); 
            statement.setInt(3, review.getRating()); 
            statement.setString(4, review.getComment());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        review.setReviewId(userId);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Could not save the review to the database: " + e.getMessage());
        }
        return false;
    }
}