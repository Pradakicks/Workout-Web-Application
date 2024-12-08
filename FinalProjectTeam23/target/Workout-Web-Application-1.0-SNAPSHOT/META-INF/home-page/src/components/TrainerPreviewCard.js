import React from "react";
import "./ProfileCard.css"; // Import CSS for styling
import "./Dashboard.css"; // Import CSS for styling

import profileImage from "../assets/example-trainer.png"; // Replace with your image path
import dumbbellIcon from "../assets/dumbbell.svg";
import online from "../assets/online.svg";
import offline from "../assets/offline.svg";

const TrainerPreviewCard = ({status = online}) => {
  return (
    <div className="trainer-preview-card">
      <div className="profile-image" style={{marginRight: "1em"}}>
        <img
          src={profileImage}
          alt="Profile"
          style={{ width: 100, height: 100}}
        />
      </div>
      
      <div style={{flexDirection: "column"}}>
		<div style={{display: "flex"}}>
        	<h2 style={{fontSize: "24px"}}>Jake Carter</h2>
			<img
				src={status}
				alt="Edit Icon"
				style={{ width: 12,  marginLeft: "0.7em" }}
				/>
		</div>
        	<h3 style={{fontSize: "14px", marginBottom: "0.5em"}}>Strength & Conditioning Coach</h3>
       
        <button className="see-more-button">
          <img
            src={dumbbellIcon}
            alt="Dumbbell Icon"
            style={{ width: 30, marginRight: 10 }} // Adjust marginRight as needed
          />
          <span style={{ marginLeft: 10, marginRight: 5 }}>See More!</span>
        </button>
      </div>
    </div>
  );
};

export default TrainerPreviewCard;
