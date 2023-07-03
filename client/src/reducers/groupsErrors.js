import types from "../actions/types.js";

const groupsErrors = (state = null, action) => {
  switch (action.type) {
    case types.SEND_GET_GROUPS_ERRORS:
      return action.payload;
    case types.REMOVE_GET_GROUPS_ERRORS:
      return null;
    default:
      return state;
  }
};

export default groupsErrors;
