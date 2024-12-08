package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
    
        public void closeConnection() {
            if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    
    
        public static boolean registerUser(User user) {
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
        
        public static int authenticateUser(String username, String password) {
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
        //AddStreaks
        //GetStreaks
        //AddWorkoutplan
        //getWorkoutplan
    
        public static Trainer getTrainer(int userId) {
            String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, t.trainer_ID, t.trainer_Contact, t.services, t.trainer_Title "
                            + "FROM Users u "
                            + "JOIN Trainers t ON u.user_id = t.user_id "
                            + "WHERE u.user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Trainer(
                rs.getInt("trainer_ID"),
                userId,
                rs.getString("username"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("profile_image"),
                rs.getString("role"),
                rs.getString("trainer_Contact"),
                rs.getString("services"),
                rs.getString("trainer_Title")
            );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Trainer> getTrainers() {
        String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, t.trainer_ID, t.trainer_Contact, t.services, t.trainer_Title "
                        + "FROM Users u "
                        + "JOIN Trainers t ON u.user_id = t.user_id";
        List<Trainer> trainers = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trainers.add(new Trainer(
                    rs.getInt("trainer_ID"),
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("profile_image"),
                    rs.getString("role"),
                    rs.getString("trainer_Contact"),
                    rs.getString("services"),
                    rs.getString("trainer_Title")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    public static Client getClient(int userId) {
        String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, c.client_ID "
                     + "FROM Users u "
                     + "JOIN Clients c ON u.user_id = c.user_id "
                     + "WHERE u.user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getInt("client_ID"),
                    userId,
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("profile_image"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Streak getStreaks(int clientId) {
        String query = "SELECT streak_ID, client_ID, current_streak, longest_streak, last_checkin "
                     + "FROM Streaks WHERE client_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Streak(
                    rs.getInt("streak_ID"),
                    clientId,
                    rs.getInt("current_streak"),
                    rs.getInt("longest_streak"),
                    rs.getTimestamp("last_checkin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    //Review Implementation - For CLIENT ONLY
    //Must create a review object before calling this
    public static boolean addReview(Review review) {
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

    public static boolean addWorkoutPlan(int clientId, String planDetails) {
        String query = "INSERT INTO Workout_Plan (client_ID, plan_details) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            stmt.setString(2, planDetails);  //planDetails is a JSON string
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getWorkoutPlan(int clientId) {
        String query = "SELECT plan_details FROM Workout_Plan WHERE client_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("plan_details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
