import React, { useState } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';

const UserForm = () => {
  const [userData, setUserData] = useState({
    name: 'John Doe',
    email: 'john.doe@example.com',
    number: '1234567890',
    password: 'password123'
  });

  const [passwordVisible, setPasswordVisible] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleSave = () => {
    // Basic validation for form fields
    if (!userData.name || !userData.email || !userData.number || !userData.password) {
      alert('All fields are required!');
      return;
    }

    // Email validation
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailRegex.test(userData.email)) {
      alert('Please enter a valid email address!');
      return;
    }

    // Phone number validation (simple)
    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(userData.number)) {
      alert('Please enter a valid phone number!');
      return;
    }

    if (window.confirm('Are you sure you want to save the changes?')) {
      // Call API to update user info
      console.log('User data saved:', userData);
    }
  };

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible); 
  };

  return (
    <div className="form-container">
      <div className="form-field">
        <label>Name</label>
        <input
          type="text"
          name="name"
          className="rounded-input"
          value={userData.name}
          onChange={handleChange}
        />
      </div>
      <div className="form-field">
        <label>Email</label>
        <input
          type="email"
          name="email"
          className="rounded-input"
          value={userData.email}
          onChange={handleChange}
        />
      </div>
      <div className="form-field">
        <label>Phone Number</label>
        <input
          type="tel"
          name="number"
          className="rounded-input"
          value={userData.number}
          onChange={handleChange}
        />
      </div>
      <div className="form-field password-container">
        <label>Password</label>
        <input
          type={passwordVisible ? 'text' : 'password'} 
          name="password"
          className="rounded-input"
          value={userData.password}
          onChange={handleChange}
        />
        <span 
          onClick={togglePasswordVisibility}
          className="password-icon"
        >
          {passwordVisible ? <FaEyeSlash /> : <FaEye />} 
        </span>
      </div>
      <button className="save-btn" onClick={handleSave}>
        Save
      </button>
    </div>
  );
};

export default UserForm;
