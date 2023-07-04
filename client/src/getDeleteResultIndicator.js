import ResultIndicator from "./components/ResultIndicator/ResultIndicator.js";

const getDeleteResultIndicator = (type) => {
  if (type === "success") {
    return <ResultIndicator type={type} message="Successfully deleted!" />;
  }

  return <ResultIndicator type={type} message="Unable to delete" />;
};

export default getDeleteResultIndicator;
