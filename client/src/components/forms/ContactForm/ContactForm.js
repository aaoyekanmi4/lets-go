import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import TextInput from "../TextInput/TextInput.js";
import ErrorsContainer from "../ErrorsContainer/ErrorsContainer.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import {
  getContacts,
  clearBackendRegisterErrors,
} from "../../../actions/index.js";
import "./ContactForm.scss";
import "../form.scss";

const ContactForm = ({ type, initialFormValues, sendData }) => {
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
        { ...formValues, appUserId: user.appUserId },
        user.jwtToken
      );

      if (response.status === 201 || response.status === 204) {
        await dispatch(getContacts());

        setBackendErrors([]);

        setSuccessMessage("Success! Redirecting you back to contacts page");

        setTimeout(() => {
          navigate(`/contacts/${user.appUserId}`);
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

    dispatch(clearBackendRegisterErrors());
  };

  return (
    <form className="ContactForm Form" onSubmit={runFrontendValidation}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">
        {type === "create" ? "Create Contact" : "Edit Contact"}
      </h1>

      <ErrorsContainer errorsArray={backendErrors} />

      {successMessage ? (
        <p className="Form__success-message">{successMessage}</p>
      ) : null}

      <TextInput
        type="text"
        id="first-name"
        label="First Name*"
        name="firstName"
        value={formValues.firstName}
        onChange={(e) => {
          onInputChange("firstName", e.target.value);
        }}
        onBlur={() => {
          const errors = validateField(
            formValues,
            { ...formErrors },
            "firstName"
          );
          setFormErrors(errors);
        }}
        error={formErrors.firstName}
      />
      <TextInput
        type="text"
        id="last-name"
        label="Last Name*"
        name="lastName"
        value={formValues.lastName}
        error={formErrors.lastName}
        onChange={(e) => {
          onInputChange("lastName", e.target.value);
        }}
        onBlur={() => {
          const errors = validateField(
            formValues,
            { ...formErrors },
            "lastName"
          );
          setFormErrors(errors);
        }}
      />
      <TextInput
        type="text"
        id="email"
        label="Email*"
        name="email"
        value={formValues.email}
        error={formErrors.email}
        onChange={(e) => {
          onInputChange("email", e.target.value);
        }}
        onBlur={() => {
          const errors = validateField(formValues, { ...formErrors }, "email");
          setFormErrors(errors);
        }}
      />
      <TextInput
        type="text"
        id="phone"
        label="Phone*"
        name="phone"
        value={formValues.phone}
        error={formErrors.phone}
        onChange={(e) => {
          onInputChange("phone", e.target.value);
        }}
        onBlur={() => {
          const errors = validateField(formValues, { ...formErrors }, "phone");
          setFormErrors(errors);
        }}
      />
      <button
        className="Form__submit-button button-main button-main--primary"
        type="submit"
      >
        {type === "create" ? "Submit" : "Update"}
      </button>
    </form>
  );
};

export default ContactForm;
