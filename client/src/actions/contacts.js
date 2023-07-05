import axios from "axios";

import baseUrls from "../baseUrls";
import types from "./types.js";

const getContacts = () => {
  return async (dispatch, getState) => {
    try {
      const appUserId = getState().user.appUserId;

      const jwtToken = getState().user.jwtToken;

      const response = await axios.get(
        `${baseUrls.database}/api/contact/user/${appUserId}`,
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      dispatch({
        type: types.GET_CONTACTS,
        payload: response.data,
      });

      dispatch(removeGetContactsErrors());
    } catch (e) {
      dispatch(sendGetContactsErrors());
    }
  };
};

const sendGetContactsErrors = () => {
  return {
    type: types.SEND_GET_CONTACTS_ERRORS,
    payload: ["We're having trouble finding your contacts. Try again later"],
  };
};

const removeGetContactsErrors = () => {
  return {
    type: types.REMOVE_GET_CONTACTS_ERRORS,
  };
};

const clearContacts = () => {
  return {
    type: types.CLEAR_CONTACTS,
  };
};

export { getContacts, clearContacts };
