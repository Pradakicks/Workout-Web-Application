package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

<<<<<<< HEAD
public class DBConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/WorkoutApplication?user=root&password=lynette14$"
        );
=======
import User.User;

public class DBConnection{
    private Connection conn;
	
        @SuppressWarnings("CallToPrintStackTrace")
	public DBConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/Server?user=root&password=Rachel99@");
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
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);
                            user.setId(userId);
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
    
    public User authenticateUser(String username, String password) {
        String query = "SELECT user_id, password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                int userId = rs.getInt("user_id");
                if (storedPassword.equals(password)) {
                    return new User(userId, username, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
>>>>>>> c2536feb6fed2363f07a31e0f543c5fa43f6e7d6
    }
}
