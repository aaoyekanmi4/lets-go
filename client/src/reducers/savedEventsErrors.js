import types from "../actions/types.js";

const savedEventsErrors = (state = null, action) => {
  switch (action.type) {
    case types.SEND_GET_SAVED_EVENTS_ERRORS:
      return action.payload;
    case types.REMOVE_GET_SAVED_EVENTS_ERRORS:
      return null;
    default:
      return state;
  }
};

export default savedEventsErrors;
