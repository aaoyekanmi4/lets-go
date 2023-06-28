import React from "react";
import { FaBullhorn } from "react-icons/fa";

import "./Logo.scss";
const Logo = () => {
  return (
    <div className="Logo">
      <p className="logo">{`Let's go`}</p>
      <FaBullhorn className="Logo__icon logo" />
    </div>
  );
};

export default Logo;
