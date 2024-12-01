import React from "react";
import SearchBar from "./components/SearchBar";
import CircleButton from "./components/CircleButton";
import ProfileButton from "./components/ProfileButton";
import UserForm from './components/UserForm';
import EditPfp from "./components/EditPfp";
import Goals from "./components/Goals";
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
        <div>
          <SearchBar />
        </div>

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
    </div>
  );
};

export default App;

