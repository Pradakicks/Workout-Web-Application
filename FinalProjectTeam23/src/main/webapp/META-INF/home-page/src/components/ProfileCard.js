import React from "react";
import "./ProfileCard.css"; // Import CSS for styling
import profileImage from "../assets/example-trainer.png"; // Replace with your image path
import dumbbellIcon from "../assets/dumbbell.svg";

const ProfileCard = ({ trainer }) => {
  return (
    <div className="profile-card">
      <div className="profile-image">
        <img
          src={profileImage}
          alt="Profile"
          style={{ width: 100, height: 150, marginBottom: 80 }}
        />
      </div>
      
      <div className="profile-info">
        <h2>{trainer.name}</h2> {/* Display trainer's name */}
        <h3>{trainer.trainerTitle}</h3> {/* Display trainer's title */}
        <h4>About</h4>
        <p>{trainer.services}</p> {/* Display trainer's services */}
        <button className="see-more-button" style={{ marginLeft: 70 }}>
          <img
            src={dumbbellIcon}
            alt="Dumbbell Icon"
            style={{ width: 30, marginRight: 10 }} // Adjust marginRight as needed
          />
          <span style={{ marginLeft: 10, marginRight: 5 }}>See More!</span>
        </button>
      </div>
    </div>
  );
};

export default ProfileCard;
