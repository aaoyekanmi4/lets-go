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
    } catch (e) {
      console.log(e);
    }
  };
};

export { getContacts };
