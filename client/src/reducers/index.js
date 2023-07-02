import { combineReducers } from "redux";

import user from "./user.js";
import backendRegisterErrors from "./backendRegisterErrors.js";
import backendLoginErrors from "./backendLoginErrors.js";
import contacts from "./contacts.js";
import contactsErrors from "./contactsErrors.js";

export default combineReducers({
  user,
  backendRegisterErrors,
  backendLoginErrors,
  contacts,
  contactsErrors,
});
