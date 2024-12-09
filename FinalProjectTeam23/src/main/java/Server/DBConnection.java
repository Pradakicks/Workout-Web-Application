package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;


public class DBConnection{
    private DBConnection() {}
        
    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/WeatherData?user=root&password=Rachel99@");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return conn;
    }


    public static boolean registerUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO users (username, name, password, email, profile_image, role) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }  
        }   
        return false;
    }
    
    public static int authenticateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT user_id, password FROM users WHERE username = ?";
        int userId = -1;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    userId = rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return userId;
    }

    public static Trainer getTrainer(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, t.trainer_ID, t.trainer_Contact, t.services, t.trainer_Title "
                        + "FROM Users u "
                        + "JOIN Trainers t ON u.user_id = t.user_id "
                        + "WHERE u.user_id = ?";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
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
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return null;
    }

    public static List<Trainer> getTrainers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, t.trainer_ID, t.trainer_Contact, t.services, t.trainer_Title "
                        + "FROM Users u "
                        + "JOIN Trainers t ON u.user_id = t.user_id";
        List<Trainer> trainers = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
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
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return trainers;
    }

    public static Client getClient(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT u.username, u.name, u.password, u.email, u.profile_image, u.role, c.client_ID "
                     + "FROM Users u "
                     + "JOIN Clients c ON u.user_id = c.user_id "
                     + "WHERE u.user_id = ?";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
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
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return null;
    }

    public static Streak getStreaks(int clientId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT streak_ID, client_ID, current_streak, longest_streak, last_checkin "
                     + "FROM Streaks WHERE client_ID = ?";
        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            rs = stmt.executeQuery();
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
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return null;
    }
    
    

    //Review Implementation - For CLIENT ONLY
    //Must create a review object before calling this
    public static boolean addReview(Review review) {
        Connection conn = null;
        PreparedStatement statement = null;
        String sql = "INSERT INTO Review (client_ID, trainer_ID, Rating, Comment) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = DBConnection.getConnection();
            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return false;
    }

    public static boolean addWorkoutPlan(int clientId, String planDetails) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Workout_Plan (client_ID, plan_details) VALUES (?, ?)";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            stmt.setString(2, planDetails);  //planDetails is a JSON string
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return false;
    }

    public static String getWorkoutPlan(int clientId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT plan_details FROM Workout_Plan WHERE client_ID = ?";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("plan_details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        return null;
    }
};
