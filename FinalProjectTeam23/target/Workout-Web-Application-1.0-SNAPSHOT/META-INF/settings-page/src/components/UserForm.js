import React, { useState, useEffect } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';

const UserForm = () => {
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    password: '',
    profilePicture: '', 
    newPassword: '', 
  });

  const [passwordVisible, setPasswordVisible] = useState(false);

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = () => {
    localStorage.setItem('userId', '1');
    let userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/settings/GetUserProfile?userId=${userId}`;
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          alert(data.error);
        } else {
          setUserData({
            name: data.username,
            email: data.email,
            password: data.password, 
            profilePicture: data.profilePicture || '', 
            newPassword: '', 
          });
        }
      })
      .catch((err) => {
        alert('Failed to load user profile: ' + err.message);
      });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const [errorMessage, setErrorMessage] = useState('');

  const handleSave = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      setErrorMessage('User not logged in.');
      return;
    }

    const { name, email, password, newPassword } = userData;

    if (!email || !name) {
      setErrorMessage('Username and email are required!');
      return;
    }

	if (newPassword && !window.confirm('Are you sure you want to update your password?')) {
	  return; 
	}
	
    const url = `http://localhost:8080/settings/UpdateUserProfile?userId=${userId}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(newPassword || password)}&username=${encodeURIComponent(name)}`;

    fetch(url)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP Error: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        if (data.error) {
          setErrorMessage(data.error);
        } else {
          setErrorMessage('');
          //alert('User profile updated successfully!');
          fetchUserProfile();
		  window.location.reload(); 
        }
      })
      .catch((err) => {
        console.error('Request failed:', err);
        setErrorMessage('Failed to update user profile: ' + err.message);
      });
  };

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  return (
    <div className="form-container">
      <div className="form-field">
        <label>Username</label>
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
      <div className="form-field password-container">
        <label>Password</label>
        <input
          type={passwordVisible ? 'text' : 'password'}
          name="password"
          className="rounded-input"
          value={userData.password}
          readOnly 
        />
        <span onClick={togglePasswordVisibility} className="password-icon">
          {passwordVisible ? <FaEyeSlash /> : <FaEye />}
        </span>
      </div>
      <div className="form-field">
        <label>Update Password</label>
        <input
          type="text"
          name="newPassword"
          className="rounded-input"
          value={userData.newPassword}
          onChange={handleChange}
        />
      </div>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      <button className="save-btn" onClick={handleSave}>
        Save
      </button>
    </div>
  );
};

export default UserForm;