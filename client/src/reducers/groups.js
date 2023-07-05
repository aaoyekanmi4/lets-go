import types from "../actions/types.js";

const groups = (state = [], action) => {
  switch (action.type) {
    case types.GET_GROUPS:
      return action.payload;
    case types.CLEAR_GROUPS:
      return [];
    default:
      return state;
  }
};

export default groups;
