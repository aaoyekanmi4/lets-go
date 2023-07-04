import React from "react";
import ReactDOM from "react-dom";

import "./ResultIndicator.scss";

const ResultIndicator = ({ type, message }) => {
  return ReactDOM.createPortal(
    <div
      className={`ResultIndicator ${
        type === "success"
          ? "ResultIndicator--success"
          : "ResultIndicator--danger"
      }`}
    >
      <p className="mb-0">{message}</p>
    </div>,
    document.getElementById("modal")
  );
};

export default ResultIndicator;
