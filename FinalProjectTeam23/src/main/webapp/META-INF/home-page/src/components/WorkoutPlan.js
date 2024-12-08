import "./ProfileCard.css"; // Import CSS for styling
import "./Dashboard.css"; // Import CSS for styling

import pushups from "../assets/pushups.svg";
import dumbbellBenchPress from "../assets/dumbbell-bench-press.svg";
import bicepCurls from "../assets/bicep-curls.svg";
import tricepDips from "../assets/tricep-dips.svg";
import WorkoutItem from "./WorkoutItem";

const WorkoutPlan = () => {
	return (
	  <div className="workout-plan">
		<WorkoutItem icon={pushups} exercise="Push-Ups" reps="3 x 10 reps"/>
		<WorkoutItem icon={dumbbellBenchPress} exercise="Dumbbell Bench Press" reps="3 x 8 reps"/>
		<WorkoutItem icon={bicepCurls} exercise="Bicep Curls" reps="3 x 10 reps"/>
		<WorkoutItem icon={tricepDips} exercise="Tricep Dips" reps="3 x 12 reps"/>

		
	  </div>
	);
  };
  
  export default WorkoutPlan;