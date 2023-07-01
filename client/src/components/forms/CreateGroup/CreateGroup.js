import React, { useState, useEffect } from "react";

import TextInput from "../TextInput/TextInput.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import AddContacts from "../../AddContacts/AddContacts.js";
import "./CreateGroup.scss";
import "../form.scss";

const CreateGroup = ({ contacts }) => {
  const [formValues, setFormValues] = useState({
    name: "",
    contacts: [],
  });

  console.log(formValues);
  const [formErrors, setFormErrors] = useState({});

  const onInputChange = (fieldName, fieldValue) => {
    setFormValues((prevState) => {
      return { ...prevState, [fieldName]: fieldValue };
    });
  };

  return (
    <form
      className="CreateGroup Form"
      onSubmit={(e) => {
        e.preventDefault();
        validateAllFields(validateField, formValues, setFormErrors);
      }}
    >
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">Create Group</h1>
      <TextInput
        type="text"
        id="group-name"
        label="Group Name*"
        name="name"
        value={formValues.name}
        onChange={(e) => {
          onInputChange("name", e.target.value);
        }}
        onBlur={() => {
          const errors = validateField(formValues, { ...formErrors }, "name");
          setFormErrors(errors);
        }}
        error={formErrors.name}
      />

      <AddContacts data={contacts} onChange={onInputChange} />

      <button className="button-main button-main--primary" type="submit">
        Submit
      </button>
    </form>
  );
};

export default CreateGroup;
