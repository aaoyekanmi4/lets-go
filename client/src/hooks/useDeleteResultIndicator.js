import { useState, useEffect } from "react";

const useDeleteResultIndicator = (deleteIndicatorTimerId) => {
  const [showDeleteResultIndicator, setShowDeleteResultIndicator] = useState({
    show: false,
    type: "",
  });

  //remove the delete result indicator after 2seconds
  useEffect(() => {
    if (showDeleteResultIndicator.show) {
      clearTimeout(deleteIndicatorTimerId);

      deleteIndicatorTimerId = setTimeout(() => {
        setShowDeleteResultIndicator({ show: false, type: "" });
      }, 2000);
    }
  }, [showDeleteResultIndicator.show]);

  return [showDeleteResultIndicator, setShowDeleteResultIndicator];
};

export default useDeleteResultIndicator;
