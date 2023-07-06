import axios from "axios";

import baseUrls from "../../../../baseUrls";
import getBackendErrorMessages from "../../../../getBackendErrorMessages";

const getSavedEvent = async (savedEventId, jwtToken) => {
  try {
    const response = await axios.get(
      `${baseUrls.database}/api/event/saved/${savedEventId}`,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    if (!response.data) {
      return {
        errorMessages: ["Unable to fetch saved event details"],
      };
    }

    return {
      status: 200,
      data: response.data,
    };
  } catch (e) {
    console.log(e);
    return {
      status: e.response.status,
      backendErrorMessages: getBackendErrorMessages(
        e,
        "Unable to fetch saved event details"
      ),
    };
  }
};

export { getSavedEvent };
