import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { loginUser } from "../../../actions";
import TextInput from "../TextInput/TextInput.js";
import { defaultLoginValues } from "../defaultValues.js";
import "./LoginForm.scss";
import "../form.scss";

const LoginForm = () => {
  const dispatch = useDispatch();

  const [formValues, setFormValues] = useState(defaultLoginValues);

  const [formErrors, setFormErrors] = useState({});

  const onInputChange = (fieldName, fieldValue) => {
    setFormValues((prevState) => {
      return { ...prevState, [fieldName]: fieldValue };
    });
  };

  const onFormSubmit = async (e) => {
    e.preventDefault();
    await dispatch(loginUser(formValues));
  };

  return (
    <form className="LoginForm Form" onSubmit={onFormSubmit}>
      <div className="Form__upper-style"></div>
      <h1 className="Form__header">Login</h1>
      <TextInput
        type="text"
        id="username"
        label="Username*"
        name="username"
        value={formValues.username}
        onChange={(e) => {
          onInputChange("username", e.target.value);
        }}
        onBlur={() => {
          return;
        }}
      />
      <TextInput
        type="text"
        id="password"
        label="Password*"
        name="password"
        value={formValues.password}
        onChange={(e) => {
          onInputChange("password", e.target.value);
        }}
        onBlur={() => {
          return;
        }}
      />
      <button className="button-main button-main--primary" type="submit">
        Submit
      </button>
    </form>
  );
};

export default LoginForm;
