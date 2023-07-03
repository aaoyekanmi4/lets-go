import React from "react";
import { Link } from "react-router-dom";

import Header from "../../../components/Header/Header.js";
import LoginForm from "../../../components/forms/LoginForm/LoginForm.js";
import "../../sharedStyles//formPage.scss";

const Login = () => {
  return (
    <div className="Login">
      <Header />
      <main className="form-page__main">
        <LoginForm />
        <Link className="form-page__link Form__link-text" to="/register">
          {`Don't have an account? Register`}
        </Link>
      </main>
    </div>
  );
};

export default Login;
