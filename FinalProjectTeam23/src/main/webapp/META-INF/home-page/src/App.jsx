import React from "react";
import ProfileCard from "./components/ProfileCard";
import Header from "./components/Header";
import ProfileHeader from "./components/ProfileHeader";
import StreakCard from "./components/StreakCard";
import SmallDataCard from "./components/SmallDataCard";
import TrainerPreviewCard from "./components/TrainerPreviewCard";

import "./App.css";

import shoe from "./assets/shoe.svg";
import plate from "./assets/plate.svg";
import water from "./assets/water.svg";
import scale from "./assets/scale.svg";
import online from "./assets/online.svg";
import offline from "./assets/offline.svg";
import WorkoutPlan from "./components/WorkoutPlan";

const App = () => {

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
      
      <Header />
      <ProfileHeader />

      {/* Dashboard container */}
      <div style={{display: "flex", marginTop: "3em", marginBottom: "5em"}}> 
      {/* Left column */}
        <div style={{display: "flex", flexDirection: "column"}}>
          <h3>MY INFO</h3>
          <div style={{display: "flex"}}>
            <StreakCard />
            {/* User's health stats */}
            <div
            className="grid-container"
            style={{
              display: "grid",
              backgroundColor: "rgb(245, 245, 245)",
              gridTemplateColumns: "repeat(2, 1fr)", // Limit to 2 items per row
              gap: "10px", // Optional: Adds spacing between rows and columns
            }}>
                <SmallDataCard dataTitle="Active steps" dataValue="6835" icon={shoe}/>
                <SmallDataCard dataTitle="Calories" dataValue="4901 kcal" icon={plate}/>
                <SmallDataCard dataTitle="Water intake" dataValue="3 L" icon={water}/>
                <SmallDataCard dataTitle="Weight" dataValue="153 lbs" icon={scale}/>
            </div>
          </div>
          <div style={{ marginTop: "3em", alignItems: "flex-start" }}>
            <h3>TODAY'S WORKOUT PLAN</h3>
            <WorkoutPlan />
          </div>
        </div>
            
        {/* Trainer profiles */}
        <div style={{flexDirection: "column", marginLeft: "3em"}}>
          <h3>MY TRAINERS</h3>
            <TrainerPreviewCard status={online} />
            <TrainerPreviewCard status={offline} />
        </div>

      </div>

    </div>
  );
};

export default App;
