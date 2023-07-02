import types from "../actions/types.js";

const defaultUser = null;

const User = (state = defaultUser, action) => {
  switch (action.type) {
    case types.LOGIN_USER:
      return action.payload;
    default:
      return state;
  }
};

export default User;
