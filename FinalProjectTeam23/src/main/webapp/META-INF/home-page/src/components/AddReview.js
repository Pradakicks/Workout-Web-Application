// src/components/AddReview.js

import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

const AddReview = () => {
  const { id } = useParams(); // Get trainer ID from URL
  const navigate = useNavigate();
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState("");
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Retrieve userId and role from localStorage
    const userId = localStorage.getItem("userId");
    const role = localStorage.getItem("role");

    console.log("User ID:", userId);
    console.log("Role:", role);

    if (!userId || role !== 'client') {
      setError("Only clients can add reviews.");
      return;
    }

    const clientId = parseInt(userId, 10);

    console.log("Client ID is:", clientId);

    const payload = {
      trainerId: parseInt(id, 10), // Trainer ID from URL params
      clientId: clientId, // Client ID from localStorage
      rating: parseInt(rating, 10), // Ensure rating is an integer
      comment: comment, // Comment input
    };

    console.log("Payload being sent:", payload);

    try {
      const response = await fetch(
        "http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/AddReview",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        }
      );

      const result = await response.json();

      console.log("Response from backend:", result);

      if (response.ok) {
        alert("Review added successfully!");
        navigate(`/trainer-profile/${id}`); // Redirect back to trainer profile
      } else {
        throw new Error(result.error || "Failed to add review");
      }
    } catch (error) {
      setError(error.message);
      console.error("Error submitting review:", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Add a Review</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            Rating (1-5):
            <input
              type="number"
              min="1"
              max="5"
              value={rating}
              onChange={(e) => setRating(e.target.value)}
              required
            />
          </label>
        </div>
        <div>
          <label>
            Comment:
            <textarea
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              required
              style={{ width: "100%", height: "100px" }}
            />
          </label>
        </div>
        <button type="submit" style={{ marginTop: "10px" }}>
          Submit Review
        </button>
        <button
          type="button"
          onClick={() => navigate(`/trainer-profile/${id}`)}
          style={{ marginLeft: "10px" }}
        >
          Cancel
        </button>
      </form>
    </div>
  );
};

export default AddReview;
