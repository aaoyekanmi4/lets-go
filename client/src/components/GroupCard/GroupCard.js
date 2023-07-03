import React from "react";
import { FaBullhorn } from "react-icons/fa";
import "./GroupCard.scss";

const GroupCard = ({ groupName }) => {
  return (
    <button className="GroupCard">
      <span className="GroupCard__content">
        <span className="GroupCard__name">{groupName}</span>
        <FaBullhorn className="GroupCard__icon" />
      </span>
    </button>
  );
};

export default GroupCard;
