import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import axios from "axios";

import { defaultCreateContactValues } from "../../../components/forms/defaultValues.js";
import baseUrls from "../../../baseUrls.js";
import Header from "../../../components/Header/Header.js";
import CreateContactForm from "../../../components/forms/ContactForm/ContactForm.js";
import "../../sharedStyles/formPage.scss";

const EditContact = () => {
  const { contactId } = useParams();

  const user = useSelector((state) => {
    return state.user;
  });

  const [formValues, setFormValues] = useState(defaultCreateContactValues);

  const [errors, setErrors] = useState(null);

  useEffect(() => {
    findContact();
  }, []);

  const findContact = async () => {
    try {
      const response = await axios.get(
        `${baseUrls.database}/api/contact/${contactId}`,
        {
          headers: {
            Authorization: `Bearer ${user.jwtToken}`,
          },
        }
      );

      const { firstName, lastName, email, phone } = response.data;

      setFormValues({ firstName, lastName, email, phone });
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="EditContact">
      <Header />
      <main className="form-page__main">
        <CreateContactForm initialFormValues={formValues} type="edit" />
      </main>
    </div>
  );
};

export default EditContact;
