import types from "../actions/types.js";

const backendRegisterErrors = (state = null, action) => {
  switch (action.type) {
    case types.SEND_BACKEND_REGISTER_ERRORS:
      return action.payload;
    case types.CLEAR_BACKEND_REGISTER_ERRORS:
      return null;
    default:
      return state;
  }
};
export default backendRegisterErrors;
