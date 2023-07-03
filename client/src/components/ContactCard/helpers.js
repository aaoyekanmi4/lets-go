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
    return {
      status: e.response.status,
      errorMessages: getBackendErrorMessages(e, "Contact not found. Try again"),
    };
  }
};

export { deleteContact };
