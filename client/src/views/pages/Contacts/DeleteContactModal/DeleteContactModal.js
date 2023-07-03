import React from "react";
import ModalContainer from "../../../../components/ModalContainer/ModalContainer.js";

import "./DeleteContactModal.scss";

const DeleteContactModal = ({ closeModal, firstName, lastName, email }) => {
  return (
    <ModalContainer>
      <div className="DeleteCard">
        <p>
          Are you sure you want to delete agent{" "}
          <span className="fw-bold">{`"${firstName} ${lastName}"?`}</span>
        </p>
        <div className="">
          <button className="" onClick={closeModal}>
            Cancel
          </button>
          <button
            className="button button--primary "
            id="confirm-delete-agent-button"
            onClick={() => {}}
          >
            Delete
          </button>
        </div>
      </div>
    </ModalContainer>
  );
};

export default DeleteContactModal;
