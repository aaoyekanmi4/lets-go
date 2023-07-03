import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { AiOutlineMail } from "react-icons/ai";
import { BsTelephone, BsPencil } from "react-icons/bs";
import { FaTrashCan } from "react-icons/fa6";

import DeleteContactModal from "../../views/pages/Contacts/DeleteContactModal/DeleteContactModal.js";
import { deleteContact } from "./helpers.js";
import { getContacts } from "../../actions";
import "./ContactCard.scss";

const ContactCard = ({ contactId, firstName, lastName, phone, email }) => {
  const dispatch = useDispatch();

  const user = useSelector((state) => {
    return state.user;
  });

  const [showDeleteContactModal, setShowDeleteContactModal] = useState(false);

  const [backendErrors, setBackendErrors] = useState([]);

  const onDelete = async () => {
    const response = await deleteContact(contactId, user.jwtToken);

    if (response.status === 204) {
      await dispatch(getContacts());

      setShowDeleteContactModal(false);
    } else {
      setBackendErrors(response.errorMessages);
    }
  };

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
    <>
      <div className="ContactCard">
        <div className="ContactCard__upper-border"></div>
        <div className="ContactCard__lower-border"></div>
        <BsPencil className="ContactCard__action-button ContactCard__pencil" />
        <button
          className="ContactCard__action-button ContactCard__trash"
          onClick={() => {
            setShowDeleteContactModal(true);
          }}
        >
          <FaTrashCan />
        </button>

        <div className="ContactCard__circle-gradient">
          <div className="ContactCard__circle">
            <p className="ContactCard__letter">{firstName.split("")[0]}</p>
            <p className="ContactCard__letter">{lastName.split("")[0]}</p>
          </div>
        </div>
        <h2 className="ContactCard__name">{`${firstName} ${lastName}`}</h2>
        <div className="ContactCard__contact-method">
          <BsTelephone className="ContactCard__icon" />
          <a href={`tel:+${phone}`} className="ContactCard__contact-value">
            {getUpdatedPhoneFormat()}
          </a>
        </div>
        <div className="ContactCard__contact-method">
          <AiOutlineMail className="ContactCard__icon" />
          <a href={`mailto:${email}`} className="ContactCard__contact-value">
            {email}
          </a>
        </div>
      </div>

      {showDeleteContactModal ? (
        <DeleteContactModal
          closeModal={() => {
            setShowDeleteContactModal(false);
          }}
          onDelete={onDelete}
          firstName={firstName}
          lastName={lastName}
          email={email}
        />
      ) : null}
    </>
  );
};

export default ContactCard;
