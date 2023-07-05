import React, { useState } from "react";
import { useSelector } from "react-redux";
import moment from "moment";
import Header from "../../../components/Header/Header.js";
import EventCard from "../../../components/EventCard/EventCard.js";
import EventsList from "../../../components/EventsList/EventsList.js";

import SearchField from "../../../components/SearchField/SearchField.js";
import "./SavedEvents.scss";

const SavedEvents = () => {
  const savedEvents = useSelector((state) => {
    return state.savedEvents;
  });

  const savedEventsArray = Object.values(savedEvents).map((savedEvent) => {
    return savedEvent.event;
  });

  const [eventsearchValue, setEventsSearchValue] = useState("");

  const [venueSearchValue, setVenueSearchValue] = useState("");

  const [sortBy, setSortBy] = useState("eventName");

  const sortEvents = (events) => {
    if (sortBy === "eventName") {
      events.sort((a, b) => {
        if (a.eventName.toLowerCase() < b.eventName.toLowerCase()) {
          return -1;
        } else if (a.eventName.toLowerCase() > b.eventName.toLowerCase()) {
          return 1;
        } else {
          return 0;
        }
      });
    }

    if (sortBy === "date") {
      events.sort((a, b) => {
        if (moment(a.dateTime).utc() < moment(b.dateTime).utc()) {
          return -1;
        } else if (moment(a.dateTime).utc() > moment(b.dateTime).utc()) {
          return 1;
        } else {
          return 0;
        }
      });
    }

    return events;
  };

  return (
    <div className="SavedEvents">
      <Header />
      <div className="SavedEvents__main">
        <div className="SavedEvents__side-actions">
          <p>search by</p>
        </div>
        <EventsList
          events={sortEvents(savedEventsArray)}
          listTitle="Saved Events"
        />
      </div>
    </div>
  );
};

export default SavedEvents;
