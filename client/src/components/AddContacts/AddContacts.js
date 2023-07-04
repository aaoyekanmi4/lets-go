import React, { useState, useEffect } from "react";
import { ImCheckmark } from "react-icons/im";

import SearchField from "../SearchField/SearchField.js";
import "./AddContacts.scss";

const AddContacts = ({ data, onChange, error, initialSelectedContacts }) => {
  const [allContacts, setAllContacts] = useState(data);

  const [suggestedContacts, setSuggestedContacts] = useState(
    allContacts.slice(0, 20)
  );

  const [selectedContacts, setSelectedContacts] = useState(
    initialSelectedContacts
  );

  const [searchValue, setSearchValue] = useState("");

  useEffect(() => {
    setAllContacts(data);
    setSuggestedContacts(data.slice(0, 20));
  }, [data]);

  //when initialSelectedContacts changes,so from [] to [{}] update selected contacts
  useEffect(() => {
    setSelectedContacts(initialSelectedContacts);
  }, [initialSelectedContacts]);

  //when selectedContacts change, update the contacts values the in parent createGroupForm
  //component
  useEffect(() => {
    onChange("contacts", selectedContacts);
  }, [selectedContacts]);

  //when search value changes, get filteredResults/suggested contacts
  useEffect(() => {
    onSearch();
  }, [searchValue]);

  //update the suggested contacts so we can render them
  const onSearch = () => {
    const suggestedContacts = allContacts.filter((contact) => {
      const contactName = `${contact.firstName} ${contact.lastName}`;

      return contactName.toLowerCase().includes(searchValue.toLowerCase());
    });

    setSuggestedContacts(suggestedContacts);
  };

  const isContactSelected = (contactId) => {
    return selectedContacts.some((contact) => {
      return contact.contactId === contactId;
    });
  };

  const renderedSuggested = suggestedContacts.map((contact) => {
    const { contactId, firstName, lastName } = contact;

    return (
      <div className="AddContacts__result" key={contactId}>
        <div className="AddContacts__pic-name">
          <input
            className="AddContacts__checkbox"
            type="checkbox"
            name="contact"
            id={contactId}
            value={contact}
            checked={isContactSelected(contactId)}
            onChange={(e) => {
              if (e.target.checked) {
                setSelectedContacts([...selectedContacts, contact]);
              } else {
                setSelectedContacts(
                  selectedContacts.filter((contact) => {
                    return contact.contactId != contactId;
                  })
                );
              }
            }}
          />
          {`${firstName} ${lastName}`}
        </div>
        <label
          className={`AddContacts__circle ${
            isContactSelected(contactId)
              ? "AddContacts__circle--selected"
              : null
          }`}
          htmlFor={contactId}
        >
          <ImCheckmark className="AddContacts__icon" />
        </label>
      </div>
    );
  });

  const renderedSelected = selectedContacts.map((contact, index) => {
    return (
      <div className="AddContacts__selected" key={index}>
        <p className="AddContacts__selected-circle">
          {contact.firstName.split("")[0]}
        </p>
        <p className="AddContacts__selected-name">{`${contact.firstName} ${contact.lastName}`}</p>
      </div>
    );
  });

  return (
    <div className="AddContacts">
      <p className="Form__label">Add Contacts*</p>
      <SearchField
        placeholder="Search contact..."
        onChange={setSearchValue}
        value={searchValue}
        onSearch={onSearch}
      />
      <span className="AddContacts__error">{error}</span>
      <div className="AddContacts__view">{renderedSelected}</div>
      <div className="AddContacts__suggestions">
        <p className="AddContacts__detail-text">
          {searchValue ? "Results" : "Suggested"}
        </p>
        {renderedSuggested}
      </div>
    </div>
  );
};

export default AddContacts;
