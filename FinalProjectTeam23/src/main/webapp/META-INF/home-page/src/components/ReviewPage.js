// Import necessary libraries
import React, { useState } from "react";
import StarRating from "./StarRating";
import "./ReviewPage.css";

const ReviewPage = () => {
  const [reviews, setReviews] = useState([]);

  // Function to add a new review
  const addReview = (review) => {
    setReviews([...reviews, review]);
  };

  return (
    <div className="review-page">
      <h2 className="review-page-title">User Reviews</h2>
      <ReviewForm addReview={addReview} />
      <ReviewList reviews={reviews} />
    </div>
  );
};

const ReviewForm = ({ addReview }) => {
  const [review, setReview] = useState({
    userName: "",
    rating: 0,
    comment: "",
    reviewedUser: "",
  });

  // Handle form input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setReview({
      ...review,
      [name]: value,
    });
  };

  // Handle rating changes
  const handleRatingChange = (rating) => {
    setReview({
      ...review,
      rating,
    });
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (
      review.userName &&
      review.rating > 0 &&
      review.comment &&
      review.reviewedUser
    ) {
      addReview(review);
      setReview({ userName: "", rating: 0, comment: "", reviewedUser: "" });
    }
  };

  const [rating, setRating] = useState(0);

  return (
    <form onSubmit={handleSubmit} className="review-form">
      <div className="form-group">
        <label>Reviewed User:</label>
        <input
          type="text"
          name="reviewedUser"
          value={review.reviewedUser}
          onChange={handleChange}
          placeholder="Enter the user's name"
          required
          className="input-field"
        />
      </div>
      <div className="form-group">
        <label>Your Name:</label>
        <input
          type="text"
          name="userName"
          value={review.userName}
          onChange={handleChange}
          placeholder="Enter your name"
          required
          className="input-field"
        />
      </div>
      <div className="form-group">
        <StarRating rating={rating} setRating={setRating} />
      </div>
      <div className="form-group">
        <label>Comment (max 200 characters):</label>
        <textarea
          name="comment"
          value={review.comment}
          onChange={handleChange}
          maxLength="200"
          placeholder="Enter your review (max 200 characters)"
          required
          className="input-field"
        />
      </div>
      <button type="submit" className="submit-button">
        Add Review
      </button>
    </form>
  );
};

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

export default ReviewPage;
