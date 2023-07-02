import axios from "axios";
import jwtDecode from "jwt-decode";

import types from "./types.js";
import baseUrls from "../baseUrls";

const EXPIRATION_MINUTES = 14;
const EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

const createUser = (userData) => {
  return async (dispatch, getState) => {
    try {
      const response = await axios.post(
        `${baseUrls.database}/api/create_account`,
        userData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      console.log(response);
    } catch (e) {
      console.log(e);
    }
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

      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };
};

const loginUser = (loginData) => {
  return async (dispatch, getState) => {
    try {
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

      const {
        app_user_id: appUserId,
        authorities,
        exp: tokenExpiration,
        sub: username,
      } = jwtDecode(jwtToken);

      dispatch({
        type: types.LOGIN_USER,
        payload: {
          jwtToken,
          appUserId,
          authorities: authorities.split(","),
          tokenExpiration,
          username,
        },
      });

      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };
};

export { loginUser, createUser, refreshToken };
