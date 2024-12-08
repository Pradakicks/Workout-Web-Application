import SearchBar from "./SearchBar";
import CircleButton from "./CircleButton";
import ProfileButton from "./ProfileButton";
import Logo from "./Logo";

const Header = () => {
  return (
	<>
	{/* Logo Positioned at the Top-Left Corner */}
	<div
	style={{
	  position: "relative", // Parent container for scrolling
	  width: "100%",
	}}>
	 <Logo />
  	</div>

  {/* Search Bar, Sort By Button, Circle Button, and Profile Button */}
  <div
	style={{
	  position: "relative",
	  width: "100%", // Full width container
	  display: "flex",
	  alignItems: "center",
	  justifyContent: "flex-end",
	  paddingTop: "20px",
	  paddingRight: "20px",
	  gap: "20px",
	}}>
   

	{/* Search Bar */}
	<div
	  style={{
		width: "80%",
		maxWidth: "350px", // Optional max width
		fontFamily: "ReadexPro-Light, sans-serif",
	  }}>
	  <SearchBar />
	</div>

	<CircleButton />
	<ProfileButton />
  </div>
  </>
  )
}

export default Header
