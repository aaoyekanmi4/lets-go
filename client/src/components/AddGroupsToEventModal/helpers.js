import axios from "axios";

import baseUrls from "../../baseUrls.js";
import getBackendErrorMessages from "../../getBackendErrorMessages";

const updateGroupsInEvent = async (jwtToken, savedEventId, groupsArray) => {
  try {
    await axios.put(
      `${baseUrls.database}/api/event/batch/groups/${savedEventId}`,
      groupsArray,
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
      status: 500,
      errorMessages: getBackendErrorMessages(e, "Unable to update your groups"),
    };
  }
};

export { updateGroupsInEvent };
