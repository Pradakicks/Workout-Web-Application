import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*; //Using all files (SQL)

public class DBConnection {
    private Connection conn;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/WorkoutDB";
	private static final String DB_USERNAME = "your_username";
	private static final String DB_PASSWORD = "your_password";
	
	// Get database connection
	private static Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

    public DBConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection to the database
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/WeatherConditions",
                “SQLUsername”,
                “SQLPassword“
            );
        } Usernamexception e) {
            e.printStackTrace();
        }
    }

   
	/* SQL OPERATIONS */

	//Add a user to the database
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
	
	//login the user - verify password and password
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
    }

	//addReview for a user
	public static boolean addReview(Review review) {
	    String query = "INSERT INTO reviews (reviewId, clientId, trainerId, rating, comment) VALUES (?, ?, ?, ?, ?)";
	
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	
	        preparedStatement.setString(1, review.getReviewId().toString());
	        preparedStatement.setString(2, review.getClientId().toString());
	        preparedStatement.setString(3, review.getTrainerId().toString());
	        preparedStatement.setInt(4, review.getRating());
	        preparedStatement.setString(5, review.getComment());
	
	        int rowsInserted = preparedStatement.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	//fetch a specific trainers reviews
	public static List<Review> fetchTrainerReviews(UUID trainerId) {
	    String query = "SELECT reviewId, clientId, trainerId, rating, comment FROM reviews WHERE trainerId = ?";
	    List<Review> reviews = new ArrayList<>();

	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setString(1, trainerId.toString());
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                UUID reviewId = UUID.fromString(resultSet.getString("reviewId"));
	                UUID clientId = UUID.fromString(resultSet.getString("clientId"));
	                int rating = resultSet.getInt("rating");
	                String comment = resultSet.getString("comment");

	                Review review = new Review(clientId, trainerId, rating, comment);
	                reviews.add(review);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return reviews;
	}

		//fetch all users reviews
		public static List<Review> fetchUserReviews(UUID clientId) {
		    String query = "SELECT reviewId, clientId, trainerId, rating, comment FROM reviews WHERE clientId = ?";
		    List<Review> reviews = new ArrayList<>();
	
		    try (Connection connection = getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	
		        preparedStatement.setString(1, clientId.toString());
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		                UUID reviewId = UUID.fromString(resultSet.getString("reviewId"));
		                UUID trainerId = UUID.fromString(resultSet.getString("trainerId"));
		                int rating = resultSet.getInt("rating");
		                String comment = resultSet.getString("comment");
	
		                Review review = new Review(clientId, trainerId, rating, comment);
		                reviews.add(review);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	
		    return reviews;
		}

		//edit a review
		public static boolean editReview(UUID reviewId, int newRating, String newComment) {
		    String query = "UPDATE reviews SET rating = ?, comment = ? WHERE reviewId = ?";

		    try (Connection connection = getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

		        preparedStatement.setInt(1, newRating);
		        preparedStatement.setString(2, newComment);
		        preparedStatement.setString(3, reviewId.toString());

		        int rowsUpdated = preparedStatement.executeUpdate();
		        return rowsUpdated > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	//Close DB
	public void closeConnection() {
	        if (conn != null) {
	            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
}


