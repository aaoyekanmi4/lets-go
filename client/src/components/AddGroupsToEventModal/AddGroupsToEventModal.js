import React, { useState } from "react";
import { useSelector } from "react-redux";

import ModalContainer from "../ModalContainer/ModalContainer.js";
import ErrorsContainer from "../../components/forms/ErrorsContainer/ErrorsContainer.js";
import AddData from "../AddData/AddData.js";
import { updateGroupsInEvent } from "./helpers.js";
import "./AddGroupsToEventModal.scss";

const AddGroupsToEventModal = ({
  closeModal,
  sourceId,
  initialSavedGroups,
  getSavedEvent,
}) => {
  const allGroups = useSelector((state) => {
    return state.groups;
  });
  const jwtToken = useSelector((state) => {
    return state.user.jwtToken;
  });

  const savedEventId = useSelector((state) => {
    if (state.savedEvents[sourceId]) {
      return state.savedEvents[sourceId].savedEventId;
    }

    return null;
  });

  const [selectedGroups, setSelectedGroups] = useState(initialSavedGroups);

  const [updateErrors, setUpdateErrors] = useState([]);

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

  const onUpdate = async (e) => {
    e.preventDefault();

    const response = await updateGroupsInEvent(
      jwtToken,
      savedEventId,
      selectedGroups.map((group) => group.groupId)
    );

    if (response.status === 204) {
      await getSavedEvent();

      closeModal();
    } else {
      setUpdateErrors(response.errorMessages);
    }
  };

  return (
    <ModalContainer closeModal={closeModal}>
      <form className="AddGroupsToEvent" onSubmit={onUpdate}>
        <h2 className="AddGroupsToEvent__header">
          Select groups to send this event to
        </h2>
        <ErrorsContainer errorsArray={updateErrors} />
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
