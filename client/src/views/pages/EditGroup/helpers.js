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

const getEditGroupFunction = (groupId) => {
  return async (groupData, contacts, jwtToken) => {
    try {
      await axios.put(
        `${baseUrls.database}/api/group/${groupId}`,
        { ...groupData, groupId },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );

      //updates list of contacts to created group
      await axios.put(
        `${baseUrls.database}/api/group/batch/${groupId}`,
        contacts,
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
        errorMessages: getBackendErrorMessages(e),
      };
    }
  };
};

export { findGroup, getEditGroupFunction };
