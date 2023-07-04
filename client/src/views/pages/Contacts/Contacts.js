import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import ContactCard from "../../../components/ContactCard/ContactCard.js";
import { AiOutlinePlus } from "react-icons/ai";
import ResultIndicator from "../../../components/ResultIndicator/ResultIndicator.js";
import "./Contacts.scss";
import "../../sharedStyles/contactsGroups.scss";

let deleteIndicatorTimerId;

const Contacts = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  const [searchValue, setSearchValue] = useState("");

  const [showDeleteResultIndicator, setShowDeleteResultIndicator] = useState({
    show: false,
    type: "",
  });

  useEffect(() => {
    getFilteredContacts();
  }, [searchValue]);

  //remove the delete result indicator after 2seconds
  useEffect(() => {
    if (showDeleteResultIndicator.show) {
      clearTimeout(deleteIndicatorTimerId);

      deleteIndicatorTimerId = setTimeout(() => {
        setShowDeleteResultIndicator({ show: false, type: "" });
      }, 2000);
    }
  }, [showDeleteResultIndicator.show]);

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
        contactId={contact.contactId}
        setDeleteResultIndicator={setShowDeleteResultIndicator}
      />
    );
  });

  const getDeleteResultIndicator = (type) => {
    if (type === "success") {
      return <ResultIndicator type={type} message="Successfully deleted!" />;
    }

    return <ResultIndicator type={type} message="Unable to delete" />;
  };

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
            <Link to="/contacts/create">
              <AiOutlinePlus className="GeneralLayout__add-icon" />
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

      {showDeleteResultIndicator.show
        ? getDeleteResultIndicator(showDeleteResultIndicator.type)
        : null}
    </>
  );
};

export default Contacts;
// grid-template-columns: repeat(auto-fit, 23rem);
// justify-content: center;
