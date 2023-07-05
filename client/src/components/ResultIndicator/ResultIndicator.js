import React from "react";
import ReactDOM from "react-dom";

import "./ResultIndicator.scss";

const ResultIndicator = ({ outcome, text }) => {
  return ReactDOM.createPortal(
    <div
      className={`ResultIndicator ${
        outcome === "success"
          ? "ResultIndicator--success"
          : "ResultIndicator--danger"
      }`}
    >
      <p className="mb-0">{text}</p>
    </div>,
    document.getElementById("modal")
  );
};

export default ResultIndicator;
