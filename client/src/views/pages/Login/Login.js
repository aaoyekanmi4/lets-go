import React from "react";
import { Link } from "react-router-dom";
import Logo from "../../../components/Logo/Logo.js";
import Header from "../../../components/Header/Header.js";
import LoginForm from "../../../components/forms/LoginForm/LoginForm.js";
import "./Login.scss";

const Login = () => {
  return (
    <div className="Login">
      <Header />
      <main className="Entry__main">
        <LoginForm />
        <Link className="Entry__link Form__link-text" to="/register">
          {`Don't have an account? Register`}
        </Link>
      </main>
    </div>
  );
};

export default Login;
