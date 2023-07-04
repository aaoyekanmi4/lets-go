import { combineReducers } from "redux";

import user from "./user.js";
import backendRegisterErrors from "./backendRegisterErrors.js";
import backendLoginErrors from "./backendLoginErrors.js";
import contacts from "./contacts.js";
import contactsErrors from "./contactsErrors.js";
import groups from "./groups.js";
import groupsErrors from "./groupsErrors.js";
import isLoggingIn from "./isLoggingIn.js";
import isCreatingUser from "./isCreatingUser.js";

export default combineReducers({
  user,
  backendRegisterErrors,
  backendLoginErrors,
  contacts,
  contactsErrors,
  groups,
  groupsErrors,
  isLoggingIn,
  isCreatingUser,
});
