package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Review {
    private int reviewId;
    private int clientId;
    private int trainerId;
    private int rating;
    private String comment;

    public Review(int reviewId, int clientId, int trainerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.clientId = clientId;
        this.trainerId = trainerId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getRating() {
        return rating;
    }

    public boolean setRating(int rating) {
        if (rating < 1 || rating > 5) {
            System.err.println("Invalid rating: Rating must be between 1 and 5.");
            return false;
        }
        this.rating = rating;
        return true;
    }

    public String getComment() {
        return comment;
    }

    public boolean setComment(String comment) {
        if (comment == null || comment.length() > 50) {
            System.err.println("Invalid comment: Must not be null and must not exceed 50 characters.");
            return false;
        }
        this.comment = comment;
        return true;
    }

    public boolean validate() {
        if (clientId <= 0 || trainerId <= 0) {
            System.err.println("Client ID and Trainer ID cannot be null.");
            return false;
        }
        if (rating < 1 || rating > 5) {
            System.err.println("Invalid rating: Rating must be between 1 and 5.");
            return false;
        }
        if (comment == null || comment.length() > 50) {
            System.err.println("Invalid comment: Must not be null and must not exceed 50 characters.");
            return false;
        }
        return true;
    }
    
    //Review Implementation - For CLIENT ONLY
    //placeholder values used for the db connection (Not yet implemented)
    public boolean addReview(Connection dbConnection) {
        if (dbConnection == null) {
            System.err.println("No Database connection.");
            return false;
        }

        if (!validate()) {
            System.err.println("Review validation failed.");
            return false;
        }

        String sql = "INSERT INTO Review (review_ID, client_ID, trainer_ID, Rating, Comment) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setObject(1, reviewId); 
            statement.setObject(2, clientId); 
            statement.setObject(3, trainerId); 
            statement.setInt(4, rating); 
            statement.setString(5, comment);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Could not save the review to the database: " + e.getMessage());
            return false;
        }
    }
}
