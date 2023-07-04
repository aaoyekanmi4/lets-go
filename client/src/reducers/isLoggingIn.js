import types from "../actions/types.js";

const isLoggingIn = (state = false, action) => {
  switch (action.type) {
    case types.SET_IS_LOGGING_IN:
      return action.payload;
    default:
      return state;
  }
};

export default isLoggingIn;
