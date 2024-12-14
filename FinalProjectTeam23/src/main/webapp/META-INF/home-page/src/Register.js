import React, { useState } from 'react';

const Register = () => {
  const [newUsername, setNewUsername] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newEmail, setNewEmail] = useState('');
  const [newName, setNewName] = useState('');
  const [error, setError] = useState('');
  const [role, setRole] = useState('client'); // Default role
  const [successMessage, setSuccessMessage] = useState('');

  const handleRegister = (e) => {
    e.preventDefault();

    // Clear previous messages
    setError('');
    setSuccessMessage('');

    // Validate input (optional step)
    if (!newUsername || !newPassword || !newEmail) {
      setError('Please fill in all required fields.');
      return;
    }

    // Prepare form data for POST
    const formData = new URLSearchParams();
    formData.append('username', newUsername);
    formData.append('password', newPassword);
    formData.append('email', newEmail);
    formData.append('name', newName);
	formData.append('role', role); // Add role to the form data

    fetch('http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: formData.toString(),
    })
      .then(response => response.json())
      .then(data => {
        if (data.status === 'success') {
          // Registration successful
          setSuccessMessage('Registration successful! Redirecting to login...');
          // Optionally redirect to login page after a short delay
          setTimeout(() => {
            window.location.href = '/login';
          }, 2000);
        } else {
          // Show error message
          setError(data.message || 'Registration failed.');
        }
      })
      .catch(err => {
        console.error('Registration error:', err);
        setError('An error occurred while registering.');
      });
  };

  return (
    <div className="register-container">
      <h2>Register</h2>
      {error && <p className="error">{error}</p>}
      {successMessage && <p className="success">{successMessage}</p>}
      <form onSubmit={handleRegister}>
        <div>
          <label>Username*</label>
          <input
            type="text"
            value={newUsername}
            onChange={(e) => setNewUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password*</label>
          <input
            type="password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Email*</label>
          <input
            type="email"
            value={newEmail}
            onChange={(e) => setNewEmail(e.target.value)}
            required
			 />
			        </div>
			        <div>
			          <label>Name (optional)</label>
			          <input
			            type="text"
			            value={newName}
			            onChange={(e) => setNewName(e.target.value)}
			          />
			        </div>
			        <div>
			          <label>Role*</label>
			          <select value={role} onChange={(e) => setRole(e.target.value)} required>
			            <option value="client">Client</option>
			            <option value="trainer">Trainer</option>
			          </select>
			        </div>
			        <button type="submit">Register</button>
			      </form>
			    </div>
			  );
			};

export default Register;
