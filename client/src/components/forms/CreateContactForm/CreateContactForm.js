import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import TextInput from "../TextInput/TextInput.js";
import ErrorsContainer from "../ErrorsContainer/ErrorsContainer.js";
import { defaultCreateContactValues } from "../defaultValues.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import { createContact } from "./helpers.js";
import { getContacts } from "../../../actions";
import "./CreateContactForm.scss";
import "../form.scss";

const CreateContactForm = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const user = useSelector((state) => {
    return state.user;
  });

  const [formValues, setFormValues] = useState(defaultCreateContactValues);

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

      const response = await createContact(
        { ...formValues, appUserId: user.appUserId },
        user.jwtToken
      );

      if (response.status === 201) {
        await dispatch(getContacts());

        navigate(`/contacts/${user.appUserId}`);
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
    <form className="CreateContactForm Form" onSubmit={runFrontendValidation}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">Create Contact</h1>
      <ErrorsContainer errorsArray={backendErrors} />
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
      <button className="button-main button-main--primary" type="submit">
        Submit
      </button>
    </form>
  );
};

export default CreateContactForm;
