import React from "react";
import { Link } from "react-router-dom";
import { FaBullhorn } from "react-icons/fa";

import "./Logo.scss";
const Logo = () => {
  return (
    <Link to="/" className="Logo">
      <span className="logo">{`Let's go`}</span>
      <FaBullhorn className="Logo__icon logo" />
    </Link>
  );
};

export default Logo;
