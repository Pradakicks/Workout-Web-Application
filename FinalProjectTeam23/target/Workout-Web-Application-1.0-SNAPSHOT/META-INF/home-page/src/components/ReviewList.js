// Import necessary libraries
import React, { useState } from "react";
import "./ReviewPage.css";

const ReviewList = ({ reviews }) => {
    return (
      <div className="review-list">
        <h3 className="review-list-title">Reviews:</h3>
        {reviews.length === 0 ? (
          <p className="no-reviews">No reviews yet.</p>
        ) : (
          reviews.map((review, index) => (
            <div key={index} className="review-item">
              <p>
                <strong>Reviewed User:</strong> {review.reviewedUser}
              </p>
              <p>
                <strong>Reviewer:</strong> {review.userName}
              </p>
              <p>
                <strong>Rating:</strong> {review.rating} / 5
              </p>
              <p>
                <strong>Comment:</strong> {review.comment}
              </p>
            </div>
          ))
        )}
      </div>
    );
  };

  export default ReviewList