import React from "react";

const LogoutButton = () => {
  const handleLogout = () => {
    alert("You have been logged out.");
    // Redirect to home page and clear session as needed
  };

  return (
    <button onClick={handleLogout} className="logout-button">
      Logout
    </button>
  );
};

export default LogoutButton;
