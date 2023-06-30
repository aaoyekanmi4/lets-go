import React, { useState, useEffect } from "react";

import SearchField from "../SearchField/SearchField.js";

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

  useEffect(() => {
    onChange("contacts", selectedContacts);
  }, [selectedContacts]);

  const onSearch = () => {
    const suggestedContacts = allContacts.filter((contact) => {
      return contact.toLowerCase().includes(searchValue.toLowerCase());
    });

    setSuggestedContacts(suggestedContacts);
  };

  const renderedSuggested = suggestedContacts.map((contact, index) => {
    return (
      <div key={index}>
        <input
          type="checkbox"
          name="contact"
          value={contact}
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
    );
  });

  const renderedSelected = selectedContacts.map((contact, index) => {
    return <div key={index}>{contact}</div>;
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
