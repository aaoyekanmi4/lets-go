import React from "react";
import ModalContainer from "../ModalContainer/ModalContainer.js";

import "./DeleteModal.scss";

const DeleteModal = ({ closeModal, onDelete, message }) => {
  return (
    <ModalContainer closeModal={closeModal}>
      <div className="DeleteCard">
        {message}
        <div className="DeleteCard__actions">
          <button
            className="button-main button-main--danger"
            onClick={closeModal}
          >
            Cancel
          </button>
          <button
            className="button-main button-main--primary"
            onClick={onDelete}
          >
            Delete
          </button>
        </div>
      </div>
    </ModalContainer>
  );
};

export default DeleteModal;
