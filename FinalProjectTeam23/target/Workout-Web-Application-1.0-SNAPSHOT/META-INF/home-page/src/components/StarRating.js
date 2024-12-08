import React, { useState } from "react";

const StarRating = () => {
  const [rating, setRating] = useState(null);
  const [hover, setHover] = useState(null);

  return (
    <div>
      {[1, 2, 3, 4, 5].map((star) => {
        return (
          <span
            key={star}
            className="star"
            style={{
              cursor: "pointer",
              color: hover >= star || rating >= star ? "gold" : "gray",
              fontSize: "35px",
            }}
            onClick={() => {
              setRating(star);
            }}
            onMouseEnter={() => {
              setHover(star);
            }}
            onMouseLeave={() => {
              setHover(null);
            }}
          >
            ★
          </span>
        );
      })}
    </div>
  );
};

export default StarRating;
