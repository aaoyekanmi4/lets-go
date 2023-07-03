import React from "react";
import ModalContainer from "../../../../components/ModalContainer/ModalContainer.js";

import "./DeleteContactModal.scss";

const DeleteContactModal = ({ closeModal, firstName, lastName, email }) => {
  return (
    <ModalContainer closeModal={closeModal}>
      <div className="DeleteCard">
        <p className="DeleteCard__text">
          Are you sure you want to delete agent{" "}
          <span className="fw-bold">{`"${firstName} ${lastName}"?`}</span>
        </p>
        <div className="DeleteCard__actions">
          <button
            className="button-main button-main--danger"
            onClick={closeModal}
          >
            Cancel
          </button>
          <button
            className="button-main button-main--primary"
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
