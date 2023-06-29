import React, { useState, useEffect } from "react";

const useWindowSize = () => {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  useEffect(() => {
    const updateWindowWidth = (e) => {
      setWindowWidth(e.target.innerWidth);
    };

    window.addEventListener("resize", updateWindowWidth);
  }, []);

  return windowWidth;
};

export default useWindowSize;
