import React, { useState } from "react";
import { useSelector } from "react-redux";

import AddData from "../../../../components/AddData/AddData.js";
import AddGroupsToEventModal from "../../../../components/AddGroupsToEventModal/AddGroupsToEventModal.js";
import "./AttachedGroups.scss";

const AttachedGroups = ({ sourceId }) => {
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

  const renderedGroupsInEvent = groupsInEvent.map((group) => {
    return (
      <p key={group.groupId} className="">
        {group.name}
      </p>
    );
  });

  const renderContent = () => {
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

  const renderGroupNames = () => {
    return (
      <>
        <div className="AttachedGroups">
          <h2 className="AttachedGroups__header">
            Groups attached to this event
          </h2>
          <div className="AttachedGroups__groups">
            {renderedGroupsInEvent.length ? (
              renderedGroupsInEvent
            ) : (
              <p>No Groups</p>
            )}
          </div>
          <button
            className="AttachedGroups__button button-outline button-outline--primary"
            onClick={() => {
              setShowAddGroupsModal(true);
            }}
          >
            Add Group
          </button>
        </div>

        {showAddGroupsModal ? (
          <AddGroupsToEventModal
            sourceId={sourceId}
            closeModal={() => {
              setShowAddGroupsModal(false);
            }}
          />
        ) : null}
      </>
    );
  };

  return renderGroupNames();
};

export default AttachedGroups;
