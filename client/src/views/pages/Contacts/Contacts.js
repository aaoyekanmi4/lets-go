import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import ModalContainer from "../../../components/ModalContainer/ModalContainer.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import ContactCard from "../../../components/ContactCard/ContactCard.js";
import "./Contacts.scss";

const Contacts = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  const [searchValue, setSearchValue] = useState("");

  useEffect(() => {
    getFilteredContacts();
  }, [searchValue]);

  const getFilteredContacts = () => {
    return contacts.filter((contact) => {
      return (
        contact.firstName.toLowerCase().includes(searchValue.toLowerCase()) ||
        contact.lastName.toLowerCase().includes(searchValue.toLowerCase())
      );
    });
  };

  const renderedContacts = getFilteredContacts().map((contact) => {
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
      <main className="Contacts__main">
        <div className="container">
          <div className="Contacts__search-field-container">
            <SearchField
              placeholder="Search contact..."
              value={searchValue}
              onChange={setSearchValue}
              onSearch={() => {}}
            />
          </div>

          <div className="Contacts__main-container">{renderedContacts}</div>
        </div>
      </main>
    </div>
  );
};

export default Contacts;
