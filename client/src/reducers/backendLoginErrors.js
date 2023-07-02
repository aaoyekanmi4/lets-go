import types from "../actions/types.js";

const backendLoginErrors = (state = null, action) => {
  switch (action.type) {
    case types.SEND_BACKEND_LOGIN_ERRORS:
      return action.payload;
    case types.CLEAR_BACKEND_LOGIN_ERRORS:
      return null;
    default:
      return state;
  }
};

export default backendLoginErrors;
