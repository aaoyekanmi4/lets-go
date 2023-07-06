import axios from "axios";

import baseUrls from "../../baseUrls";
import getBackendErrorMessages from "../../getBackendErrorMessages";

const editEventPost = async (jwtToken, postData) => {
  try {
    const response = await axios.put(
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

export { editEventPost };
