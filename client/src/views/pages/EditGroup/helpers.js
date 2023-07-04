import axios from "axios";

import baseUrls from "../../../baseUrls";
import getBackendErrorMessages from "../../../getBackendErrorMessages";

const findGroup = async (jwtToken, groupId, setFormValues, setErrors) => {
  try {
    const response = await axios.get(
      `${baseUrls.database}/api/group/${groupId}`,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    if (!response.data) {
      setErrors(["Unable to find group"]);
      return;
    }

    const { name, contacts } = response.data;

    setFormValues({ name, contacts });
  } catch (e) {
    setErrors(getBackendErrorMessages(e));
  }
};

export { findGroup };
