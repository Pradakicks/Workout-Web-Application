import React from "react";
import logo from "./assets/logo.svg";
import edit from "./assets/edit.svg";
import settings from "./assets/settings.svg";
import ProfileCard from "./components/ProfileCard";
import SearchBar from "./components/SearchBar";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import "./App.css";
import Dashboard from "./Dashboard";

const App = () => {
  const logoHeight = 80;
  return (< Dashboard/>)
  return (
    <div
      style={{
        backgroundColor: "rgb(245, 245, 245)",
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "100%", // Full width container
        overflowX: "hidden", // Prevent horizontal scrolling
      }}>
      {/* Logo Positioned at the Top-Left Corner */}
      <div
        style={{
          position: "relative", // Parent container for scrolling
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

        {/* Profile and Settings Buttons */}
        <div
          style={{
            position: "absolute",
            top: `calc(${logoHeight}px + 10px)`, // Relative to logo
            left: "2%", // Aligned to the left with the logo
            display: "flex",
            flexDirection: "column",
            gap: "10px",
          }}>
          <button className="profile-button">
            <img
              src={edit}
              alt="Edit Icon"
              style={{ width: 30, marginRight: 10 }}
            />
            <span>Profile</span>
          </button>

          <button className="settings-button">
            <img
              src={settings}
              alt="Settings Icon"
              style={{ width: 30, marginRight: 10 }}
            />
            <span>Settings</span>
          </button>
        </div>
      </div>

      {/* Search Bar, Sort By Button, Circle Button, and Profile Button */}
      <div
        style={{
          position: "relative",
          width: "100%", // Full width container
          display: "flex",
          alignItems: "center",
          justifyContent: "center", // Center items
          paddingTop: "20px",
          gap: "20px",
        }}>
        {/* Sort By Button */}
        <button
          className="sort-by-button"
          style={{
            marginBottom: "10px", // Space below the button for the search bar
          }}>
          <span style={{ fontSize: "1rem" }}>Sort By</span>
        </button>

        {/* Search Bar */}
        <div
          style={{
            width: "80%",
            maxWidth: "350px", // Optional max width
          }}>
          <SearchBar />
        </div>

        <CircleButton />
        <ProfileButton />
      </div>

      {/* Grid Container */}
      <div
        className="grid-container"
        style={{
          display: "grid",
          backgroundColor: "rgb(245, 245, 245)",
          gridTemplateColumns: "repeat(2, 1fr)", // Limit to 2 items per row
          marginTop: "150px",
          gap: "20px", // Optional: Adds spacing between rows and columns
          width: "90%", // Responsive grid width
        }}>
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
        <ProfileCard />
      </div>
    </div>
  );
};

export default App;
