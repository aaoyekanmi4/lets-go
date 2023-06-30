import React, { useState, useEffect } from "react";
import { ImCheckmark } from "react-icons/im";

import SearchField from "../SearchField/SearchField.js";
import "./AddContacts.scss";

const AddContacts = ({ data, onChange }) => {
  const [allContacts, setAllContacts] = useState(data);

  const [suggestedContacts, setSuggestedContacts] = useState(
    allContacts.slice(0, 20)
  );

  const [selectedContacts, setSelectedContacts] = useState([]);

  const [searchValue, setSearchValue] = useState("");

  useEffect(() => {
    setAllContacts(data);
    setSuggestedContacts(data.slice(0, 20));
  }, [data]);

  //when selectedContacts change, update contacts values into in parent createGroup form
  useEffect(() => {
    onChange("contacts", selectedContacts);
  }, [selectedContacts]);

  //onSearch update the suggested contacts so we can render them
  const onSearch = () => {
    const suggestedContacts = allContacts.filter((contact) => {
      return contact.toLowerCase().includes(searchValue.toLowerCase());
    });

    setSuggestedContacts(suggestedContacts);
  };

  //when we check a suggested contact, add it to selected
  const renderedSuggested = suggestedContacts.map((contact, index) => {
    return (
      <div className="AddContacts__result" key={index}>
        <div className="AddContacts__pic-name">
          <input
            className="AddContacts__checkbox"
            type="checkbox"
            name="contact"
            id={index}
            value={contact}
            checked={selectedContacts.includes(contact)}
            onChange={(e) => {
              if (e.target.checked) {
                setSelectedContacts([...selectedContacts, e.target.value]);
              } else {
                setSelectedContacts(
                  selectedContacts.filter((contact) => {
                    return contact != e.target.value;
                  })
                );
              }
            }}
          />
          {contact}
        </div>
        <label
          className={`AddContacts__circle ${
            selectedContacts.includes(contact)
              ? "AddContacts__circle--selected"
              : null
          }`}
          htmlFor={index}
        >
          <ImCheckmark className="AddContacts__icon" />
          {/* <span className="AddedContact__circle"></span> */}
        </label>
      </div>
    );
  });

  const renderedSelected = selectedContacts.map((contact, index) => {
    return (
      <div className="AddContacts__selected" key={index}>
        <p>hi</p>
        <p>{contact}</p>
      </div>
    );
  });

  return (
    <div className="AddContacts">
      <p>Add Contacts</p>
      <SearchField
        placeholder="Search contact..."
        onChange={setSearchValue}
        value={searchValue}
        onSearch={onSearch}
      />
      <div className="AddContacts__view">{renderedSelected}</div>
      <div className="AddContacts__suggestions">{renderedSuggested}</div>
    </div>
  );
};

export default AddContacts;
