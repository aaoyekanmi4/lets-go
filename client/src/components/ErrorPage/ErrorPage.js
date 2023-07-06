import React from "react";
import { Link } from "react-router-dom";
import Header from "../Header/Header.js";
import "./ErrorPage.scss";

const ErrorPage = ({ message }) => {
  return (
    <div className="ErrorPage">
      <Header />
      <main className="ErrorPage__main">
        <div className="ErrorPage__container container">
          <p className="ErrorPage__message">{message}</p>
          <Link to="/" className="button-text button-text--primary">
            Return to homepage
          </Link>
        </div>
      </main>
    </div>
  );
};

export default ErrorPage;
