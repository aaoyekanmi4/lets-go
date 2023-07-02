import { combineReducers } from "redux";

import user from "./user.js";
import backendRegisterErrors from "./backendRegisterErrors.js";
import backendLoginErrors from "./backendLoginErrors.js";

export default combineReducers({
  user,
  backendRegisterErrors,
  backendLoginErrors,
});