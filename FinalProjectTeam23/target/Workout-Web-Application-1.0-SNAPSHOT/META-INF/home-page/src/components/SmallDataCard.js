import editGray from "../assets/editGray.svg";
import shoe from "../assets/shoe.svg";

const SmallDataCard = ({dataTitle = "Active steps", dataValue = "6835", icon = shoe}) => {
  return (
	<div className="data-card">
		<div style={{
			display: "flex",
			flexDirection: "column",
			justifyContent: "flex-end",
		}}>
			<div style={{flexDirection: "column"}}>
				<div style={{display: "flex"}}>
					<h4 style={{color: "#6c6c6c", fontWeight: "lighter", fontSize: "12px"}}>{dataTitle}</h4>
					<img
					src={editGray}
					alt="Edit Icon"
					style={{ width: 14, marginTop: "-0.3em", marginLeft: "0.4em" }}
					/>
				</div>
					<h2 style={{fontSize: "20px", marginTop: "0.1em"}}>{dataValue}</h2>
			</div>
		</div>
			<img
				src={icon}
				style={{ width: 40, marginLeft: "1em" }}
			/>
	</div>
  )
}

export default SmallDataCard
