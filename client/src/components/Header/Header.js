import React from "react";
import { Link } from "react-router-dom";

import Logo from "../Logo/Logo.js";
import TextButtonDropdown from "../TextButtonDropdown/TextButtonDropdown.js";

import "./Header.scss";

const Header = () => {
  return (
    <header className="Header">
      <div className="Header__container container">
        <Logo />
        <div className="Header_actions">
          <nav className="Header__nav">
            <ul className="Header__links">
              <li>
                <TextButtonDropdown buttonName="My Personals">
                  <Link
                    className="button-text button-text--primary"
                    to="/contacts"
                  >
                    My Account
                  </Link>
                  <Link
                    className="button-text button-text--primary"
                    to="/contacts"
                  >
                    My Events
                  </Link>
                  <Link
                    className="button-text button-text--primary"
                    to="/contacts"
                  >
                    My Contacts
                  </Link>
                  <Link
                    className="button-text button-text--primary"
                    to="/contacts"
                  >
                    My Groups
                  </Link>
                </TextButtonDropdown>
              </li>
              <li className="Header__link">
                <Link
                  className="button-outline button-outline--primary"
                  to="/contacts"
                >
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
