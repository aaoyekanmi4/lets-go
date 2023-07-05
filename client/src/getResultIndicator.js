import ResultIndicator from "./components/ResultIndicator/ResultIndicator.js";

//takes in a message object with operation and data fields
//takes in an object of outcome,operation,message
const getResultIndicator = (resultDetails) => {
  const { outcome, operation, message } = resultDetails;

  const genericMessages = {
    delete: {
      success: "Successfully deleted!",
      fail: "Unable to delete",
    },
    save: {
      success: "Successfully saved!",
      fail: "Unable to save",
    },
  };

  let text = message;

  if (!text) {
    text = genericMessages[operation][outcome];
  }

  return <ResultIndicator outcome={outcome} text={text} />;
};

export default getResultIndicator;
