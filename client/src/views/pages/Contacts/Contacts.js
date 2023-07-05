import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import ContactCard from "../../../components/ContactCard/ContactCard.js";
import useResultIndicator from "../../../hooks/useResultIndicator.js";
import getResultIndicator from "../../../getResultIndicator.js";
import "./Contacts.scss";
import "../../sharedStyles/contactsGroups.scss";

let deleteIndicatorTimerId;

const Contacts = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  const [searchValue, setSearchValue] = useState("");

  const [deleteResultIndicator, setDeleteResultIndicator] = useResultIndicator(
    deleteIndicatorTimerId
  );

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

  const sortContacts = (contacts) => {
    contacts.sort((a, b) => {
      if (a.firstName.toLowerCase() < b.firstName.toLowerCase()) {
        return -1;
      } else if (a.firstName.toLowerCase() > b.firstName.toLowerCase()) {
        return 1;
      } else {
        return 0;
      }
    });

    return contacts;
  };

  const renderedContacts = sortContacts(getFilteredContacts()).map(
    (contact) => {
      return (
        <ContactCard
          key={contact.contactId}
          firstName={contact.firstName}
          lastName={contact.lastName}
          phone={contact.phone}
          email={contact.email}
          contactId={contact.contactId}
          setDeleteResultIndicator={setDeleteResultIndicator}
        />
      );
    }
  );

  return (
    <>
      <div className="Contacts">
        <Header />
        <main className="GeneralLayout__main">
          <div className="container">
            <div className="GeneralLayout__search-field-container">
              <SearchField
                placeholder="Search contact..."
                value={searchValue}
                onChange={setSearchValue}
                onSearch={() => {}}
              />
            </div>
            <Link
              to="/contacts/create"
              className="Contacts__add-link button-text button-text--primary"
            >
              + Add Contact
            </Link>

            <div
              className="Contacts__container"
              style={
                renderedContacts.length >= 4
                  ? {
                      gridTemplateColumns:
                        "repeat(auto-fit, minmax(23rem, 1fr))",
                    }
                  : { gridTemplateColumns: "repeat(auto-fit, 26rem" }
              }
            >
              {renderedContacts}
            </div>
          </div>
        </main>
      </div>

      {deleteResultIndicator.show
        ? getResultIndicator(deleteResultIndicator)
        : null}
    </>
  );
};

export default Contacts;
