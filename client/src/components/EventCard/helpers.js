import axios from "axios";

import baseUrls from "../../baseUrls";
import getBackendErrorMessages from "../../getBackendErrorMessages";

const saveEvent = async (appUserId, jwtToken, eventData) => {
  try {
    //add venue first since its used as a foreign key in event
    const venueResponse = await axios.post(
      `${baseUrls.database}/api/venue`,
      eventData.venue,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    const savedVenue = venueResponse.data;

    //update the venue in eventId to also include venueId since used as foreign key
    await axios.post(
      `${baseUrls.database}/api/event/user/${appUserId}`,
      { ...eventData, venue: savedVenue },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

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

const removeEvent = async (appUserId, jwtToken, eventId) => {
  try {
    //add venue first since its used as a foreign key in event
    await axios.delete(
      `${baseUrls.database}/api/event/user/${eventId}/${appUserId}`,
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
      errorMessages: getBackendErrorMessages(e),
    };
  }
};

export { saveEvent, removeEvent };
