import React from "react";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import GroupCard from "../../../components/GroupCard/GroupCard.js";

const Groups = () => {
  const groups = useSelector((state) => {
    return state.groups;
  });

  const renderedGroups = groups.map((group) => {
    return <GroupCard key={group.groupId} groupName={group.name} />;
  });

  return (
    <div className="Groups">
      <Header />
      <div className="Groups__container container">{renderedGroups}</div>
    </div>
  );
};

export default Groups;
