import React from "react";

import "./GroupCard.scss";

const GroupCard = ({ groupName, contacts }) => {
  return (
    <div className="GroupCard">
      <h2 className="GroupCard__name">{groupName}</h2>
    </div>
  );
};

export default GroupCard;
