import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css"; // Includes Tailwind styles
import { BrowserRouter, Routes, Route } from "react-router";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import ReviewPage from "./components/ReviewPage";
import Dashboard from "./components/Dashboard";
import AddReview from "./components/AddReview"; // Import the AddReview component
import SettingsPage from "./components/SettingsPage";
import Login from './Login';
import Register from './Register';
import TrainerProfile from './components/TrainerProfile'; // Import the TrainerProfile component


const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
	  	<Route path="/login" element={<Login />} />
	    <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/review" element={<ReviewPage />} />
		<Route path="/settings" element={<SettingsPage />} />
		<Route path="/trainer-profile/:id" element={<TrainerProfile />} />
		<Route path="/add-review/:id" element={<AddReview />} />
		<Route path="*" element={<App />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
