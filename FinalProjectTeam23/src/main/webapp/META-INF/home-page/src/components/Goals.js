import React, { useState, useEffect } from 'react';

const Goals = () => {
  const [goals, setGoals] = useState([]);
  const [goalInput, setGoalInput] = useState('');

  useEffect(() => {
    fetchGoals();
  }, []);

  const fetchGoals = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/fetchGoals?userId=${userId}`;
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
        //alert('Failed to fetch goals: ' + err.message);
      });
  };

  const handleAddGoal = () => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    if (goalInput.trim() === '') {
      alert('Please enter a goal.');
      return;
    }

    // Check if the user already has 5 goals
    if (goals.length >= 5) {
      alert('You can only have 5 goals.');
      return;
    }

    const url = `http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/AddGoal?userId=${userId}&goal=${goalInput.trim()}`;

    fetch(url, { method: 'GET' })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          setGoals([...goals, goalInput.trim()]);
          setGoalInput('');
        } else {
          //alert('Failed to add goal: ' + data.error);
		  setGoals([...goals, goalInput.trim()]);
		            setGoalInput('');
        }
      })
      .catch((err) => {
        //alert('Failed to add goal: ' + err.message);
      });
  };


  const handleRemoveGoal = (goal) => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User not logged in.');
      return;
    }

    const url = `http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/RemoveGoal?userId=${userId}&goal=${goal}`;

    fetch(url, { method: 'GET' })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          setGoals(goals.filter((g) => g !== goal));
        } else {
          //alert('Failed to remove goal: ' + data.error);
		  setGoals(goals.filter((g) => g !== goal));
        }
      })
      .catch((err) => {
        //alert('Failed to remove goal: ' + err.message);
      });
  };

  return (
      <>
        {/* Header outside container */}
        <div className="header">Goals</div>

        <div className="container">
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
      </>
    );
  };

export default Goals;