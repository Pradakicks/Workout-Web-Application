import React, { useState, useEffect } from 'react';
import profileIcon from "../assets/example-trainer.png";
import ProfileButton from './ProfileButton'; 

const EditPfp = () => {
  const [userData, setUserData] = useState({
    name: '',
    profilePicture: profileIcon, 
  });
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const userProfileUrl = `http://localhost:8080/GetUserProfile?userId=${userId}`;
    
    fetch(userProfileUrl)
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          alert(data.error);
        } else {
          const profilePictureUrl = `http://localhost:8080/GetProfilePicture?userId=${userId}`;
          
          setUserData({
            name: data.username,
            profilePicture: profilePictureUrl || profileIcon, 
          });
          setLoading(false); 
        }
      })
      .catch((err) => {
        //alert('Failed to load user profile: ' + err.message);
      });
  };

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("profilePicture", file);
      const userId = localStorage.getItem('userId');
      formData.append("userId", userId); 

      fetch("http://localhost:8080/UploadProfilePicture", {
        method: "POST",
        body: formData, 
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.success) {
            setUserData((prevData) => ({
              ...prevData,
              profilePicture: URL.createObjectURL(file), 
            }));
          } else {
            //alert("Failed to update profile picture.");
          }
        })
        .catch((err) => {
          //alert("Error updating profile picture: " + err.message);
        });
    }
  };

  return (
    <div className="edit-pfp-container">
      {/* Profile Picture */}
      <div>
        {loading ? (
          <div className="profile-image-placeholder" style={{width: 100, height: 100, backgroundColor: 'white' }}></div>
        ) : (
          <img
            src={userData.profilePicture}
            alt="Profile"
            className="profile-image"
          />
        )}
      </div>

      {/* Username */}
      <div className="username">
        {loading ? (
          <div className="username-placeholder" style={{ width: 100, height: 20, backgroundColor: 'white' }}></div>
        ) : (
          `@${userData.name}`
        )}
      </div>

      {/* Upload Button */}
      <div className="upload-button-container">
        <input
          type="file"
          onChange={handleImageUpload}
          style={{ display: 'none' }}
          id="upload-image"
        />
        <label htmlFor="upload-image" className="upload-button">
          Update Profile Picture
        </label>
      </div>
      <ProfileButton profilePicture={userData.profilePicture} /> 
    </div>
  );
};

export default EditPfp;



 
