import React from "react";
import { Link } from "react-router-dom";

import Logo from "../Logo/Logo.js";
import "./Header.scss";

const Header = () => {
  return (
    <header className="Header">
      <div className="Header__container container">
        <Logo />
        <div className="Header_actions">
          <nav className="Header__nav">
            <ul className="Header__links">
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  My Contacts
                </Link>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  Create Event
                </Link>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  My Groups
                </Link>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  My Events
                </Link>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  My Account
                </Link>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
                  {" "}
                  Sign Out
                </Link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  );
};

export default Header;
