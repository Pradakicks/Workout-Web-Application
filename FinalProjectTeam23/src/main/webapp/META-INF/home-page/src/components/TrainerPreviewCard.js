import React from "react";
import "./ProfileCard.css"; // Import CSS for styling
import "./Dashboard.css"; // Import CSS for styling
import { useNavigate } from "react-router-dom"; // Import useNavigate for navigation

import profileImage from "../assets/example-trainer.png"; // Replace with your image path
import dumbbellIcon from "../assets/dumbbell.svg";
import online from "../assets/online.svg";
import offline from "../assets/offline.svg";

const TrainerPreviewCard = ({ trainer, status = online }) => {
  const navigate = useNavigate(); // Initialize navigation

  const handleSeeMoreClick = () => {
    // Navigate to the trainer's profile page
	console.log("fetching in preview trainer");
	navigate(`/trainer-profile/${trainer.trainerId}`);
  };

  return (
    <div className="trainer-preview-card">
      <div className="profile-image" style={{ marginRight: "1em" }}>
        <img
          src={profileImage}
          alt="Profile"
          style={{ width: 100, height: 100 }}
        />
      </div>
      <div style={{ flexDirection: "column" }}>
        <div style={{ display: "flex" }}>
          <h2 style={{ fontSize: "24px" }}>{trainer.name}</h2>
          <img
            src={status}
            alt="Status Icon"
            style={{ width: 12, marginLeft: "0.7em" }}
          />
        </div>
        <h3 style={{ fontSize: "14px", marginBottom: "0.5em" }}>
          {trainer.title}
        </h3>
        <button className="see-more-button" onClick={handleSeeMoreClick}>
          <img
            src={dumbbellIcon}
            alt="Dumbbell Icon"
            style={{ width: 30, marginRight: 10 }}
          />
          <span style={{ marginLeft: 10, marginRight: 5 }}>See More!</span>
        </button>
      </div>
    </div>
  );
};

export default TrainerPreviewCard;
