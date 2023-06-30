import React, { useState } from "react";
import { Link } from "react-router-dom";
import moment from "moment";

import Logo from "../Logo/Logo.js";
import TextButtonDropdown from "../TextButtonDropdown/TextButtonDropdown.js";
import SearchField from "../SearchField/SearchField.js";
import useWindowSize from "../../hooks/useWindowSize.js";
import "./Header.scss";

const Header = () => {
  const [showMobileMenu, setShowMobileMenu] = useState(false);

  const windowSize = useWindowSize();

  const getMobileMenu = () => {
    return (
      <>
        <nav className="Header__mobile-nav">
          <button
            className="Header__mobile-button"
            onClick={() => {
              setShowMobileMenu(!showMobileMenu);
            }}
          >
            <span className="Header__hamburger"></span>
          </button>
          <ul
            className={`Header__mobile-links ${
              showMobileMenu
                ? "Header__mobile-links--on-screen"
                : "Header__mobile-links--off-screen"
            }`}
          >
            <li>
              <div className="Header__search-container-mobile">
                <SearchField placeholder="Search Events..." />
              </div>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to="/contacts"
              >
                My Account
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to="/contacts"
              >
                My Events
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to="/contacts"
              >
                My Contacts
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to="/contacts"
              >
                My Groups
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to="/contacts"
              >
                Sign Out
              </Link>
            </li>
          </ul>
        </nav>
      </>
    );
  };

  const getFullMenu = () => {
    return (
      <>
        <nav className="Header__nav">
          <ul className="Header__links">
            <li>
              <div className="Header__search-container-full">
                <SearchField placeholder="Search Events..." />
              </div>
            </li>
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
      </>
    );
  };

  return (
    <header className="Header">
      <div className="Header__container">
        <Logo />
        <div className="Header__actions">
          {windowSize < 768 ? getMobileMenu() : getFullMenu()}
        </div>
      </div>
    </header>
  );
};

export default Header;
