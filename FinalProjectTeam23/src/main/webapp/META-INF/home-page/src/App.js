import React, { useState, useEffect } from "react";
import logo from "./assets/logo.svg";
import edit from "./assets/edit.svg";
import Header from "./components/Header";
import settings from "./assets/settings.svg";
import ProfileCard from "./components/ProfileCard";
import SearchBar from "./components/SearchBar";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import TrainerPreviewCard from "./components/TrainerPreviewCard";

import Login from "./Login"; // Import your Login component
import { useNavigate } from "react-router-dom"; // Import useNavigate


import "./App.css";

const App = () => {
  const logoHeight = 80;
  // const cors = require("cors");
  // App.use(cors());
  // var corsOptions = {
  //   origin: "http://localhost:3000"
  // };
  
  // app.use(cors(corsOptions));
  const userId = localStorage.getItem('userId');
  const navigate = useNavigate(); // Initialize navigate function

  const [trainers, setTrainers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filteredTrainers, setFilteredTrainers] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [sortBy, setSortBy] = useState("name"); // New state for sorting criteria
  const [isSearchTriggered, setIsSearchTriggered] = useState(false);
  //
	
  const handleSettingsClick = () => {
      navigate("/settings"); // Navigate to the settings page
    };
	
	const handleLogout = () => {
		localStorage.removeItem("userId");
		 localStorage.removeItem("role");
		 navigate("/login"); // Redirect to login page after logout
	 };
	 
  useEffect(() => {
	if (isSearchTriggered) {
      console.log("Search triggered!");
      // Trigger your search logic here
      // For example, fetch data based on the current search query
      handleSearch({ target: { value: searchQuery } });
    }
  }, [isSearchTriggered]);
	
  useEffect(() => {
    const baseURL = "http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/";
    const url = new URL("Server", baseURL); // Define url inside the effect but don't include it in dependencies

    fetch(url, {
      method: "GET",
      credentials: "include", // This includes cookies if required
      headers: {
        'Content-Type': 'application/json', // Explicitly specify the content type
      },
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
		  console.log('Response Body:', data); // Log response
		       // Map trainerId to id for consistency
			   const mappedData = data.map(trainer => ({
			           ...trainer,
			           id: trainer.trainerId, // Add `id` property
			         }));
		  setTrainers(mappedData);
          setFilteredTrainers(mappedData);
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
  }, []); // Empty dependency array ensures the effect runs only once on mount


  /*
  const handleSearch = (e) => {
      const query = e.target.value;
      console.log("Search Query:", query); // Logs the search query
      setSearchQuery(query);
      const filtered = trainers.filter((trainer) => {
          return (
              trainer.name.toLowerCase().includes(query.toLowerCase()) ||
              trainer.trainerTitle.toLowerCase().includes(query.toLowerCase()) ||
              trainer.services.toLowerCase().includes(query.toLowerCase())
          );
      });
      setFilteredTrainers(filtered);
  };
*/
const handleSearch = (e) => {
  const query = e.target.value;
  setSearchQuery(query);
	
  if(isSearchTriggered || query === ""){
	  setIsSearchTriggered(true);
	  let baseURL = "http://localhost:8080/Workout-Web-Application-1.0-SNAPSHOT/";
	  let url = new URL("SearchTrainers", baseURL);
	  url.searchParams.set("query", query);
	  url.searchParams.set("sortBy", sortBy);
	  console.log("Sort by in handle search", sortBy);

	  console.log('Search URL:', url.toString());

	  fetch(url, {
	    method: "GET",
	    credentials: "include",
	    headers: {
	      'Content-Type': 'application/json',
	    },
	  })
	    .then(response => response.json())
	    .then(data => {
	      console.log('Filtered Trainers:', data); // Log response
	      if (Array.isArray(data)) {
	        setFilteredTrainers(data); // Update state only if response is an array
	      } else {
	        console.error('Unexpected response:', data); // Log unexpected responses
	        setFilteredTrainers([]);
	      }
	    })
	    .catch(error => {
	      console.error("Error fetching trainers:", error);
	      setFilteredTrainers([]); // Reset state on error
	    })
		.finally(() => {
	        // Reset isSearchTriggered after the search is complete
	        setIsSearchTriggered(false);
	    });
  }
};



// Handle sort change from the radio buttons
 const handleSortChange = (e) => {
	const selectedSort = e.target.value;
	console.log(selectedSort);
    setSortBy(selectedSort); // Update the sort criteria
 };
 
 const handleSort = () => {
   setIsSearchTriggered(true);
   console.log("hi");
   setIsSearchTriggered(true);
   console.log(isSearchTriggered);
   handleSearch({ target: { value: searchQuery } }); // Trigger search with the current query and sort criteria
 };



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
         }}
       >
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

         {/* Conditionally render Profile and Settings Buttons */}
         {userId && (
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
             <button className="profile-button">
               <img
                 src={edit}
                 alt="Edit Icon"
                 style={{ width: 30, marginRight: 10 }}
               />
               <span>Profile</span>
             </button>

			 <button className="settings-button" onClick={handleSettingsClick}>
			       <img
			         src={settings}
			         alt="Settings Icon"
			         style={{ width: 30, marginRight: 10 }}
			       />
			       <span>Settings</span>
			     </button>
				 
				 {/* Logout Button */}
				             <button
				               className="settings-button"
				               onClick={handleLogout}
				               style={{
				                 backgroundColor: "#fbfbfb", // Same light gray background
				                 color: "#6c6c6c", // Same dark gray text
				                 border: "solid #6c6c6c 1px", // Same border style
				                 borderRadius: "10px", // Rounded corners
				                 padding: "8px 16px",
				                 fontSize: "0.9rem",
				                 fontWeight: "bold",
				                 cursor: "pointer",
				               }}
				             >
				               <span>Logout</span>
				             </button>
				          
           </div>
         )}
       </div>

       {/* Rest of your UI (Header, Search, Grid, etc.) */}
       <Header 
	   	onSearch={handleSearch} 
		onSort={handleSort}
	    sortBy={sortBy} 
	    onSortChange={handleSortChange}
	   />

       <div
         className="grid-container"
         style={{
           display: "grid",
           backgroundColor: "rgb(245, 245, 245)",
           gridTemplateColumns: "repeat(2, 1fr)",
           marginTop: "50px",
           gap: "20px",
           width: "90%",
         }}
       >
         {loading ? (
           <p>Loading trainers...</p>
         ) : (
           filteredTrainers.map((trainer) => (
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
