import React from "react";
import SearchIcon from "../assets/search.svg"; // Path to your custom search SVG

const SearchBar = () => {
  return (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        border: "1px solid #ccc",
        borderRadius: "8px",
        padding: "10px",
        width: "100%", // Extend the bar across the container
        maxWidth: "600px", // Optional: Set a max width
        margin: "0 auto", // Center it horizontally
        backgroundColor: "#f3f3f3",
      }}>
      <img
        src={SearchIcon}
        alt="Search Icon"
        style={{
          width: "20px",
          height: "20px",
          marginRight: "10px", // Space between icon and input
          opacity: "0.6", // Optional: Slightly fade the icon
        }}
      />
      <input
        type="text"
        placeholder="Search..."
        style={{
          flex: "1", // Take the remaining width
          border: "none", // Remove input border
          outline: "none", // Remove focus outline
          fontSize: "16px", // Adjust text size
          backgroundColor: "transparent", // Match the container background
        }}
      />
    </div>
  );
};

export default SearchBar;
