import axios from "axios";

import baseUrls from "../../baseUrls";
import getBackendErrorMessages from "../../getBackendErrorMessages";

const editEventPost = async (jwtToken, postData) => {
  try {
    await axios.put(
      `${baseUrls.database}/api/event-post/${postData.postId}`,
      postData,
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
      errorMessages: getBackendErrorMessages(e, "Unable to edit event post."),
    };
  }
};

const deleteEventPost = async (jwtToken, postId) => {
  try {
    await axios.delete(`${baseUrls.database}/api/event-post/${postId}`, {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    });

    return {
      status: 204,
    };
  } catch (e) {
    return {
      status: e.response.status,
      errorMessages: getBackendErrorMessages(e, "Could not find event post"),
    };
  }
};
export { editEventPost, deleteEventPost };
