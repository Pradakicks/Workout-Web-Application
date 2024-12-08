import React from "react";
import { useNavigate } from "react-router-dom"; 

const LogoutButton = () => {
  const navigate = useNavigate(); 

  const handleLogout = () => {
    localStorage.removeItem("userId"); 
    
    
    navigate("/"); // Navigate to home page ("/")
  };

  return (
    <button onClick={handleLogout} className="logout-button">
      Logout
    </button>
  );
};

export default LogoutButton;