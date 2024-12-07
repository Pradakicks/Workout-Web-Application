package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection{
    private Connection conn;
	
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

    //getTrainer
    //getClient
    //getStreaks
    //getWorkoutplan

    public String getTrainer(int userId){

        return "hi";
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
