import React, { useState } from "react";
import { useSelector } from "react-redux";

import ModalContainer from "../ModalContainer/ModalContainer.js";
import AddData from "../AddData/AddData.js";
import "./AddGroupsToEventModal.scss";

const AddGroupsToEventModal = ({ closeModal, sourceId }) => {
  const allGroups = useSelector((state) => {
    return state.groups;
  });

  const groupsInEvent = useSelector((state) => {
    if (state.savedEvents[sourceId]) {
      return state.savedEvents[sourceId].groups;
    }
    return [];
  });

  // const eventId = useSelector((state) => {
  //   return state.savedEvents[sourceId].event.eventId;
  // });

  const [selectedGroups, setSelectedGroups] = useState(groupsInEvent);

  const [showAddGroupsModal, setShowAddGroupsModal] = useState(false);

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
    <ModalContainer closeModal={closeModal}>
      <form className="AddGroupsToEvent">
        <h2 className="AddGroupsToEvent__header">
          Select groups to send this event to
        </h2>
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
        <button
          type="submit"
          className="Form__submit-button button-main button-main--primary"
        >
          Update
        </button>
      </form>
    </ModalContainer>
  );
};

export default AddGroupsToEventModal;
