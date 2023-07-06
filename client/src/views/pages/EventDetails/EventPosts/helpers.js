import axios from "axios";

import baseUrls from "../../../../baseUrls";
import getBackendErrorMessages from "../../../../getBackendErrorMessages";

const getEventPosts = async (eventId, setPosts, setErrors) => {
  try {
    const response = await axios.get(
      `${baseUrls.database}/api/event-post/${eventId}`
    );

    setPosts(response.data);

    setErrors([]);
  } catch (e) {
    setErrors(getBackendErrorMessages(e, "Unable to fetch event posts."));
  }
};

const createEventPost = async (postData, jwtToken) => {
  try {
    const response = await axios.post(
      `${baseUrls.database}/api/event-post`,
      postData,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    return {
      status: 201,
      data: response.data,
    };
  } catch (e) {
    console.log(e);
    return {
      status: e.response.status,
      errorMessages: getBackendErrorMessages(e),
    };
  }
};

export { getEventPosts, createEventPost };
