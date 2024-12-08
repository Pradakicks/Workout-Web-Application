import edit from "../assets/edit.svg";
import settings from "../assets/settings.svg";
import ProfileButton from "./ProfileButton";

const ProfileHeader = () => {
  return (
	<>
	<div
	style={{
	  position: "relative",
	  width: "100%", // Full width container
	  display: "flex",
	  alignItems: "center",
	  justifyContent: "center",
	  paddingTop: "20px",
	  paddingRight: "20px",
	  gap: "20px",
	}}>
   

	<ProfileButton width="150px" height="150px"/>

	<div
	style={{
		display: "flex",
		flexDirection: "column",
		alignItems: "flex-start",
		marginLeft: "2em",

	}}>
		<h1 style={{paddingBottom: "0.2em",}}>John Smith</h1>
		<h3>@johnsmith123</h3>

	</div>

	<button className="profile-button" style={{marginLeft: "2em"}}>
		<img
			src={edit}
			alt="Edit Icon"
			style={{ width: 30, marginRight: 10 }}
		/>
		<span>Edit Profile</span>
	</button>

	<button className="settings-button">
		<img
			src={settings}
			alt="Settings Icon"
			style={{ width: 20, marginRight: 10 }}
		/>
		<span>Settings</span>
	</button>

  </div>
  </>
  )
}

export default ProfileHeader
