import React, { useState, useEffect } from "react";

import TextInput from "../TextInput/TextInput.js";
import { defaultCreateContactValues } from "../defaultValues.js";
import "../form.scss";

const CreateContact = () => {
  const [formValues, setFormValues] = useState(defaultCreateContactValues);

  const [formErrors, setFormErrors] = useState({});

  const onInputChange = (fieldName, fieldValue) => {
    setFormValues((prevState) => {
      return { ...prevState, [fieldName]: fieldValue };
    });
  };

  return (
    <form className="CreateContact Form">
      <h1 className="Form__header">Create Contact</h1>
      <TextInput
        type="text"
        id="first-name"
        label="First Name"
        name="firstName"
        value={formValues.firstName}
        onChange={(e) => {
          onInputChange("firstName", e.target.value);
        }}
        error={formErrors.firstName}
      />
      <TextInput
        type="text"
        id="last-name"
        label="Last Name"
        name="lastName"
        value={formValues.lastName}
        error={formErrors.lastName}
        onChange={(e) => {
          onInputChange("lastName", e.target.value);
        }}
      />
      <TextInput
        type="text"
        id="email"
        label="Email"
        name="email"
        value={formValues.email}
        error={formErrors.email}
        onChange={(e) => {
          onInputChange("email", e.target.value);
        }}
      />
      <TextInput
        type="text"
        id="phone"
        label="Phone"
        name="phone"
        value={formValues.phone}
        error={formErrors.phone}
        onChange={(e) => {
          onInputChange("phone", e.target.value);
        }}
      />
    </form>
  );
};

export default CreateContact;
