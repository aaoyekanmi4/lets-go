import axios from "axios";

import baseUrls from "../baseUrls";
import types from "./types.js";

const getGroups = () => {
  return async (dispatch, getState) => {
    try {
      const appUserId = getState().user.appUserId;

      const jwtToken = getState().user.jwtToken;

      const response = await axios.get(
        `${baseUrls.database}/api/group/user/${appUserId}`,
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      dispatch({
        type: types.GET_GROUPS,
        payload: response.data,
      });

      dispatch(removeGetGroupsErrors());
    } catch (e) {
      dispatch(sendGetGroupsErrors());
    }
  };
};

const sendGetGroupsErrors = () => {
  return {
    type: types.SEND_GET_GROUPS_ERRORS,
    payload: ["We're having trouble finding your groups. Try again later"],
  };
};

const removeGetGroupsErrors = () => {
  return {
    type: types.REMOVE_GET_GROUPS_ERRORS,
  };
};

const clearGroups = () => {
  return {
    type: types.CLEAR_GROUPS,
  };
};
export { getGroups, clearGroups };
