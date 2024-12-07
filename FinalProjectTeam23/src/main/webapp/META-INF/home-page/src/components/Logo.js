import logo from "../assets/logo.svg";

const Logo = () => {
	const logoHeight = 80;
  return (
	<div>
	  <img
          src={logo}
          alt="Logo"
          style={{
            position: "absolute",
            width: "80px",
            height: `${logoHeight}px`,
            left: "2%",
            top: "2%",
          }}
        />
	</div>
  )
}

export default Logo
