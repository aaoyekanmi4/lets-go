import axios from "axios";

import types from "./types.js";
import baseUrls from "../baseUrls";
import getBackendErrorMessages from "../getBackendErrorMessages.js";

const getSavedEvents = () => {
  return async (dispatch, getState) => {
    try {
      const appUserId = getState().user.appUserId;

      const jwtToken = getState().user.jwtToken;

      const response = await axios.get(
        `${baseUrls.database}/api/event/user/${appUserId}`,
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      dispatch({
        type: types.GET_SAVED_EVENTS,
        payload: response.data,
      });

      dispatch(removeGetSavedEventsErrors);
    } catch (e) {
      dispatch(
        sendGetSavedEventsErrors(
          getBackendErrorMessages(
            e,
            "We're having trouble finding your saved events. Try again later"
          )
        )
      );
    }
  };
};

const sendGetSavedEventsErrors = (errorsArray) => {
  return {
    type: types.SEND_GET_SAVED_EVENTS_ERRORS,
    payload: errorsArray,
  };
};

const removeGetSavedEventsErrors = () => {
  return {
    type: types.REMOVE_GET_SAVED_EVENTS_ERRORS,
  };
};

export { getSavedEvents };
