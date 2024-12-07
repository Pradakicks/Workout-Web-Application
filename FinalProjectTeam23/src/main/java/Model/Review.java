package Model;

import java.util.UUID;

public class Review {
    private UUID reviewId;
    private UUID clientId;
    private UUID trainerId;
    private int rating;
    private String comment;

    // Ctor new Review
    public Review(UUID clientId, UUID trainerId, int rating, String comment) {
        this.reviewId = UUID.randomUUID();
        this.clientId = clientId;
        this.trainerId = trainerId;
        this.rating = rating;
        this.comment = comment;
    }

    //Existing Review
    public Review(UUID reviewId, int rating, String comment) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
    }

     public UUID getReviewId() {
        return reviewId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
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

    // Validation for creating a new Review
    public boolean validateForCreation() {
        if (clientId == null || trainerId == null) {
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

    // Validation for updating an existing Review
    public boolean validateForUpdate() {
        if (reviewId == null) {
            System.err.println("Review ID cannot be null for an update.");
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
}
