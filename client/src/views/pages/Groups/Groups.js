import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import GroupCard from "../../../components/GroupCard/GroupCard.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import useResultIndicator from "../../../hooks/useResultIndicator.js";
import getResultIndicator from "../../../getResultIndicator.js";
import "../../sharedStyles/contactsGroups.scss";
import "./Groups.scss";

let deleteIndicatorTimerId;

const Groups = () => {
  const groups = useSelector((state) => {
    return state.groups;
  });

  const [searchValue, setSearchValue] = useState("");

  const [showDeleteResultIndicator, setShowDeleteResultIndicator] =
    useResultIndicator(deleteIndicatorTimerId);

  useEffect(() => {
    getFilteredGroups();
  }, [searchValue]);

  const getFilteredGroups = () => {
    return groups.filter((group) => {
      return group.name.toLowerCase().includes(searchValue.toLowerCase());
    });
  };

  const sortGroups = (groups) => {
    groups.sort((a, b) => {
      if (a.name.toLowerCase() < b.name.toLowerCase()) {
        return -1;
      } else if (a.name.toLowerCase() > b.name.toLowerCase()) {
        return 1;
      } else {
        return 0;
      }
    });

    return groups;
  };

  const renderedGroups = sortGroups(getFilteredGroups()).map((group) => {
    return (
      <GroupCard
        key={group.groupId}
        groupId={group.groupId}
        groupName={group.name}
        setDeleteResultIndicator={setShowDeleteResultIndicator}
      />
    );
  });

  return (
    <>
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
            <Link
              to="/groups/create"
              className="button-text button-text--primary"
            >
              + Add Group
            </Link>
            <div className="Groups__container">{renderedGroups}</div>
          </div>
        </main>
      </div>

      {showDeleteResultIndicator.show
        ? getResultIndicator(showDeleteResultIndicator.type, {
            operation: "delete",
          })
        : null}
    </>
  );
};

export default Groups;
