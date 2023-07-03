import React from "react";
import { FaBullhorn } from "react-icons/fa";
import "./GroupCard.scss";

const GroupCard = ({ groupName }) => {
  return (
    <div className="GroupCard">
      <div className="GroupCard__content">
        <h2 className="GroupCard__name">{groupName}</h2>
        <FaBullhorn className="GroupCard__icon" />
      </div>
    </div>
  );
};

export default GroupCard;
