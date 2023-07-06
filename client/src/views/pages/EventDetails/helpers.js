import axios from "axios";

import baseUrls from "../../../baseUrls";
import normalizeApiEvents from "../../../normalizeApiEvents.js";
import getBackendErrorMessages from "../../../getBackendErrorMessages";

const findApiEvent = async (sourceValue, sourceId) => {
  let response;

  try {
    if (sourceValue === "1") {
      response = await axios.get(
        `${baseUrls.database}/ticketmaster/events/id/${sourceId}`
      );
    } else if (sourceValue === "2") {
      response = await axios.get(
        `${baseUrls.database}/seatgeek/events/id/${sourceId}`
      );
    }

    return {
      status: 200,
      data: normalizeApiEvents([response.data]),
    };
  } catch (e) {
    return {
      status: 500,
      errorMessages: getBackendErrorMessages(
        e,
        "Unable to fetch event details"
      ),
    };
  }
};

export { findApiEvent };
