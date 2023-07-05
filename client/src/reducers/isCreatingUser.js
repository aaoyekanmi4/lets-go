import types from "../actions/types.js";

const isCreatingUser = (state = false, action) => {
  switch (action.type) {
    case types.SET_IS_CREATING_USER:
      return action.payload;
    default:
      return state;
  }
};

export default isCreatingUser;
