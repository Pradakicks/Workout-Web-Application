import React from "react";
import bellIcon from "../assets/bell.svg"; 

const SettingsCircleButton = () => {
  return (
    <button
      style={{
        width: "40px", 
        height: "40px", 
        borderRadius: "50%", 
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#fff", 
        border: "1px solid #ddd", 
        boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)", 
        cursor: "pointer",
        position: "absolute", 
        top: "30px", 
        right: "85px", 
      }}>
      <img
        src={bellIcon}
        alt="Notification Bell"
        style={{
          width: "20px", 
          height: "20px",
          opacity: 0.8,
        }}
      />
    </button>
  );
};

export default SettingsCircleButton;