import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { getGroups } from "../../../actions";
import TextInput from "../TextInput/TextInput.js";
import ErrorsContainer from "../ErrorsContainer/ErrorsContainer.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import AddContacts from "../../AddContacts/AddContacts.js";
import "./GroupForm.scss";
import "../form.scss";

const GroupForm = ({ type, contacts, initialFormValues, sendData }) => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const user = useSelector((state) => {
    return state.user;
  });

  const [formValues, setFormValues] = useState(initialFormValues);

  const [formErrors, setFormErrors] = useState({});

  const [isFrontendValidated, setIsFrontendValidated] = useState(false);

  const [backendErrors, setBackendErrors] = useState([]);

  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    setFormValues(initialFormValues);
  }, [initialFormValues]);

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

      const response = await sendData(
        { name: formValues.name, appUserId: user.appUserId },
        formValues.contacts.map((contact) => contact.contactId),
        user.jwtToken
      );

      if (response.status === 201 || response.status === 204) {
        await dispatch(getGroups());

        setSuccessMessage("Success! Redirecting you back to groups page...");

        setTimeout(() => {
          navigate(`/groups/${user.appUserId}`);
        }, 1000);
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
    <form className="GroupForm Form" onSubmit={runFrontendValidation}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">
        {type === "create" ? "Create Group" : "Edit Group"}
      </h1>

      <ErrorsContainer errorsArray={backendErrors} />

      {successMessage ? (
        <p className="Form__success-message">{successMessage}</p>
      ) : null}

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

      <AddContacts
        data={contacts}
        error={formErrors.contacts}
        onChange={onInputChange}
        initialSelectedContacts={formValues.contacts}
      />

      <button className="button-main button-main--primary" type="submit">
        {type === "create" ? "Submit" : "Update"}
      </button>
    </form>
  );
};

export default GroupForm;
