import React from "react";
import { Link } from "react-router-dom";

import Header from "../../../components/Header/Header.js";

import RegisterForm from "../../../components/forms/RegisterForm/RegisterForm.js";
import "../../sharedStyles/formPage.scss";

const Register = () => {
  return (
    <div className="Register">
      <Header />
      <main className="form-page__main">
        <RegisterForm />
        <Link className="form-page__link Form__link-text" to="/login">
          Already registered? Login
        </Link>
      </main>
    </div>
  );
};

export default Register;
