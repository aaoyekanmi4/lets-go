import React, { useEffect, useState } from "react";

import { defaultGroupValues } from "../../../../components/forms/defaultValues.js";
import ModalContainer from "../../../../components/ModalContainer/ModalContainer.js";
import { findGroup } from "../../EditGroup/helpers.js";
import "./ViewGroupModal.scss";

const ViewGroupModal = ({ closeModal, groupId, jwtToken }) => {
  const [values, setValues] = useState(defaultGroupValues);

  const [errors, setErrors] = useState([]);

  useEffect(() => {
    findGroup(jwtToken, groupId, setValues, setErrors);
  }, []);

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

  const renderedContacts = sortContacts(values.contacts).map((contact) => {
    return (
      <p
        className="ViewGroupModal__contact"
        key={contact.contactId}
      >{`${contact.firstName} ${contact.lastName}`}</p>
    );
  });

  return (
    <ModalContainer closeModal={closeModal}>
      <div className="ViewGroupModal">
        <h2 className="ViewGroupModal__group-name">{values.name}</h2>
        <div className="ViewGroupModal__contacts">
          <h3 className="text-center">Contacts In This Group</h3>
          <div className="ViewGroupModal__contacts-container">
            {renderedContacts}
          </div>
        </div>
      </div>
    </ModalContainer>
  );
};

export default ViewGroupModal;
