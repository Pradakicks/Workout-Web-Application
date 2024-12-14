import React, { useState } from "react";
import "./ProfileCard.css"; // Import CSS for styling
import "./Dashboard.css";

import StreakDay from "./StreakDay";

const StreakCard = () => {
  const [streak, setStreak] = useState(58); // Initialize streak state with a default value

  const updateStreak = () => {
	localStorage.setItem('clientId', '1');
    let clientId = localStorage.getItem('clientId');

    if (!clientId) {
      alert("Client ID not found. Please log in.");
      return;
    }

    const url = `http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/update-streak?clientId=${clientId}`;

    fetch(url)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to update streak.");
        }
        return response.text();
      })
      .then((data) => {
        // Assuming the API returns the updated streak value
        const updatedStreak = parseInt(data, 10);
        if (!isNaN(updatedStreak)) {
          setStreak(updatedStreak); // Update the streak state
        } else {
          alert("Invalid response from server.");
        }
      })
      .catch((error) => {
        console.error("Error updating streak:", error);
        //alert("Failed to update streak. Please try again.");
      });
  };

  return (
    <div className="streak-card">
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "flex-start",
        }}
      >
        <div
          style={{
            display: "flex",
            width: "100%",
            marginLeft: "1px",
            marginBottom: "1em",
          }}
        >
          <div style={{ flexDirection: "column" }}>
            <h4 style={{ color: "#6c6c6c", fontWeight: "lighter" }}>
              Current streak
            </h4>
            <h2 style={{ color: "#C41616" }}>🔥 {streak}</h2>
          </div>
          {/* Plus button */}
          <div style={{ justifyContent: "flex-end", marginLeft: "25em" }}>
            <button
              onClick={updateStreak} // Attach the click handler
              style={{
                width: "30px", // Adjust the size
                height: "30px", // Make it a circle
                borderRadius: "50%", // Creates a circular shape
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                backgroundColor: "#E8E8E8", // Button background
                color: "#6C6C6C",
                border: "none",
                cursor: "pointer",
                fontSize: "20px",
              }}
            >
              +
            </button>
          </div>
        </div>
        <div style={{ display: "flex", gap: "0.5em" }}>
          <StreakDay day="S" />
          <StreakDay day="M" />
          <StreakDay day="T" />
          <StreakDay day="W" />
          <StreakDay day="T" />
          <StreakDay day="F" />
          <StreakDay day="S" />
        </div>
      </div>
    </div>
  );
};

export default StreakCard;
