import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import TextInput from "../TextInput/TextInput.js";
import { defaultRegisterValues } from "../defaultValues.js";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import { createUser, clearBackendRegisterErrors } from "../../../actions";
import ErrorsContainer from "../ErrorsContainer/ErrorsContainer.js";
import "./RegisterForm.scss";
import "../form.scss";

const RegisterForm = () => {
  console.log("dssss");
  const dispatch = useDispatch();

  const backendErrors = useSelector((state) => {
    return state.backendRegisterErrors || [];
  });

  const [formValues, setFormValues] = useState(defaultRegisterValues);

  const [formErrors, setFormErrors] = useState({});

  const [isFrontendValidated, setIsFrontendValidated] = useState(false);

  //sends form data to backend if frontend has been validated and
  // there are no errors with user inputted data
  useEffect(() => {
    if (!isFrontendValidated) {
      return;
    }

    if (Object.values(formErrors).length) {
      setIsFrontendValidated(false);
      return;
    }

    setIsFrontendValidated(false);

    dispatch(createUser(formValues));
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
    <form className="RegisterForm Form" onSubmit={runFrontendValidation}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">Register</h1>
      <ErrorsContainer errorsArray={backendErrors} />
      <div className="Form__double-group">
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
      </div>

      <div className="Form__double-group">
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
            const errors = validateField(
              formValues,
              { ...formErrors },
              "email"
            );
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
            const errors = validateField(
              formValues,
              { ...formErrors },
              "phone"
            );
            setFormErrors(errors);
          }}
        />
      </div>

      <div className="Form__double-group">
        <TextInput
          type="text"
          id="username"
          label="Username*"
          name="username"
          value={formValues.username}
          error={formErrors.username}
          onChange={(e) => {
            onInputChange("username", e.target.value);
          }}
          onBlur={() => {
            const errors = validateField(
              formValues,
              { ...formErrors },
              "username"
            );
            setFormErrors(errors);
          }}
        />
        <TextInput
          type="text"
          id="password"
          label="Password*"
          name="password"
          value={formValues.password}
          error={formErrors.password}
          onChange={(e) => {
            onInputChange("password", e.target.value);
          }}
          onBlur={() => {
            const errors = validateField(
              formValues,
              { ...formErrors },
              "password"
            );
            setFormErrors(errors);
          }}
        />
      </div>

      <button className="button-main button-main--primary" type="submit">
        Submit
      </button>
    </form>
  );
};

export default RegisterForm;
