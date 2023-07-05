import axios from "axios";

import baseUrls from "../../../baseUrls";
import getBackendErrorMessages from "../../../getBackendErrorMessages";

const findContact = async (jwtToken, contactId, setFormValues, setErrors) => {
  try {
    const response = await axios.get(
      `${baseUrls.database}/api/contact/${contactId}`,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    if (!response.data) {
      setErrors(["Unable to find contact"]);
      return;
    }

    const { firstName, lastName, email, phone } = response.data;

    setFormValues({ firstName, lastName, email, phone });
  } catch (e) {
    setErrors(getBackendErrorMessages(e));
  }
};

const getEditContactFunction = (contactId) => {
  return async (contactData, jwtToken) => {
    try {
      await axios.put(
        `${baseUrls.database}/api/contact/${contactId}`,
        { ...contactData, contactId },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      return {
        status: 204,
      };
    } catch (e) {
      return {
        status: e.response.status,
        errorMessages: getBackendErrorMessages(e),
      };
    }
  };
};

export { findContact, getEditContactFunction };
