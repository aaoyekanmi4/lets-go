import React from "react";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import CreateContactForm from "../../../components/forms/CreateContactForm/CreateContactForm.js";
import "../../sharedStyles/formPage.scss";

const CreateContact = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  return (
    <div className="CreateContact">
      <Header />
      <main className="form-page__main">
        <CreateContactForm />
      </main>
    </div>
  );
};

export default CreateContact;
