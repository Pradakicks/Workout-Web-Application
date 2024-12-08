import React, { useState, useEffect } from 'react';
import profileIcon from "../assets/no-pfp.webp"; // Default profile image
import SettingsProfileButton from './SettingsProfileButton'; 

const EditPfp = () => {
  const [userData, setUserData] = useState({
    name: '',
    profilePicture: profileIcon, // Default profile picture
  });
  const [loading, setLoading] = useState(true); // State to track if profile picture is loading

  // Fetch user profile on component mount
  useEffect(() => {
    fetchUserProfile();
  }, []);

  // Fetch the user's profile using GET
  const fetchUserProfile = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const userProfileUrl = `http://localhost:8080/Workout-Web-Application/GetUserProfile?userId=${userId}`;
    
    // Fetch user profile information
    fetch(userProfileUrl)
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          alert(data.error); // Handle error from the API
        } else {
          // Now that we have the username, fetch the profile picture
          const profilePictureUrl = `http://localhost:8080/Workout-Web-Application/GetProfilePicture?userId=${userId}`;
          
          // Set user data and profile picture URL (use default if no profile picture URL returned)
          setUserData({
            name: data.username,
            profilePicture: profilePictureUrl && profilePictureUrl !== '' ? profilePictureUrl : profileIcon, // Fallback to default profile picture
          });
          setLoading(false); // Set loading to false when user data is fetched
        }
      })
      .catch((err) => {
        //alert('Failed to load user profile: ' + err.message);
      });
  };

  // Handle profile picture upload
  const handleImageUpload = (e) => {
    e.preventDefault();
    const file = e.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("profilePicture", file);
      const userId = localStorage.getItem('userId');
      formData.append("userId", userId); // Assuming userData contains the userId

      fetch("http://localhost:8080/Workout-Web-Application/UploadProfilePicture", {
        method: "POST",
        body: formData, // Sending form data with the image
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.success) {
            // Update the UI with the new profile picture URL
            setUserData((prevData) => ({
              ...prevData,
              profilePicture: URL.createObjectURL(file), 
            }));
          } else {
            alert("Failed to update profile picture.");
          }
        })
        .catch((err) => {
          alert("Error updating profile picture: " + err.message);
        });
    }
  };

  return (
    <div className="edit-pfp-container">
      {/* Profile Picture */}
      <div>
        {loading ? (
          <img
            src={profileIcon} // Show the default profile icon while loading
            alt="profileIcon"
            className="profile-image"
          />
        ) : (
          <img
            src={userData.profilePicture} // Show fetched profile picture once available
            alt="profileIcon"
            className="profile-image"
          />
        )}
      </div>

      {/* Username */}
      <div className="username">
        {loading ? (
          <div className="username-placeholder" style={{ backgroundColor: 'white' }}></div> // Placeholder for username
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

      {/* Profile Button */}
      <SettingsProfileButton profilePicture={userData.profilePicture} /> {/* Pass the updated profile picture */}
    </div>
  );
};

export default EditPfp;