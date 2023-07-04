import React from "react";
import { createContact } from "./helpers";

import { defaultCreateContactValues } from "../../../components/forms/defaultValues.js";
import Header from "../../../components/Header/Header.js";
import ContactForm from "../../../components/forms/ContactForm/ContactForm.js";
import "../../sharedStyles/formPage.scss";

const CreateContact = () => {
  return (
    <div className="CreateContact">
      <Header />
      <main className="form-page__main">
        <ContactForm
          initialFormValues={defaultCreateContactValues}
          type="create"
          sendData={createContact}
        />
      </main>
    </div>
  );
};

export default CreateContact;
