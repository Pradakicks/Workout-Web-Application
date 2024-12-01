import React, { useState } from 'react';
import profileIcon from "../assets/example-trainer.png"; 

const EditPfp = () => {
  const [image, setImage] = useState(profileIcon);
  const [username] = useState('@JohnDoe123'); // set this dynamically later

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className="edit-pfp-container">
      {/* Profile Picture */}
      <div>
        <img
          src={image || 'https://via.placeholder.com/150'}
          alt="Profile"
          className="profile-image"
        />
      </div>

      {/* Username */}
      <div className="username">
        {username}
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
    </div>
  );
};

export default EditPfp;

