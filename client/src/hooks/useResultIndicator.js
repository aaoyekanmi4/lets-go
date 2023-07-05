import { useState, useEffect } from "react";

const defaultResultValues = {
  show: false,
  outcome: "",
  operation: "",
  message: "",
};
const useResultIndicator = (resultIndicatorTimerId) => {
  const [showResultIndicator, setShowResultIndicator] =
    useState(defaultResultValues);

  //remove the  result indicator after 2seconds
  useEffect(() => {
    if (showResultIndicator.show) {
      clearTimeout(resultIndicatorTimerId);

      resultIndicatorTimerId = setTimeout(() => {
        setShowResultIndicator(defaultResultValues);
      }, 2000);
    }
  }, [showResultIndicator.show]);

  return [showResultIndicator, setShowResultIndicator];
};

export default useResultIndicator;
