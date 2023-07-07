import axios from "axios";

const GROUP_API_URL = "http://localhost:8080/api/group";
const SEND_SMS_API_URL = "http://localhost:8080/api/message/sendSMS";
const SEND_EMAIL_API_URL = "http://localhost:8080/api/message/sendEmail";

const getAuthorization = (token) => {
  return `Bearer ${token}`;
};

const getAllEventMembers = async (savedEvent, token) => {
  const Authorization = getAuthorization(token);

  const { groups, contacts } = savedEvent;
  let allContacts = [...contacts];

  const groupIds = groups.map((group) => group.groupId);

  const groupUrlsForFindById = groupIds.map((groupId) => {
    return `${GROUP_API_URL}/${groupId}`;
  });

  const responses = await axios.all(
    groupUrlsForFindById.map((url) =>
      axios.get(url, {
        headers: {
          Accept: "application/json",
          Authorization,
        },
      })
    )
  );

  responses.map((response) => {
    allContacts = [...response.data.contacts, ...allContacts];
  });

  return allContacts;
};

const getPhoneNumbers = (eventMembers) => {
  const phoneNumberSet = new Set();
  eventMembers.forEach((member) => {
    phoneNumberSet.add(member.phone);
  });
  const phoneNumbers = Array.from(phoneNumberSet);
  return phoneNumbers;
};

const getContactEmails = (eventMembers) => {
  const emailSet = new Set();
  eventMembers.forEach((member) => {
    emailSet.add(member.email);
  });
  const emailAddresses = Array.from(emailSet);
  return emailAddresses;
};

const getEventDetailLink = (savedEvent) => {
  const sourceType = savedEvent.event.source === "TicketMaster" ? 1 : 2;
  const sourceId = savedEvent.event.sourceId;
  return `http://localhost:3000/events/${sourceType}/${sourceId}`;
};

const sendSMSMessage = async (savedEvent, token) => {
  try {
    const Authorization = getAuthorization(token);
    const eventMembers = await getAllEventMembers(savedEvent, token);

    const phoneNumbers = getPhoneNumbers(eventMembers);

    const smsMessages = phoneNumbers.map((number) => {
      return {
        recipient: number,
        savedEvent,
        eventDetailUrl: getEventDetailLink(savedEvent),
      };
    });

    await axios.all(
      smsMessages.map((message) =>
        axios.post(SEND_SMS_API_URL, message, {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            Authorization,
          },
        })
      )
    );

    return {
      status: 200,
    };
  } catch (e) {
    return {
      status: 500,
    };
  }
};

const sendEmail = async (savedEvent, token) => {
  try {
    const Authorization = getAuthorization(token);
    const eventMembers = await getAllEventMembers(savedEvent, token);

    const emailAddresses = getContactEmails(eventMembers);

    const emailMessage = {
      recipients: emailAddresses,
      savedEvent,
      eventDetailUrl: getEventDetailLink(savedEvent),
    };

    const response = await axios.post(SEND_EMAIL_API_URL, emailMessage, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        Authorization,
      },
    });

    return {
      status: response.status,
    };
  } catch (e) {
    return {
      status: e.response.status,
    };
  }
};

export { sendEmail, sendSMSMessage };
