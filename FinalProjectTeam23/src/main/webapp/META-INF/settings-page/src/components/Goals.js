import React, { useState } from 'react';

const Goals = () => {
  const [goals, setGoals] = useState([]); // Initialize with an empty list of goals
  const [goalInput, setGoalInput] = useState('');

  // Handle adding a new goal
  const handleAddGoal = () => {
    if (goalInput.trim() !== '') {
      setGoals([...goals, goalInput.trim()]);
      setGoalInput(''); // Clear the input field after adding a goal
    }
  };

  // Handle removing a goal
  const handleRemoveGoal = (index) => {
    const updatedGoals = goals.filter((goal, i) => i !== index);
    setGoals(updatedGoals);
  };

  return (
    <div className="container">
      <div className="header">Goals</div>

      {/* Display the list of goals */}
      <div className="goals-container">
        {goals.map((goal, index) => (
          <div key={index} className="goal-item">
            <span>{goal}</span>
            <button
              onClick={() => handleRemoveGoal(index)}
              className="remove-button"
            >
              &times;
            </button>
          </div>
        ))}
      </div>

      {/* Input for adding new goals */}
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
