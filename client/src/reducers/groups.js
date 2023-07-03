import types from "../actions/types.js";

const groups = (state = [], action) => {
  switch (action.type) {
    case types.GET_GROUPS:
      return action.payload;
    default:
      return state;
  }
};

export default groups;
