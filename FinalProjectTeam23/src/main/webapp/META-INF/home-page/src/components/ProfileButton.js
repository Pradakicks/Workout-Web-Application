import React from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate hook
import profileIcon from "../assets/example-trainer.png"; // Replace with the path to your image

const ProfileButton = ({ width = "40px", height = "40px" }) => {
  const navigate = useNavigate(); // Initialize navigate function

  const handleProfileClick = () => {
    navigate("/dashboard"); // Navigate to dashboard when clicked
  };

  return (
    <button
      onClick={handleProfileClick} // Add onClick handler
      style={{
        width: width, // Adjust the size
        height: height, // Make it a circle
        borderRadius: "50%", // Creates a circular shape
        padding: 0, // Remove padding to prevent image overflow
        overflow: "hidden", // Ensures the image stays within the circle
        border: "1px solid #ddd", // Optional border
        boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)", // Optional shadow
        cursor: "pointer",
      }}
    >
      <img
        src={profileIcon}
        alt="Profile"
        style={{
          width: "100%", // Fill the entire button width
          height: "100%", // Fill the entire button height
          objectFit: "cover", // Ensure the image covers the circle
        }}
      />
    </button>
  );
};

export default ProfileButton;

