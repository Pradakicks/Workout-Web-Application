import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css"; // Includes Tailwind styles
import { BrowserRouter, Routes, Route } from "react-router";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import ReviewPage from "./components/ReviewPage";
import Dashboard from "./components/Dashboard";
import SettingsPage from "./components/SettingsPage";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="*" element={<App />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/review" element={<ReviewPage />} />
		<Route path="/settings" element={<SettingsPage />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
