import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { loginUser, clearBackendLoginErrors } from "../../../actions";
import { validateField } from "./validator.js";
import { validateAllFields } from "../validators.js";
import TextInput from "../TextInput/TextInput.js";
import { defaultLoginValues } from "../defaultValues.js";
import ErrorsContainer from "../ErrorsContainer/ErrorsContainer.js";
import "./LoginForm.scss";
import "../form.scss";

const LoginForm = () => {
  const dispatch = useDispatch();

  const backendErrors = useSelector((state) => {
    return state.backendLoginErrors || [];
  });

  const isLoggingIn = useSelector((state) => {
    return state.isLoggingIn;
  });

  const [formValues, setFormValues] = useState(defaultLoginValues);

  const [formErrors, setFormErrors] = useState({});

  const [isFrontendValidated, setIsFrontendValidated] = useState(false);

  useEffect(() => {
    if (!isFrontendValidated) {
      return;
    }

    if (Object.values(formErrors).length) {
      setIsFrontendValidated(false);
      return;
    }

    setIsFrontendValidated(false);

    dispatch(loginUser(formValues));
  }, [isFrontendValidated]);

  const onInputChange = (fieldName, fieldValue) => {
    setFormValues((prevState) => {
      return { ...prevState, [fieldName]: fieldValue };
    });
  };

  const validateFrontend = async (e) => {
    e.preventDefault();

    validateAllFields(validateField, formValues, setFormErrors);

    setIsFrontendValidated(true);

    dispatch(clearBackendLoginErrors());
  };

  return (
    <form className="LoginForm Form" onSubmit={validateFrontend}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">Login</h1>
      <ErrorsContainer errorsArray={backendErrors} />
      <TextInput
        type="text"
        id="username"
        label="Username*"
        name="username"
        value={formValues.username}
        onChange={(e) => {
          onInputChange("username", e.target.value);
        }}
        error={formErrors.username}
        onBlur={() => {
          return;
        }}
      />
      <TextInput
        type="password"
        id="password"
        label="Password*"
        name="password"
        value={formValues.password}
        error={formErrors.password}
        onChange={(e) => {
          onInputChange("password", e.target.value);
        }}
        onBlur={() => {
          return;
        }}
      />
      {isLoggingIn ? <p className="Form__text">Logging in user...</p> : null}
      <button
        className="Form__submit-button button-main button-main--primary"
        type="submit"
      >
        Submit
      </button>
    </form>
  );
};

export default LoginForm;
