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
	  <form>
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
		>Create one here</button>
    </div>
  );
}

export default Login;