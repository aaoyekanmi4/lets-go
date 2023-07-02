import React from "react";

const ErrorsContainer = ({ errorsArray }) => {
  if (!errorsArray.length) {
    return null;
  }

  const errorsList = errorsArray.map((error, index) => {
    return <p className="" key={index}>{`o ${error}`}</p>;
  });

  return <div className="Form__backend-errors">{errorsList}</div>;
};

export default ErrorsContainer;
