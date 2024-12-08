import React from "react";

const LogoutButton = () => {
  const handleLogout = () => {
    //alert("You have been logged out.");
    localStorage.setItem('userId', '0');
    // Redirect to home page 
  };

  return (
    <button onClick={handleLogout} className="logout-button">
      Logout
    </button>
  );
};

export default LogoutButton;
