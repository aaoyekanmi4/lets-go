import React, { useState } from "react";
import { useSelector } from "react-redux";
import AddData from "../../../../components/AddData/AddData.js";

const SendMessage = ({ sourceId }) => {
  const allGroups = useSelector((state) => {
    return state.groups;
  });

  const groupsInEvent = useSelector((state) => {
    return state.savedEvents[sourceId].groups;
  });

  const eventId = useSelector((state) => {
    return state.savedEvents[sourceId].event.eventId;
  });

  const [selectedGroups, setSelectedGroups] = useState(groupsInEvent);

  console.log(selectedGroups);
  const onSelectedChange = (selectedData) => {
    setSelectedGroups(selectedData);
  };

  //dataObj is a group object
  const getDataName = (dataObj) => {
    return `${dataObj.name}`;
  };

  const isSelected = (selectedDataObj, currentObjId) => {
    return selectedDataObj.groupId === currentObjId;
  };

  const getDataId = (dataObj) => {
    return dataObj.groupId;
  };

  return (
    <form className="SendMessage">
      <p>Select groups to send this event to</p>
      <AddData
        getDataName={getDataName}
        isSelected={isSelected}
        getDataId={getDataId}
        inputPlaceholder="Search group..."
        labelName="Select groups"
        data={allGroups}
        onSelectedChange={onSelectedChange}
        initialSelectedData={selectedGroups}
      />
    </form>
  );
};

export default SendMessage;
