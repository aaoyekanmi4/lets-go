import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import axios from "axios";

import baseUrls from "../../../baseUrls.js";
import Header from "../../../components/Header/Header.js";
import CreateContactForm from "../../../components/forms/CreateContactForm/CreateContactForm.js";
import "../../sharedStyles/formPage.scss";

const EditContact = () => {
  const { contactId } = useParams();

  console.log(contactId);
  const user = useSelector((state) => {
    return state.user;
  });

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

      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="EditContact">
      <Header />
      <main className="form-page__main">
        <CreateContactForm />
      </main>
    </div>
  );
};

export default EditContact;
