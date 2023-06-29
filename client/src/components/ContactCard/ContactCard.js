import React from "react";
import { AiOutlineMail } from "react-icons/ai";
import { BsTelephone } from "react-icons/bs";
import "./ContactCard.scss";

const ContactCard = ({ firstName, lastName, phone, email, groups }) => {
  const getUpdatedPhoneFormat = () => {
    let newPhone = phone;
    if (phone.length === 10) {
      newPhone =
        phone.substring(0, 3) +
        "-" +
        phone.substring(3, 6) +
        "-" +
        phone.substring(6, 10);
    }
    return newPhone;
  };

  return (
    <div className="ContactCard">
      <div className="ContactCard__upper-border"></div>
      <div className="ContactCard__lower-border"></div>
      <div className="ContactCard__circle">
        <p className="ContactCard__letter">{firstName.split("")[0]}</p>
      </div>
      <h2 className="ContactCard__name">{`${firstName} ${lastName}`}</h2>
      <div className="ContactCard__contact-method">
        <BsTelephone className="ContactCard__icon" />
        <p className="ContactCard__contact-value">{getUpdatedPhoneFormat()}</p>
      </div>
      <div className="ContactCard__contact-method">
        <AiOutlineMail className="ContactCard__icon" />
        <p className="ContactCard__contact-value">{email}</p>
      </div>
    </div>
  );
};

export default ContactCard;
