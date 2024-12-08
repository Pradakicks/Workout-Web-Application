import React from "react";
import { useNavigate } from "react-router-dom";
import home from "../assets/home.svg";
import SettingsCircleButton from "./SettingsCircleButton";
import SettingsProfileButton from "./SettingsProfileButton";
import UserForm from './UpdateUserForm';
import Goals from "./Goals";
import LogoutButton from "./LogoutButton";
import "./Settings.css";

const SettingsPage = () => {
  const navigate = useNavigate(); // Hook for navigation

  return (
    <div>
      {/* Logo Positioned at the Top-Left Corner */}
      <div className="app-name">activ</div>

      {/* Home Button */}
      <button
        className="home-button"
        onClick={() => navigate("/")} // Navigate to the main App
      >
        <img
          src={home}
          alt="Home Icon"
          style={{ width: 30, marginRight: 10 }}
        />
        <span>Home</span>
      </button>

      {/* Notification Button, and Profile Button */}
      <div>
        <SettingsCircleButton />
        <SettingsProfileButton />
      </div>

      {/* User Form and Profile Picture */}
      <div style={{ display: 'flex', flexGrow: 1 }}>
        <UserForm />
        <EditPfp />
      </div>

      {/* Goals Component */}
      <Goals />

      {/* Logout Button */}
      <LogoutButton />
    </div>
  );
};

export default SettingsPage;