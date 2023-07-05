import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

import { getEditContactFunction, findContact } from "./helpers.js";

import { defaultCreateContactValues } from "../../../components/forms/defaultValues.js";
import Header from "../../../components/Header/Header.js";
import ContactForm from "../../../components/forms/ContactForm/ContactForm.js";
import "../../sharedStyles/formPage.scss";

const EditContact = () => {
  const { contactId } = useParams();

  const user = useSelector((state) => {
    return state.user;
  });

  const [formValues, setFormValues] = useState(defaultCreateContactValues);

  const [errors, setErrors] = useState(null);

  useEffect(() => {
    findContact(user.jwtToken, contactId, setFormValues, setErrors);
  }, []);

  const editContact = getEditContactFunction(contactId);

  return (
    <div className="EditContact">
      <Header />
      <main className="form-page__main">
        <ContactForm
          initialFormValues={formValues}
          type="edit"
          sendData={editContact}
        />
      </main>
    </div>
  );
};

export default EditContact;
