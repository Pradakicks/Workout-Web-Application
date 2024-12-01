import React from "react";
import SearchIcon from "../assets/search.svg"; 

const SearchBar = () => {
  return (
    <div className="search-bar-container">
      <img
        src={SearchIcon}
        alt="Search Icon"
      />
      <input
        type="text"
        placeholder="Search for personal trainers..."
      />
    </div>
  );
};

export default SearchBar;
