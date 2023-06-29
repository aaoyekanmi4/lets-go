import React, { useState } from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";

import "./SearchField.scss";

const SearchField = ({ placeholder, onSearch }) => {
  const [searchValue, setSearchValue] = useState("");

  return (
    <form
      className="SearchField"
      onSubmit={(e) => {
        e.preventDefault();
        onSearch();
      }}
    >
      <input
        className="SearchField__input"
        type="text"
        name="searchValue"
        value={searchValue}
        placeholder={placeholder}
        onChange={(e) => {
          setSearchValue(e.target.value);
        }}
      />
      <button type="submit" className="SearchField__button" onClick={onSearch}>
        <FaMagnifyingGlass className="SearchField__icon" />
      </button>
    </form>
  );
};

export default SearchField;
