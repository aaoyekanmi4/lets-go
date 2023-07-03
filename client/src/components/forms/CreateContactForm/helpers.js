import axios from "axios";

import baseUrls from "../../../baseUrls.js";
import getBackendErrorMessages from "../../../getBackendErrorMessages.js";

const createContact = async (contactData, jwtToken) => {
  try {
    await axios.post(`${baseUrls.database}/api/contact`, contactData, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
    });

    return {
      status: 201,
    };
  } catch (e) {
    return {
      status: e.response.status,
      errorMessages: getBackendErrorMessages(e),
    };
  }
};

export { createContact };
