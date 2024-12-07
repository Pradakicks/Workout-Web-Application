import React from "react";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import UserForm from './components/UserForm';
import EditPfp from "./components/EditPfp";
import Goals from "./components/Goals";
import LogoutButton from "./components/LogoutButton"; 
import "./App.css";

const App = () => {

  return (
    <div>
      {/* Logo Positioned at the Top-Left Corner */}
      <div className="app-name">
      activ
      </div>


      {/* Search Bar, Notif Button, and Profile Button */}
      <div>
        <CircleButton />
        <ProfileButton />
      </div>

      <div>
	      <div style={{ display: 'flex', flexGrow: 1 }}>
	        <UserForm /> {/* Left side form */}
	        <EditPfp /> {/* Right side profile picture */}
	      </div>
	      <Goals /> {/* Goals component at the bottom */}
	  </div>
	  <LogoutButton /> 
    </div>
  );
};

export default App;

