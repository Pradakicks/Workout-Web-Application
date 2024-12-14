import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // Access dynamic route params
import profileImage from "../assets/example-trainer.png";
import { useNavigate } from "react-router-dom";

const TrainerProfile = () => {
  const { id } = useParams(); // Retrieve the trainer ID from the route
  const navigate = useNavigate(); // For navigation
  const [trainer, setTrainer] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [isClientLoggedIn, setIsClientLoggedIn] = useState(false); // State to track client login

	
 console.log("Fetching");
  useEffect(() => {
	const userId = localStorage.getItem("userId");
	    const role = localStorage.getItem("role");
	    const loggedIn = userId !== null && role === "client";	   setIsClientLoggedIn(loggedIn);
	   
    fetch(`http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/Trainer/${id}`)
      .then((response) => response.json())
      .then((data) => {
        setTrainer(data);
        setReviews(data.reviews || []);
      })
      .catch((error) => console.error("Error fetching trainer profile:", error));
  }, [id]);

  if (!trainer) {
    return <p>Loading trainer profile...</p>;
  }
  
  // Handler for navigating back to the homepage
    const handleBackToHomepage = () => {
      navigate("/");
    };

    // Handler for adding a review
    const handleAddReview = () => {
      navigate(`/add-review/${id}`); // Example: Redirect to an Add Review page for the trainer
    };


  return (
    <div style={{ padding: "20px" }}>
      <div style={{ display: "flex", alignItems: "center", marginBottom: "20px" }}>
        <img
          src={profileImage}
          alt="Trainer"
          style={{ width: 150, height: 150, marginRight: "20px", borderRadius: "50%" }}
        />
        <div>
          <h1>{trainer.name}</h1>
          <h3>{trainer.title}</h3>
          <p>{trainer.contact}</p>
        </div>
      </div>
      <h2>Services</h2>
      <p>{trainer.services}</p>
      <h2>Reviews</h2>
      {reviews.length > 0 ? (
        reviews.map((review) => (
          <div key={review.id} style={{ marginBottom: "10px" }}>
            <strong>Rating:</strong> {review.rating}/5
            <p>{review.comment}</p>
          </div>
        ))
      ) : (
        <p>No reviews yet.</p>
      )}
	  
	  {/* Back to Homepage button */}
	        <button
	          onClick={handleBackToHomepage}
	          style={{
	            marginTop: "20px",
	            padding: "10px 20px",
	            backgroundColor: "#007BFF",
	            color: "#fff",
	            border: "none",
	            borderRadius: "5px",
	            cursor: "pointer",
	          }}
	        >
	          Back to Homepage
	        </button>

	        {/* Add Review button (only visible if the client is logged in) */}
	        {isClientLoggedIn && (
	          <button
	            onClick={handleAddReview}
	            style={{
	              marginTop: "20px",
	              marginLeft: "10px",
	              padding: "10px 20px",
	              backgroundColor: "#28A745",
	              color: "#fff",
	              border: "none",
	              borderRadius: "5px",
	              cursor: "pointer",
	            }}
	          >
	            Add Review
	          </button>
	        )}
	      </div>
	    );
	  };

export default TrainerProfile;
