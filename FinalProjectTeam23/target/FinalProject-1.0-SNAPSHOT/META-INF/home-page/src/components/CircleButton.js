import React from "react";
import bellIcon from "../assets/bell.svg"; // Ensure the path is correct

const CircleButton = () => {
  return (
    <button
      style={{
        width: "40px", // Adjust the size
        height: "40px", // Make it a circle
        borderRadius: "50%", // Creates a circular shape
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#fff", // Button background
        border: "1px solid #ddd", // Optional border
        boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)", // Optional shadow
        cursor: "pointer",
      }}>
      <img
        src={bellIcon}
        alt="Notification Bell"
        style={{
          width: "20px", // Adjust the size of the image
          height: "20px",
          opacity: 0.8, // Slightly transparent image
        }}
      />
    </button>
  );
};

export default CircleButton;
