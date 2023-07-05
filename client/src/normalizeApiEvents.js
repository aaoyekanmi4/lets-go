const stateLookup = {
  Alabama: "AL",
  Alaska: "AK",
  Arizona: "AZ",
  Arkansas: "AR",
  California: "CA",
  Colorado: "CO",
  Connecticut: "CT",
  Delaware: "DE",
  Florida: "FL",
  Georgia: "GA",
  Hawaii: "HI",
  Idaho: "ID",
  Illinois: "IL",
  Indiana: "IN",
  Iowa: "IA",
  Kansas: "KS",
  Kentucky: "KY",
  Louisiana: "LA",
  Maine: "ME",
  Maryland: "MD",
  Massachusetts: "MA",
  Michigan: "MI",
  Minnesota: "MN",
  Mississippi: "MS",
  Missouri: "MO",
  Montana: "MT",
  Nebraska: "NE",
  Nevada: "NV",
  "New Hampshire": "NH",
  "New Jersey": "NJ",
  "New Mexico": "NM",
  "New York": "NY",
  "North Carolina": "NC",
  "North Dakota": "ND",
  Ohio: "OH",
  Oklahoma: "OK",
  Oregon: "OR",
  Pennsylvania: "PA",
  "Rhode Island": "RI",
  "South Carolina": "SC",
  "South Dakota": "SD",
  Tennessee: "TN",
  Texas: "TX",
  Utah: "UT",
  Vermont: "VT",
  Virginia: "VA",
  Washington: "WA",
  "West Virginia": "WV",
  Wisconsin: "WI",
  Wyoming: "WY",
};

function normalizeState(stateName) {
  if (!stateName) {
    return "";
  }

  if (stateName.length === 2) {
    return stateName.toUpperCase();
  }

  const abbreviation = stateLookup[stateName.trim()];
  return abbreviation || "";
}

const normalizeSeatGeekEvent = (seatGeekEvent) => {
  const imageUrl = seatGeekEvent.performers
    ? seatGeekEvent.performers[0].images.huge
    : "";

  const {
    title,
    datetime_local,
    source,
    id,
    url,
    venue: { name, city, state, postal_code, address },
  } = seatGeekEvent;

  return {
    eventName: title,
    imageUrl,
    dateTime: datetime_local,
    source,
    sourceId: id,
    eventLink: url,
    venue: {
      venueName: name,
      city,
      state: normalizeState(state),
      zipCode: postal_code,
      address: address,
    },
  };
};

const normalizeTicketMasterEvent = (ticketMasterEvent) => {
  const imageUrl = ticketMasterEvent.images
    ? ticketMasterEvent.images[0].url
    : "";

  const venue =
    ticketMasterEvent._embedded && ticketMasterEvent._embedded.venues
      ? ticketMasterEvent._embedded.venues[0]
      : "";

  const dateTime =
    ticketMasterEvent.dates && ticketMasterEvent.dates.start
      ? ticketMasterEvent.dates.start.dateTime
      : "";

  const venueName = venue.name;
  const city = venue.city ? venue.city.name : "";
  const state = venue.state ? venue.state.name : "";
  const zipCode = venue.postalCode;
  const address = venue.address ? venue.address.line1 : "";

  return {
    eventName: ticketMasterEvent.name,
    imageUrl,
    dateTime,
    source: ticketMasterEvent.source,
    sourceId: ticketMasterEvent.id,
    eventLink: ticketMasterEvent.url,
    venue: {
      venueName,
      city,
      state: normalizeState(state),
      zipCode,
      address,
    },
  };
};

const normalizeAllApiEvents = (allApiEvents) => {
  return allApiEvents.map((apiEvent) => {
    if (apiEvent.source === "TicketMaster") {
      return normalizeTicketMasterEvent(apiEvent);
    } else {
      return normalizeSeatGeekEvent(apiEvent);
    }
  });
};

export default normalizeAllApiEvents;
