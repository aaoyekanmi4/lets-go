import axios from "axios";
import baseUrls from "../../../baseUrls.js";

const createContact = async (contactData, jwtToken) => {
  try {
    const response = await axios.post(
      `${baseUrls.database}/api/contact`,
      contactData,
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
    let errorMessages = e.response.data;

    if (!errorMessages) {
      errorMessages = ["Something went wrong. Try again later"];
    }
    return {
      status: e.response.status,
      errorMessages: errorMessages,
    };
  }
};

export { createContact };
