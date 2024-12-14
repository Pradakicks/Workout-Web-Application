import React from "react";
import { useNavigate } from 'react-router-dom';
import SearchBar from "./SearchBar";
import CircleButton from "./CircleButton";
import ProfileButton from "./ProfileButton";
import Logo from "./Logo";

const Header = ({ onSearch, onSort, sortBy, onSortChange}) => {
  const userId = localStorage.getItem('userId');
  const navigate = useNavigate();

  return (
    <>
      {/* Logo Positioned at the Top-Left Corner */}
      <div
        style={{
          position: "relative", // Parent container for scrolling
          width: "100%",
        }}
      >
        <Logo />
      </div>

      {/* Search Bar, Sort By Button, Circle Button, and Profile Button */}
      <div
        style={{
          position: "relative",
          width: "100%", // Full width container
          display: "flex",
          alignItems: "center",
          justifyContent: "flex-end",
          paddingTop: "20px",
          paddingRight: "20px",
          gap: "20px",
        }}
      >
	  	   <button
	         className="sort-by-button"
	         style={{
	           marginBottom: "10px",
	         }}
			 onClick={onSort}
	       >
	         <span style={{ fontSize: "1rem" }}>Sort By</span>
	       </button>
	       <div>
	         <label style={{ marginRight: "15px" }}>
	           <input
	             type="radio"
	             name="sortBy"
	             value="name"
	             checked={sortBy === "name"}
	             onChange={onSortChange}
	           />
	           Sort by Name
	         </label>
	         <label>
	           <input
	             type="radio"
	             name="sortBy"
	             value="trainerTitle"
	             checked={sortBy === "trainerTitle"}
	             onChange={onSortChange}
	           />
	           Sort by Title
	         </label>
	       </div>
        {/* Search Bar */}
        <div
          style={{
            width: "80%",
            maxWidth: "350px", // Optional max width
            fontFamily: "ReadexPro-Light, sans-serif",
          }}
        >
          <SearchBar 
		  	onSearch={onSearch} 
		  />
        </div>

        {/* Always show CircleButton */}
        <CircleButton />

        {userId ? (
          // If user is logged in, show ProfileButton
          <ProfileButton />
        ) : (
          // If user is not logged in, show Login and Register buttons
          <>
		  <button onClick={() => navigate('/login')}>
		               Login
		             </button>
		             <button onClick={() => navigate('/register')}>
		               Register
		             </button>
          </>
        )}
      </div>
    </>
  );
};

export default Header;
