import React, { useState } from "react";
import { useSelector } from "react-redux";
import moment from "moment";
import Header from "../../../components/Header/Header.js";
import EventCard from "../../../components/EventCard/EventCard.js";
import SearchField from "../../../components/SearchField/SearchField.js";
import "./SavedEvents.scss";

const SavedEvents = () => {
  const savedEvents = useSelector((state) => {
    return state.savedEvents;
  });

  const [eventsearchValue, setEventsSearchValue] = useState("");

  const [venueSearchValue, setVenueSearchValue] = useState("");

  const [sort, setSortBy] = useState("eventName");

  const sortEvents = (events) => {
    if (sort === "eventName") {
      events.sort((a, b) => {
        if (a.event.eventName.toLowerCase() < b.event.eventName.toLowerCase()) {
          return -1;
        } else if (
          a.event.eventName.toLowerCase() > b.event.eventName.toLowerCase()
        ) {
          return 1;
        } else {
          return 0;
        }
      });
    }

    if (sort === "date") {
      events.sort((a, b) => {
        if (moment(a.event.dateTime).utc() < moment(b.event.dateTime).utc()) {
          return -1;
        } else if (
          moment(a.event.dateTime).utc() > moment(b.event.dateTime).utc()
        ) {
          return 1;
        } else {
          return 0;
        }
      });
    }

    return events;
  };

  const renderedSavedEvents = sortEvents(savedEvents).map((event) => {
    const { dateTime, source, sourceId, imageUrl, eventName, venue } =
      event.event;
    return (
      <EventCard
        key={event.savedEventId}
        dateTime={dateTime}
        source={source}
        sourceId={sourceId}
        imageUrl={imageUrl}
        eventName={eventName}
        venue={venue}
      />
    );
  });

  return (
    <div className="SavedEvents">
      <Header />
      <div className="container">
        <h1>Saved Events</h1>
        <div className="SavedEvents__events-container">
          {renderedSavedEvents}
        </div>
      </div>
    </div>
  );
};

export default SavedEvents;
