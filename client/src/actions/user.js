import axios from "axios";
import jwtDecode from "jwt-decode";

import types from "./types.js";
import baseUrls from "../baseUrls";
import getBackendErrorMessages from "../getBackendErrorMessages.js";

const EXPIRATION_MINUTES = 13;

const EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

const createUser = (userData) => {
  return async (dispatch) => {
    try {
      dispatch(setIsCreatingUser(true));

      const response = await axios.post(
        `${baseUrls.database}/api/create_account`,
        userData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const jwtToken = response.data.jwt_token;

      dispatch({
        type: types.CREATE_USER,
        payload: makeUserFromJwt(jwtToken),
      });

      dispatch(clearBackendRegisterErrors());

      dispatch(clearBackendLoginErrors());

      dispatch(setRefreshTokenTimer());
    } catch (e) {
      dispatch(sendBackendRegisterErrors(getBackendErrorMessages(e)));
    } finally {
      dispatch(setIsCreatingUser(false));
    }
  };
};

const sendBackendRegisterErrors = (errorsArray) => {
  return {
    type: types.SEND_BACKEND_REGISTER_ERRORS,
    payload: errorsArray,
  };
};

const clearBackendRegisterErrors = () => {
  return {
    type: types.CLEAR_BACKEND_REGISTER_ERRORS,
  };
};

const loginUser = (loginData) => {
  return async (dispatch) => {
    try {
      dispatch(setIsLoggingIn(true));

      const response = await axios.post(
        "http://localhost:8080/api/authenticate",
        loginData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const jwtToken = response.data.jwt_token;

      dispatch({
        type: types.LOGIN_USER,
        payload: makeUserFromJwt(jwtToken),
      });

      dispatch(clearBackendLoginErrors());

      dispatch(clearBackendRegisterErrors());

      dispatch(setRefreshTokenTimer());
    } catch (e) {
      if (e.response.status === 403) {
        dispatch(sendBackendLoginErrors(["Incorrect username or password"]));
      } else dispatch(sendBackendLoginErrors(getBackendErrorMessages(e)));
    } finally {
      dispatch(setIsLoggingIn(false));
    }
  };
};

const sendBackendLoginErrors = (errorsArray) => {
  return {
    type: types.SEND_BACKEND_LOGIN_ERRORS,
    payload: errorsArray,
  };
};

const clearBackendLoginErrors = () => {
  return {
    type: types.CLEAR_BACKEND_LOGIN_ERRORS,
  };
};

const logoutUser = () => {
  return {
    type: types.LOGOUT_USER,
  };
};

const refreshToken = () => {
  return async (dispatch, getState) => {
    try {
      const jwtToken = getState().user.jwtToken;

      const user = getState().user;

      const response = await axios.post(
        `${baseUrls.database}/api/refresh_token`,
        user,
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      const updatedJwtToken = response.data.jwt_token;

      dispatch({
        type: types.REFRESH_TOKEN,
        payload: makeUserFromJwt(updatedJwtToken),
      });
    } catch (e) {
      dispatch({
        type: types.LOGOUT_USER,
      });
    }
  };
};

const setRefreshTokenTimer = () => {
  return async (dispatch, getState) => {
    let intervalId;

    setInterval(async () => {
      const user = getState().user;

      if (user) {
        await dispatch(refreshToken());
      } else {
        clearInterval(intervalId);
      }
    }, EXPIRATION_MILLIS);
  };
  // if (user) {
  //   await dispatch(refreshToken());

  //   setTimeout(() => {
  //     dispatch(setRefreshTokenTimer());
  //   }, 5000);
  // } else {
  //   return;
  // }
};

const setIsLoggingIn = (boolean) => {
  return {
    type: types.SET_IS_LOGGING_IN,
    payload: boolean,
  };
};

const setIsCreatingUser = (boolean) => {
  return {
    type: types.SET_IS_CREATING_USER,
    payload: boolean,
  };
};

const makeUserFromJwt = (jwtToken) => {
  const {
    app_user_id: appUserId,
    authorities,
    exp: tokenExpiration,
    sub: username,
  } = jwtDecode(jwtToken);

  return {
    jwtToken,
    appUserId,
    authorities: authorities.split(","),
    username,
    tokenExpiration,
  };
};

export {
  loginUser,
  createUser,
  logoutUser,
  clearBackendRegisterErrors,
  clearBackendLoginErrors,
};
