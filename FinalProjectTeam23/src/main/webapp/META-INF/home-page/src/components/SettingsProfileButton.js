import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; 
import defaultImage from '../assets/no-pfp.webp'; 

const SettingsProfileButton = ({ profilePicture }) => {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); 

  const fetchUserProfilePicture = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return null;
    }

    const url = `http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/GetProfilePicture?userId=${userId}`;
    
    fetch(url)
      .then((response) => {
        if (response.ok) {
          return response.blob();
        } else {
          // If there's an error fetching the image, set to false to stop loading.
          setLoading(false);
        }
      })
      .then((imageBlob) => {
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchUserProfilePicture();
  }, []);

  return (
    <button
      onClick={() => navigate("/dashboard")} // Navigate to dashboard on click
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
      }}
    >
      <div
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {loading ? (
          <div
            className="profile-image-placeholder"
            style={{
              width: "100%",
              height: "100%",
              backgroundColor: '#ccc', // Grey background while loading
            }}
          ></div>
        ) : (
          <img
            src={profilePicture || defaultImage} // Fallback to defaultImage if no profilePicture is provided
            alt="Profile"
            style={{
              width: "100%",
              height: "100%",
              objectFit: "cover",
            }}
          />
        )}
      </div>
    </button>
  );
};

export default SettingsProfileButton;
