import React, { useState, useEffect } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';

const UpdateUserForm = () => {
  const [userData, setUserData] = useState({
    name: 'Jane Doe',
	username: 'jdoe',
    email: 'Janedoe@gmail.com',
    password: 'Password1',
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

    const url = `http://localhost:8080/Workout-Web-Application/GetUserProfile?userId=${userId}`;
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          //alert(data.error);
        } else {
          setUserData({
            name: data.name,
			username: data.username,
            email: data.email,
            password: data.password, 
            profilePicture: data.profilePicture || '', 
            newPassword: '', 
          });
        }
      })
      .catch((err) => {
	  console.error('Request failed:', err);
	  //setErrorMessage('Failed to get user profile: ' + err.message);
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

  const handleSave = (e) => {
	e.preventDefault();
    const userId = localStorage.getItem('userId');
    if (!userId) {
      setErrorMessage('User not logged in.');
      return;
    }

    const { name, username, email, password, newPassword } = userData;

    if (!email || !name || !username || !password) {
      setErrorMessage('Username and email are required!');
      return;
    }

	if (newPassword && !window.confirm('Are you sure you want to update your password?')) {
	  return; 
	}
	
    // If the newPassword field is filled, update it, otherwise, use the old password
    const url = `http://localhost:8080/Workout-Web-Application/UpdateUserProfile?userId=${userId}&password=${encodeURIComponent(newPassword || password)}&name=${encodeURIComponent(name)}`;

    fetch(url)
      .then((response) => {
        if (!response.ok) {
          //throw new Error(`HTTP Error: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        if (data.error) {
          setErrorMessage(data.error);
        } else {
          setErrorMessage('');
          fetchUserProfile();
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
		  readOnly
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
      <button className="save-btn" /*onClick={handleSave}*/>
        Save
      </button>
    </div>
  );
};

export default UpdateUserForm;