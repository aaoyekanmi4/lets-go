import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";

import AddGroupsToEventModal from "../../../../components/AddGroupsToEventModal/AddGroupsToEventModal.js";
import { getSavedEvent } from "../AttachedGroups/helpers.js";
import "./AttachedGroups.scss";

const AttachedGroups = ({ sourceId }) => {
  const user = useSelector((state) => {
    return state.user;
  });

  const savedEventId = useSelector((state) => {
    if (state.savedEvents[sourceId]) {
      return state.savedEvents[sourceId].savedEventId;
    }

    return null;
  });

  const [groupsInEvent, setGroupsInEvent] = useState([]);

  const [savedEventErrors, setSavedEventErrors] = useState([]);

  const [showAddGroupsModal, setShowAddGroupsModal] = useState(false);

  useEffect(() => {
    runGetSavedEvent();
  }, []);

  const runGetSavedEvent = async () => {
    const response = await getSavedEvent(savedEventId, user.jwtToken);

    if (!response.errorMessages) {
      setGroupsInEvent(response.data.groups);
    } else {
      setSavedEventErrors(response.errorMessages);
    }
  };

  const renderedGroupsInEvent = groupsInEvent.map((group) => {
    return (
      <p key={group.groupId} className="AttachedGroups__group">
        {group.name}
      </p>
    );
  });

  const renderContent = () => {
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
              <p>No groups to show</p>
            )}
          </div>
          <button
            className="AttachedGroups__button button-outline button-outline--primary"
            onClick={() => {
              setShowAddGroupsModal(true);
            }}
          >
            Update Groups
          </button>
        </div>

        {showAddGroupsModal ? (
          <AddGroupsToEventModal
            sourceId={sourceId}
            closeModal={() => {
              setShowAddGroupsModal(false);
            }}
            getSavedEvent={runGetSavedEvent}
            initialSavedGroups={groupsInEvent}
          />
        ) : null}
      </>
    );
  };

  return renderContent();
};

export default AttachedGroups;
