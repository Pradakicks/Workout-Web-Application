import React, { useState, useEffect } from 'react';
import profileIcon from "../assets/example-trainer.png"; 

const ProfileButton = ({ profilePicture }) => {
  const [loading, setLoading] = useState(true); 

  const fetchUserProfilePicture = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/settings/GetProfilePicture?userId=${userId}`;
    
    fetch(url)
      .then((response) => {
        if (response.ok) {
          return response.blob();
        } else {
          throw new Error("Failed to fetch profile picture");
        }
      })
      .then((imageBlob) => {
        const imageUrl = URL.createObjectURL(imageBlob) || profileIcon;
        setLoading(false);
      })
      .catch((err) => {
        alert('Failed to load profile picture: ' + err.message);
      });
  };

  useEffect(() => {
    fetchUserProfilePicture();
  }, []);

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
              backgroundColor: '#ccc', 
            }}
          ></div> 
        ) : (
          <img
            src={profilePicture} 
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

export default ProfileButton;


