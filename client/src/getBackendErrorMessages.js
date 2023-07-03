const getBackendErrorMessages = (
  e,
  customMessage = "Something went wrong. Try again later"
) => {
  let errorMessages;

  if (e.response.status === 403) {
    errorMessages = ["You don't have access to this resource"];
    return errorMessages;
  }

  if (!e.response.data) {
    errorMessages = [customMessage];
  } else if (e.response.data.message) {
    errorMessages = [e.response.data.message];
  } else {
    errorMessages = e.response.data;
  }

  return errorMessages;
};

export default getBackendErrorMessages;
