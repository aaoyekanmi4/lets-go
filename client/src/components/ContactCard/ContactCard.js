import React from "react";

import "./ContactCard.scss";

const ContactCard = ({ firstName, lastName, phone, email, groups }) => {
  return (
    <div className="ContactCard">
      <div className="ContactCard__upper-border"></div>
      <div className="ContactCard__lower-border"></div>
      <div className="ContactCard__circle">
        <p className="ContactCard__letter">{firstName.split("")[0]}</p>
      </div>
      <h2 className="ContactCard__name">{`${firstName} ${lastName}`}</h2>
    </div>
  );
};

export default ContactCard;
