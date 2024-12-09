import React, { useState, useEffect } from 'react';
import { Routes, Route, useNavigate } from "react-router-dom";
import logo from "./assets/logo.svg";
import edit from "./assets/edit.svg";
import settings from "./assets/settings.svg";
import ProfileCard from "./components/ProfileCard";
import SearchBar from "./components/SearchBar";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import SettingsPage from "./components/SettingsPage";
import Dashboard from "./components/Dashboard";
import "./App.css";
import Login from "./Login";

const App = () => {
  const logoHeight = 80;
  const navigate = useNavigate();

  const [trainers, setTrainers] = useState([]);
  const [loading, setLoading] = useState(true);

  let baseURL = window.location.origin + "/FinalProjectTeam23/";
  var url = new URL("Weather", baseURL);

  useEffect(() => {
    fetch(url)
      .then(response => response.json()) 
      .then(data => {
        setTrainers(data); 
        setLoading(false);
      })
      .catch(error => {
        console.error("Error fetching trainers:", error);
        setLoading(false); 
      });
  }, []);

  return (
    <div
      style={{
        backgroundColor: "rgb(245, 245, 245)",
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "100%",
        overflowX: "hidden",
      }}
    >
      <div
        style={{
          position: "relative",
          width: "100%",
        }}>
        <img
          src={logo}
          alt="Logo"
          style={{
            position: "absolute",
            width: "80px",
            height: `${logoHeight}px`,
            left: "2%",
            top: "2%",
          }}
        />

        <div
          style={{
            position: "absolute",
            top: `calc(${logoHeight}px + 10px)`,
            left: "2%",
            display: "flex",
            flexDirection: "column",
            gap: "10px",
          }}
        >
          <button
            className="profile-button"
            onClick={() => navigate("/dashboard")}
          >
            <img
              src={edit}
              alt="Edit Icon"
              style={{ width: 30, marginRight: 10 }}
            />
            <span>Profile</span>
          </button>

          <button
            className="settings-button"
            onClick={() => navigate("/SettingsPage")}
          >
            <img
              src={settings}
              alt="Settings Icon"
              style={{ width: 30, marginRight: 10 }}
            />
            <span>Settings</span>
          </button>
        </div>
      </div>

      <div
        style={{
          position: "relative",
          width: "100%",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          paddingTop: "20px",
          gap: "20px",
        }}
      >
        <button
          className="sort-by-button"
          style={{
            marginBottom: "10px",
          }}
        >
          <span style={{ fontSize: "1rem" }}>Sort By</span>
        </button>

        <div
          style={{
            width: "80%",
            maxWidth: "350px",
          }}
        >
          <SearchBar />
        </div>

        <CircleButton />
        <ProfileButton />
      </div>

      <div
        className="grid-container"
        style={{
          display: "grid",
          backgroundColor: "rgb(245, 245, 245)",
          gridTemplateColumns: "repeat(2, 1fr)",
          marginTop: "150px",
          gap: "20px", // Optional: Adds spacing between rows and columns
          width: "90%", // Responsive grid width
        }}>
        {loading ? (
          <p>Loading trainers...</p>
        ) : (
          trainers.map(trainer => (
            <ProfileCard key={trainer.trainerId} trainer={trainer} />
          ))
        )}
      </div> 
    </div>
  );
};
/*For each trainer in the trainers array, it renders a ProfileCard component.
Each ProfileCard gets a key prop, which is necessary for React to keep track of 
elements in the list efficiently. The trainer.trainerId is used as the unique key.*/

const AppRoutes = () => (
  <Routes>
    <Route path="*" element={<App />} />
    <Route path="/SettingsPage" element={<SettingsPage />} />
    <Route path="/dashboard" element={<Dashboard />} />
  </Routes>
);

export default AppRoutes;
