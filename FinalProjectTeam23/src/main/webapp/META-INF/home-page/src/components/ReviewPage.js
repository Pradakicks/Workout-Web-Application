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
      <h2 className="review-page-title">Add Review</h2>
      <ReviewForm addReview={addReview} />
      {/* <ReviewList reviews={reviews} /> */}
    </div>
  );
};

const ReviewForm = ({ addReview }) => {
  const [review, setReview] = useState({
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
      review.rating > 0 &&
      review.comment &&
      review.reviewedUser
    ) {
      addReview(review);
      setReview({ rating: 0, comment: "", reviewedUser: "" });
    }
  };

  const [rating, setRating] = useState(0);

  return (
    <form onSubmit={handleSubmit} className="review-form">
      <div className="form-group">
        <label>Trainer:</label>
        <input
          type="text"
          name="reviewedUser"
          value={review.reviewedUser}
          onChange={handleChange}
          placeholder="Enter the trainer's name for review"
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



export default ReviewPage;
