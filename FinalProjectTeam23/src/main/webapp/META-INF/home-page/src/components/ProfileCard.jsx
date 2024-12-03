import React from "react";
import "./ProfileCard.css"; // Import CSS for styling
import profileImage from "../assets/example-trainer.png"; // Replace with your image path
import dumbbellIcon from "../assets/dumbbell.svg";

const ProfileCard = () => {
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
        <h2>Jake Carter</h2>
        <h3>Strength & Conditioning Coach</h3>
        <h4>About</h4>
        <p>
          As a dedicated Strength & Conditioning Coach, I design workouts that
          are tailored to help you achieve peak physical performance. With years
          of experience and a deep understanding of exercise science, I focus on
          creating programs that are safe and effective.
        </p>
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
