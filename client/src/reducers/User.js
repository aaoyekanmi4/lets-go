import types from "../actions/types.js";

const defaultUser = null;

const user = (state = defaultUser, action) => {
  switch (action.type) {
    case types.LOGIN_USER:
    case types.CREATE_USER:
    case types.REFRESH_TOKEN:
      return action.payload;
    case types.LOGOUT_USER:
      return null;
    default:
      return state;
  }
};

export default user;
