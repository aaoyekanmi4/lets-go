import types from "../actions/types.js";

const contacts = (state = [], action) => {
  switch (action.type) {
    case types.GET_CONTACTS:
      return action.payload;
    default:
      return state;
  }
};

export default contacts;
