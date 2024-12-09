import React, { useState, useEffect } from "react";
import logo from "./assets/logo.svg";
import edit from "./assets/edit.svg";
import settings from "./assets/settings.svg";
import ProfileCard from "./components/ProfileCard";
import SearchBar from "./components/SearchBar";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import "./App.css";

const App = () => {
  const logoHeight = 80;
  // const cors = require("cors");
  // App.use(cors());
  // var corsOptions = {
  //   origin: "http://localhost:3000"
  // };
  
  // app.use(cors(corsOptions));

  const [trainers, setTrainers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filteredTrainers, setFilteredTrainers] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");

  let baseURL = "http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/";
  var url = new URL("Server", baseURL);

  useEffect(() => {
    fetch(url, {
      mode: 'no-cors',
      method: "GET",
      credentials: "include", // This includes cookies if required
    })
      .then(response => {
        console.log('Response Status:', response.status); // Log status code
        console.log('actual url:', url);
        return response.text(); // Get raw text first
      }) 
      .then(text => {
        console.log('Response Body:', text); // Log the body of the response
        try {
          const data = JSON.parse(text); // Try to parse the response manually
          setTrainers(data);
          setFilteredTrainers(data);
          setLoading(false);
        } catch (error) {
          console.error('Error parsing JSON:', error);
          setLoading(false);
        }
      })
      .catch(error => {
        console.error("Error fetching trainers:", error);
        setLoading(false); 
      });
  }, [url]);

  const handleSearch = (e) => {
    setSearchQuery(e.target.value);
    const query = e.target.value.toLowerCase();
  
    // Filter the trainers based on name, title, or services
    const filtered = trainers.filter((trainer) => {
      return (
        trainer.name.toLowerCase().includes(query) ||
        trainer.trainerTitle.toLowerCase().includes(query) ||
        trainer.services.toLowerCase().includes(query)
      );
    });
  
    setFilteredTrainers(filtered);
  };

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
          <SearchBar onSearch={handleSearch}/>
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
        {loading ? (
          <p>Loading trainers...</p>
        ) : (
          filteredTrainers.map(trainer => (
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

export default App;
