import types from "../actions/types.js";

const defaultUser = null;

const User = (state = defaultUser, action) => {
  switch (action.type) {
    case (types.LOGIN_USER, types.CREATE_USER, types.REFRESH_TOKEN):
      return action.payload;
    case types.LOGOUT_USER:
      return null;

    default:
      return state;
  }
};

export default User;
