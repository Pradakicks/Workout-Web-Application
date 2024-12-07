import React, { useState, useEffect } from 'react';

const Goals = () => {
  const [goals, setGoals] = useState([]);
  const [goalInput, setGoalInput] = useState('');

  useEffect(() => {
    fetchGoals();
  }, []);

  const fetchGoals = () => {
	localStorage.setItem('clientId','1');
    const clientId = localStorage.getItem('clientId');
    if (!clientId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/settings/fetchGoals?clientId=${clientId}`;
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data) && data.length > 0) {
          setGoals(data);
        } else {
          console.log('No goals found for the user.');
        }
      })
      .catch((err) => {
        alert('Failed to fetch goals: ' + err.message);
      });
  };

  const handleAddGoal = () => {
    const clientId = localStorage.getItem('clientId');
    if (!clientId) {
      alert('User not logged in.');
      return;
    }

    if (goalInput.trim() === '') {
      alert('Please enter a goal.');
      return;
    }

    if (goals.length >= 5) {
      alert('You can only have 5 goals.');
      return;
    }

    const url = `http://localhost:8080/settings/AddGoal?clientId=${clientId}&goal=${goalInput.trim()}`;

    fetch(url, { method: 'GET' })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          setGoals([...goals, goalInput.trim()]);
          setGoalInput('');
        } else {
          alert('Failed to add goal: ' + data.error);
        }
      })
      .catch((err) => {
        alert('Failed to add goal: ' + err.message);
      });
  };


  const handleRemoveGoal = (goal) => {
    const clientId = localStorage.getItem('clientId');
    if (!clientId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/settings/RemoveGoal?clientId=${clientId}&goal=${goal}`;

    fetch(url, { method: 'GET' })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          setGoals(goals.filter((g) => g !== goal));
        } else {
          alert('Failed to remove goal: ' + data.error);
        }
      })
      .catch((err) => {
        alert('Failed to remove goal: ' + err.message);
      });
  };

  return (
    <div className="container">
      <div className="header">Goals</div>
      <div className="goals-container">
        {goals.map((goal, index) => (
          <div key={index} className="goal-item">
            <span>{goal}</span>
            <button onClick={() => handleRemoveGoal(goal)} className="remove-button">
              x
            </button>
          </div>
        ))}
      </div>
      <div className="add-goal-container">
        <input
          type="text"
          value={goalInput}
          onChange={(e) => setGoalInput(e.target.value)}
          placeholder="Enter new goal"
          className="input"
        />
        <button onClick={handleAddGoal} className="add-button">
          +
        </button>
      </div>
    </div>
  );
};

export default Goals;
