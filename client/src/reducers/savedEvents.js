import types from "../actions/types.js";

const savedEvents = (state = [], action) => {
  switch (action.type) {
    case types.GET_SAVED_EVENTS:
      return action.payload;
    default:
      return state;
  }
};

export default savedEvents;
