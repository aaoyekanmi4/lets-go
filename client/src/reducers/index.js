import { combineReducers } from "redux";

import user from "./user.js";
import backendRegisterErrors from "./backendRegisterErrors.js";

export default combineReducers({
  user,
  backendRegisterErrors,
});
