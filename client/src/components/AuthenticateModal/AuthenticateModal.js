import React from "react";
import { Link } from "react-router-dom";

import ModalContainer from "../ModalContainer/ModalContainer.js";
import "./AuthenticateModal.scss";

const AuthenticateModal = ({ closeModal }) => {
  return (
    <ModalContainer closeModal={closeModal}>
      <div className="AuthenticateModal">
        <p className="AuthenticateModal__text text-center">
          Must be logged in to save this event
        </p>
        <p className="AuthenticateModal__link text-center">
          <Link to="/login" className="button-text button-text--primary">
            Click to login
          </Link>
        </p>
      </div>
    </ModalContainer>
  );
};

export default AuthenticateModal;
