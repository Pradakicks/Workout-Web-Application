import pushups from "../assets/pushups.svg";
import dumbbellBenchPress from "../assets/dumbbell-bench-press.svg";
import bicepCurls from "../assets/bicep-curls.svg";
import tricepDips from "../assets/tricep-dips.svg";

const WorkoutItem = ({ icon = pushups, exercise = "Push-Ups", reps = "3 x 10 reps" }) => {
  return (
	<div className="workout-item" style={{
		display: "flex",
		flexDirection: "column",
		alignItems: "center"
		
	}}>
		<img
			src={icon}
			style={{ width: 90, marginBottom: "1em" }}
		/>
		<h2 style={{color: "#103F5B",
			 marginBottom: "0.2em", 
			 fontSize: "16px", 
			 textAlign: "center"}}>
				{exercise}
		</h2>
		<h3 style={{color: "#103F5B"}}>{reps}</h3>
	</div>
  )
}

export default WorkoutItem
