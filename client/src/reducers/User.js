import types from "../actions/types.js";

let defaultUser = null;

defaultUser = localStorage.getItem("user");

if (defaultUser) {
  defaultUser = JSON.parse(defaultUser);
}

const user = (state = defaultUser, action) => {
  switch (action.type) {
    case types.LOGIN_USER:
    case types.CREATE_USER:
    case types.REFRESH_TOKEN: {
      const userString = JSON.stringify(action.payload);

      localStorage.setItem("user", userString);

      return action.payload;
    }
    case types.LOGOUT_USER:
      localStorage.removeItem("user");
      return null;
    default:
      return state;
  }
};

export default user;
