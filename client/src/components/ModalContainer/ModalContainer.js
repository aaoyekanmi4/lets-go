import React from "react";
import ReactDOM from "react-dom";

import "./ModalContainer.scss";

const ModalContainer = ({ closeModal, children }) => {
  return ReactDOM.createPortal(
    <div
      className="ModalContainer d-flex
       justify-content-center align-items-center"
    >
      <button
        className="ModalContainer__close-button anchor-button anchor-button--light"
        onClick={closeModal}
      >
        Close
      </button>
      <div className="ModalContainer__content">{children}</div>
    </div>,
    document.getElementById("modal")
  );
};

export default ModalContainer;
