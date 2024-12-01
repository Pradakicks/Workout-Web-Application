import React from "react";
import profileIcon from "../assets/example-trainer.png"; 

const ProfileButton = () => {
  return (
    <button
      style={{
        width: "40px", 
        height: "40px", 
        borderRadius: "50%",
        padding: 0, 
        overflow: "hidden", 
        border: "1px solid #ddd", 
        boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)", 
        cursor: "pointer",
        position: "absolute",
        top: "30px", 
        right: "30px", 
      }}>
      <img
        src={profileIcon}
        alt="Profile"
        style={{
          width: "100%", 
          height: "100%", 
          objectFit: "cover", 
        }}
      />
    </button>
  );
};

export default ProfileButton;
