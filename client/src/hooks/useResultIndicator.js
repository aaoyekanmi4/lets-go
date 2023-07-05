import { useState, useEffect } from "react";

const useResultIndicator = (resultIndicatorTimerId) => {
  const [showResultIndicator, setShowResultIndicator] = useState({
    show: false,
    type: "",
  });

  //remove the  result indicator after 2seconds
  useEffect(() => {
    if (showResultIndicator.show) {
      clearTimeout(resultIndicatorTimerId);

      resultIndicatorTimerId = setTimeout(() => {
        setShowResultIndicator({ show: false, type: "" });
      }, 2000);
    }
  }, [showResultIndicator.show]);

  return [showResultIndicator, setShowResultIndicator];
};

export default useResultIndicator;
