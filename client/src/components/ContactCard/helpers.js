import axios from "axios";

import baseUrls from "../../baseUrls.js";
import getBackendErrorMessages from "../../getBackendErrorMessages.js";

const deleteContact = async (contactId, jwtToken) => {
  try {
    await axios.delete(
      `${baseUrls.database}/api/contact/
      ${contactId}`,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    return {
      status: 204,
    };
  } catch (e) {
    const status = e.response.status;

    let errorMessages = [];

    if (status === 404) {
      errorMessages = ["Contact not found"];
    } else {
      errorMessages = getBackendErrorMessages(e);
    }

    return {
      status,
      errorMessages,
    };
  }
};

export { deleteContact };
