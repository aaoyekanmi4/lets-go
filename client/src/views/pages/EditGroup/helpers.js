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

    console.log(response);

    const { name, contacts } = response.data;

    //setFormValues({ firstName, lastName, email, phone });
  } catch (e) {
    console.log(e);
  }
};

export { findGroup };
