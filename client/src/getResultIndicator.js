import ResultIndicator from "./components/ResultIndicator/ResultIndicator.js";

//takes in message object with operation and data fields
const getResultIndicator = (type, message) => {
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

  let text = message.data;

  if (!text) {
    text = genericMessages[message.operation][type];
  }

  return <ResultIndicator type={type} message={text} />;
};

export default { getResultIndicator };
