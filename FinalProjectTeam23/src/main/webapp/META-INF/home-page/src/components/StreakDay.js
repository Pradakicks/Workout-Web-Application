import React from 'react'

const StreakDay = ({day = "S"}) => {
  return (
	<div style={{
		display: "flex",
		flexDirection: "column",
		alignItems: "center"
	}}>
	  <h4 style={{fontSize: "12px", fontWeight: "lighter"}}>{day}</h4>
	  <button style={{
		backgroundColor: "#ECD1D1",
		border: "none",
		borderRadius: "10px",
		height: "65px",
		width: "40px"
		}}></button>
	</div>
  )
}

export default StreakDay
