import React, { useEffect, useState } from "react";

function EventList() {
  const [ticketMasterEvents, setTicketMasterEvents] = useState([]);
  const [seatGeekEvents, setSeatGeekEvents] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/ticketmaster/events")
      .then((response) => response.json())
      .then((data) => {
        setTicketMasterEvents(data);
      });

    fetch("http://localhost:8080/seatgeek/events")
      .then((response) => response.json())
      .then((data) => setSeatGeekEvents(data));
  }, []);

  return (
    <div>
      <h1>TicketMaster Events</h1>
      {ticketMasterEvents.map((event) => (
        <div key={event.id}>
          <h2>{event.name}</h2>
          <img src={event.images[0].url} alt={event.name} />
          <p>
            <a href={event.url}>Event Link</a>
          </p>
          {event.dates.start && <p>Event Time: {event.dates.start.dateTime}</p>}
          {event._embedded.venues[0] && (
            <>
              <p>Venue: {event._embedded.venues[0].name}</p>
              {event._embedded.venues[0].city && (
                <p>City: {event._embedded.venues[0].city.name}</p>
              )}
              {event._embedded.venues[0].state && (
                <p>
                  State: {normalizeState(event._embedded.venues[0].state.name)}
                </p>
              )}
              <p>Postal Code: {event._embedded.venues[0].postalCode}</p>
            </>
          )}
        </div>
      ))}

      <h1>SeatGeek Events</h1>
      {seatGeekEvents.map((event) => (
        <div key={event.id}>
          <h2>{event.title}</h2>
          <img src={event.performers[0].images.huge} alt={event.title} />
          <p>
            <a href={event.url}>Event Link</a>
          </p>
          {event.datetime_local && <p>Event Time: {event.datetime_local}</p>}
          {event.venue && (
            <>
              <p>Venue: {event.venue.name}</p>
              <p>City: {event.venue.city}</p>
              <p>State: {event.venue.state}</p>
              <p>Postal Code: {event.venue.postal_code}</p>
            </>
          )}
        </div>
      ))}
    </div>
  );
}

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

export default EventList;
