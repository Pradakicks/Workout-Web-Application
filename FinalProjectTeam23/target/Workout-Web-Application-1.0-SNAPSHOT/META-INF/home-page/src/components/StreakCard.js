import "./ProfileCard.css"; // Import CSS for styling
import "./Dashboard.css";

import profileImage from "../assets/example-trainer.png"; // Replace with your image path
import dumbbellIcon from "../assets/dumbbell.svg";
import StreakDay from "./StreakDay";

const StreakCard = () => {
  return (
	<div className="streak-card">
      <div style={{
		display: "flex",
		flexDirection: "column",
		justifyContent: "flex-start",
	  }}>
		<div style={{display: "flex", width: "100%", marginLeft: "1px", marginBottom: "1em"}}>
			<div style={{flexDirection: "column"}}>
				<h4 style={{color: "#6c6c6c", fontWeight: "lighter"}}>Current streak</h4>
				<h2 style={{color: "#C41616"}}>🔥 58</h2>
	  		</div>
			{/* Plus button */}
			<div style={{justifyContent: "flex-end", marginLeft: "25em"}}>
				<button
				style={{
					width: "30px", // Adjust the size
					height: "30px", // Make it a circle
					borderRadius: "50%", // Creates a circular shape
					display: "flex",
					alignItems: "center",
					justifyContent: "center",
					backgroundColor: "#E8E8E8", // Button background
					color: "#6C6C6C",
					border: "none",
					cursor: "pointer",
					fontSize: "20px"
				}}>
				+
			</button>
			</div>
	  </div>
	  <div style={{display: "flex", gap: "0.5em"}}>
	  	<StreakDay day="S"/>
	  	<StreakDay day="M"/>
		<StreakDay day="T"/>
		<StreakDay day="W"/>
		<StreakDay day="T"/>
		<StreakDay day="F"/>
		<StreakDay day="S"/>
	  </div>
    </div>
	</div>
  )
}

export default StreakCard
