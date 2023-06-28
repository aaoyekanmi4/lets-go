import React, { useState } from "react";
import { BsFillCaretDownFill } from "react-icons/bs";

import "./TextButtonDropdown.scss";

const TextButtonDropdown = ({ buttonName, children }) => {
  const [showDropdownOptions, setShowDropdownOptions] = useState(false);

  // const renderedOptions = options.map((option, index) => {
  //   return <span key={index}>option</span>;
  // });

  return (
    <div
      className="TextButtonDropdown"
      onClick={() => {
        setShowDropdownOptions(!showDropdownOptions);
      }}
    >
      <div className="TextButtonDropdown__view button-outline button-outline--primary">
        <p>{buttonName}</p>
        <BsFillCaretDownFill className="TextButtonDropdown__icon" />
      </div>
      <div
        className={
          showDropdownOptions
            ? `TextButtonDropdown__options--open`
            : `TextButtonDropdown__options--closed`
        }
      >
        {children}
      </div>
    </div>
  );
};

export default TextButtonDropdown;
