import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import TextInput from "../TextInput/TextInput.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import AddContacts from "../../AddContacts/AddContacts.js";
import { createGroup } from "./helpers.js";
import "./CreateGroupForm.scss";
import "../form.scss";

const CreateGroupForm = ({ contacts }) => {
  const user = useSelector((state) => {
    return state.user;
  });

  const navigate = useNavigate();

  const [formValues, setFormValues] = useState({
    name: "",
    contacts: [],
  });

  const [formErrors, setFormErrors] = useState({});

  const [isFrontendValidated, setIsFrontendValidated] = useState(false);

  const [backendErrors, setBackendErrors] = useState([]);

  useEffect(() => {
    const run = async () => {
      if (!isFrontendValidated) {
        return;
      }

      if (Object.values(formErrors).length) {
        setIsFrontendValidated(false);
        return;
      }

      setIsFrontendValidated(false);

      const response = await createGroup(
        { ...formValues, appUserId: user.appUserId },
        user.jwtToken
      );

      if (response.status === 201) {
        navigate(`/groups/${user.appUserId}`);
      } else {
        setBackendErrors(response.errorMessages);
      }
    };

    run();
  }, [isFrontendValidated]);

  const onInputChange = (fieldName, fieldValue) => {
    setFormValues((prevState) => {
      return { ...prevState, [fieldName]: fieldValue };
    });
  };

  const runFrontendValidation = (e) => {
    e.preventDefault();

    validateAllFields(validateField, formValues, setFormErrors);

    setIsFrontendValidated(true);
  };

  return (
    <form
      className="CreateGroupForm Form"
      onSubmit={(e) => {
        runFrontendValidation();
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

export default CreateGroupForm;
