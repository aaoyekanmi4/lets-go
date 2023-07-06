const EVENT_API_URL = "http://localhost:8080/api/event";
const VENUE_API_URL = "http://localhost:8080/api/venue";
const GROUP_API_URL = "http://localhost:8080/api/group";
const SEND_SMS_API_URL = "http://localhost:8080/api/message/sendSMS";
const SEND_EMAIL_API_URL = "http://localhost:8080/api/message/sendEmail";

const Authorization =
  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsZXRzLWdvIiwic3ViIjoiYXJpdEBkZXYxMC5jb20iLCJhcHBfdXNlcl9pZCI6MywiYXV0aG9yaXRpZXMiOiJVU0VSIiwiZXhwIjoxNjg4NTg2MTIyfQ.z9WrdKmfYNuMwJ2xo_1ssyrbNlLOEg4BqJXJHYX6AYM";

const makeInit = (methodName, entity) => {
  const init = {
    method: methodName,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization,
    },

    body: JSON.stringify(entity),
  };
  return init;
};

const savedEvent = {
  savedEventId: 18,
  appUserId: 3,
  event: {
    eventId: 6,
    eventName: "Davido",
    category: null,
    imageUrl:
      "https://seatgeek.com/images/performers-landscape/davido-e9c361/250876/45976/huge.jpg",
    description: null,
    dateTime: "2023-07-07T19:00:00",
    source: "SeatGeek",
    sourceId: "6008856",
    eventLink:
      "https://seatgeek.com/davido-tickets/houston-texas-toyota-center-7-2023-07-07-7-pm/concert/6008856",
    venue: {
      venueId: 7,
      venueName: "Toyota Center",
      address: "1510 Polk Street",
      city: "Houston",
      state: "TX",
      country: null,
      zipCode: 77002,
      events: [],
    },
    eventPosts: [],
  },
  groups: [
    {
      groupId: 2,
      appUserId: 2,
      name: "Three Amigos",
      contacts: [],
    },
    {
      groupId: 3,
      appUserId: 3,
      name: "Yahoos",
      contacts: [],
    },
  ],
  contacts: [
    {
      contactId: 7,
      appUserId: 3,
      email: "bigbrother@yahoo.com",
      phone: "6782345559",
      firstName: "Big",
      lastName: "Brother",
      groups: [],
    },
  ],
};

//may want to have a state variable for this in the app
const getAllEventMembers = async (savedEvent) => {
  const { groups, contacts } = savedEvent;
  let allContacts = [...contacts];

  //Getting contacts from each group
  //Have to find groups by id first
  const groupIds = groups.map((group) => group.groupId);

  const groupUrlsForFindById = groupIds.map((groupId) => {
    return `${GROUP_API_URL}/${groupId}`;
  });

  const responses = await Promise.all(
    groupUrlsForFindById.map(async (url) => {
      const response = await fetch(url, {
        headers: {
          Authorization,
        },
      });
      if (!response.ok) console.log(response.status);
      return response.json();
    })
  );
  responses.map((group) => {
    allContacts = [...group.contacts, ...allContacts];
  });
  return allContacts;
};

// export const saveEventForUser = async (appEvent, userId) => {
//   const { venue } = appEvent;
//   const init = makeInit("POST", venue);
//   try {
//     const response = await fetch(VENUE_API_URL, init);
//     const newVenue = await response.json();
//     const eventWithVenue = { ...appEvent, venue: newVenue }

//     const init2 = makeInit("POST", eventWithVenue);
//     const eventResponse = await fetch(`${EVENT_API_URL}/user/3`, init2);

//     const newEvent = await eventResponse.json();
//   } catch (err) {
//     console.log(err);
//   }
// }

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

const sendSMSMessage = async (savedEvent) => {
  const eventMembers = await getAllEventMembers(savedEvent);

  const phoneNumbers = getPhoneNumbers(eventMembers);

  //replace with your phone number and twilio credentials to get response

  //It's 10 digits: 1 + area code + your number
  const smsMessages = [""].map((number) => {
    return {
      recipient: number,
      savedEvent,
      eventDetailUrl: "https://www.google.com",
    };
  });

  const responses = await Promise.all(
    smsMessages.map(async (message) => {
      const response = await fetch(SEND_SMS_API_URL, makeInit("POST", message));
      if (!response.ok) console.log(response.status);
      return response.json();
    })
  );

  console.log(responses);
};

const sendEmail = async (savedEvent) => {
  const eventMembers = await getAllEventMembers(savedEvent);

  const emailAddresses = getContactEmails(eventMembers);

  console.log(emailAddresses);

  //put your email addresses here and it should work, can't receive it in my dev 10 outlook account though
  const emailMessage = {
    recipients: ["aritoyekan@gmail.com", "aaoyekanmi4@gmail.com"],
    savedEvent,
    eventDetailUrl: "http://www.google.com",
  };

  const response = await fetch(
    SEND_EMAIL_API_URL,
    makeInit("POST", emailMessage)
  );
  if (!response.ok) console.log(response.status);
  console.log(response.json());
};

// sendSMSMessage(savedEvent);
sendEmail(savedEvent);
