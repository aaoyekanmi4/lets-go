import React from "react";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import ContactCard from "../../../components/ContactCard/ContactCard.js";
import "./Contacts.scss";

const Contacts = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  const renderedContacts = contacts.map((contact) => {
    return (
      <ContactCard
        key={contact.contactId}
        firstName={contact.firstName}
        lastName={contact.lastName}
        phone={contact.phone}
        email={contact.email}
      />
    );
  });

  return (
    <div className="Contacts">
      <Header />
      <div className="Contacts__container container">{renderedContacts}</div>
    </div>
  );
};

export default Contacts;
