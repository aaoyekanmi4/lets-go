import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

import Logo from "../Logo/Logo.js";
import TextButtonDropdown from "../TextButtonDropdown/TextButtonDropdown.js";
import SearchField from "../SearchField/SearchField.js";
import { logoutUser, clearAllData } from "../../actions";
import useWindowSize from "../../hooks/useWindowSize.js";
import "./Header.scss";

const Header = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const user = useSelector((state) => {
    return state.user;
  });

  const [showMobileMenu, setShowMobileMenu] = useState(false);

  const windowSize = useWindowSize();

  const onSignOut = () => {
    dispatch(logoutUser());

    dispatch(clearAllData());

    navigate("/login");
  };

  const getMobileMenuLoggedIn = () => {
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
            <h3
              className="color-tertiary"
              onClick={(e) => {
                e.stopPropagation();
              }}
            >
              {user.username}
            </h3>
            <li>
              <div className="Header__search-container-mobile">
                <SearchField placeholder="Search Events..." />
              </div>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to={`/saved-events/${user.appUserId}`}
              >
                My Events
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to={`/contacts/${user.appUserId}`}
              >
                My Contacts
              </Link>
            </li>
            <li>
              <Link
                className="button-text button-text--secondary"
                to={`/groups/${user.appUserId}`}
              >
                My Groups
              </Link>
            </li>
            <li>
              <button
                className="button-text button-text--secondary"
                onClick={onSignOut}
              >
                Sign Out
              </button>
            </li>
          </ul>
        </nav>
      </>
    );
  };

  const getMobileMenuLoggedOut = () => {
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
              <Link className="button-text button-text--secondary" to="/login">
                Sign In
              </Link>
            </li>
          </ul>
        </nav>
      </>
    );
  };

  const getMobileMenu = () => {
    if (user) {
      return getMobileMenuLoggedIn();
    }

    return getMobileMenuLoggedOut();
  };

  const getFullMenuLoggedIn = () => {
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
                <h3
                  className="color-tertiary"
                  onClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  {user.username}
                </h3>
                <Link
                  className="button-text button-text--primary"
                  to={`/saved-events/${user.appUserId}`}
                >
                  My Events
                </Link>
                <Link
                  className="button-text button-text--primary"
                  to={`/contacts/${user.appUserId}`}
                >
                  My Contacts
                </Link>
                <Link
                  className="button-text button-text--primary"
                  to={`/groups/${user.appUserId}`}
                >
                  My Groups
                </Link>
              </TextButtonDropdown>
            </li>
            <li className="Header__link">
              <button
                className="button-outline button-outline--primary"
                onClick={onSignOut}
              >
                {`Sign Out`}
              </button>
            </li>
          </ul>
        </nav>
      </>
    );
  };

  const getFullMenuLoggedOut = () => {
    return (
      <nav className="Header__nav">
        <ul className="Header__links">
          <li>
            <div className="Header__search-container-full">
              <SearchField placeholder="Search Events..." />
            </div>
          </li>
          <li>
            <Link
              className="button-outline button-outline--primary"
              to="/login"
            >
              Sign In
            </Link>
          </li>
        </ul>
      </nav>
    );
  };

  const getFullMenu = () => {
    if (user) {
      return getFullMenuLoggedIn();
    }

    return getFullMenuLoggedOut();
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
