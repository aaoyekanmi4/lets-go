import React from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";

import "./SearchField.scss";

const SearchField = ({ placeholder, value, onChange, onSearch }) => {
  // const [searchValue, setSearchValue] = useState("");

  return (
    <div className="SearchField">
      <input
        className="SearchField__input"
        type="text"
        name="searchValue"
        value={value}
        placeholder={placeholder}
        onChange={(e) => {
          onChange(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.code === "Enter") {
            onSearch();
          }
        }}
      />
      <button type="button" className="SearchField__button" onClick={onSearch}>
        <FaMagnifyingGlass className="SearchField__icon" />
      </button>
    </div>
  );
};

export default SearchField;
