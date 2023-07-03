import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { AiOutlinePlus } from "react-icons/ai";

import Header from "../../../components/Header/Header.js";
import GroupCard from "../../../components/GroupCard/GroupCard.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import "../../sharedStyles/contactsGroups.scss";
import "./Groups.scss";

const Groups = () => {
  const groups = useSelector((state) => {
    return state.groups;
  });

  const [searchValue, setSearchValue] = useState("");

  useEffect(() => {
    getFilteredGroups();
  }, [searchValue]);

  const getFilteredGroups = () => {
    return groups.filter((group) => {
      return group.name.toLowerCase().includes(searchValue.toLowerCase());
    });
  };

  const renderedGroups = getFilteredGroups().map((group) => {
    return <GroupCard key={group.groupId} groupName={group.name} />;
  });

  return (
    <div className="Groups">
      <Header />
      <main className="GeneralLayout__main">
        <div className="container">
          <div className="GeneralLayout__search-field-container">
            <SearchField
              placeholder="Search group..."
              value={searchValue}
              onChange={setSearchValue}
              onSearch={() => {}}
            />
          </div>
          <Link to="/groups/create">
            <AiOutlinePlus className="GeneralLayout__add-icon" />
          </Link>
          <div className="Groups__container">{renderedGroups}</div>
        </div>
      </main>
    </div>
  );
};

export default Groups;
