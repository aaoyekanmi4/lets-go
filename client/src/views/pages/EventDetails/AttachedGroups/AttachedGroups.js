import React, { useState, useEffect } from "react";
import { sendEmail, sendSMSMessage } from "../../../../MessagingTextApi.js";
import { useSelector } from "react-redux";

import AddGroupsToEventModal from "../../../../components/AddGroupsToEventModal/AddGroupsToEventModal.js";
import { getSavedEvent } from "../AttachedGroups/helpers.js";
import useResultIndicator from "../../../../hooks/useResultIndicator.js";
import getResultIndicator from "../../../../getResultIndicator.js";
import "./AttachedGroups.scss";

let resultIndicatorId;

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

  const [savedEvent, setSavedEvent] = useState(null);

  const [resultIndicator, setResultIndicator] =
    useResultIndicator(resultIndicatorId);

  useEffect(() => {
    runGetSavedEvent();
  }, []);

  const runGetSavedEvent = async () => {
    const response = await getSavedEvent(savedEventId, user.jwtToken);

    if (!response.errorMessages) {
      setGroupsInEvent(response.data.groups);
      setSavedEvent(response.data);
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
          <button
            className="AttachedGroups__button button-outline button-outline--primary"
            onClick={async () => {
              const response = await sendEmail(savedEvent, user.jwtToken);

              if (response.status === 200) {
                setResultIndicator({
                  show: "true",
                  outcome: "success",
                  operation: "notify",
                  message: "Successfully emailed all groups",
                });
              } else {
                setResultIndicator({
                  show: "true",
                  outcome: "fail",
                  operation: "notify",
                  message: "Unable to email groups",
                });
              }
            }}
          >
            Notify by email
          </button>
          <button
            className="AttachedGroups__button button-outline button-outline--primary"
            onClick={async () => {
              const response = await sendSMSMessage(savedEvent, user.jwtToken);

              if (response.status === 200) {
                setResultIndicator({
                  show: "true",
                  outcome: "success",
                  operation: "notify",
                  message: "Successfully sent text to all groups",
                });
              } else {
                setResultIndicator({
                  show: "true",
                  outcome: "fail",
                  operation: "notify",
                  message: "Unable to send text to groups",
                });
              }
            }}
          >
            Notify by text
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

        {resultIndicator.show ? getResultIndicator(resultIndicator) : null}
      </>
    );
  };

  return renderContent();
};

export default AttachedGroups;
