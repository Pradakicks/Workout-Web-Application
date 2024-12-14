// src/Login.js

import React, { useState } from 'react';
//import axios from 'axios';
import './Login.css';

const Login = () => {
  // State variables for username and password
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  // State variable for error messages
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
      e.preventDefault(); // Prevent form from reloading the page

      fetch('http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded', // backend expects form data
        },
        body: new URLSearchParams({
          username: username,
          password: password,
        }),
      })
        .then((res) => res.json())
        .then((data) => {
          if (data.status === 'success') {
            // Store userId in localStorage
            localStorage.setItem('userId', data.userId);
			localStorage.setItem('role', data.role);
            window.location.href = '/'; // Redirect to homepage or wherever after successful login
          } else {
            setError(data.message || 'Invalid username or password');
          }
        })
        .catch((err) => {
          console.error('Login error:', err);
          setError('An error occurred while logging in.');
        });
    };
  // Function to handle form submission
//   const handleSubmit = (e) => {
//     e.preventDefault(); // Prevent default form submission behavior

//     // Make a POST request to the backend
// 	axios.post('/login', {
// 	  username: username,
// 	  password: password
// 	})
// 	.then(response => {
// 	  // Handle successful login
// 	  console.log(response.data);
// 	  alert('Login successful!');
// 	})
// 	.catch(error => {
// 	  // Handle login error
// 	  console.error('There was an error logging in!', error);
// 	  setError('Invalid username or password');
// 	});
//   };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <p className="error">{error}</p>} {/* Display error message */}
      {/* <form onSubmit={handleSubmit}> */}
	  <form onSubmit={handleSubmit}>
        <div>
          <label>Username</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
			style={{marginBottom: "1em"}}
          />
        </div>
        <button type="submit"
			style={{marginBottom: "3em"}}
		>Login</button>
      </form>
	  <label>Need an account?</label>
	  <button
			style={{marginBottom: "3em",
				width: "30%",
				fontSize: "14px"
			}}
			onClick={() => { window.location.href = '/register'; }} // Navigate to a register page
		>Create one here</button>
    </div>
  );
}

export default Login;