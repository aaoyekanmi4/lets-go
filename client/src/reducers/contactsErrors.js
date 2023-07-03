import types from "../actions/types.js";

const contactsErrors = (state = null, action) => {
  switch (action.type) {
    case types.SEND_GET_CONTACTS_ERRORS:
      return action.payload;
    case types.REMOVE_GET_CONTACTS_ERRORS:
      return null;
    default:
      return state;
  }
};

export default contactsErrors;
