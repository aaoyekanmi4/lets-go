import React, { useState, useEffect } from "react";
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

  let savedEventsArray = Object.values(savedEvents).map((savedEvent) => {
    return savedEvent.event;
  });

  const [filter, setFilter] = useState("eventName");

  const [searchValue, setSearchValue] = useState("");

  const [sortBy, setSortBy] = useState("eventName");

  const getFilteredSavedEvents = () => {
    if (filter === "eventName") {
      return savedEventsArray.filter((event) =>
        event.eventName.toLowerCase().includes(searchValue.toLowerCase())
      );
    }

    if (filter === "venue") {
      return savedEventsArray.filter((event) =>
        event.venue.venueName.toLowerCase().includes(searchValue.toLowerCase())
      );
    }
  };

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
        <div className="container">
          <div className="SavedEvents__search-and-filter">
            <div className="SavedEvents__search-field-container">
              <SearchField
                placeholder="Search for event..."
                value={searchValue}
                onChange={setSearchValue}
                onSearch={() => {}}
              />
            </div>

            <div className="SavedEvents__sort">
              <select
                onChange={(e) => {
                  setSortBy(e.target.value);
                }}
                className="SavedEvents__select"
                default={sortBy}
              >
                <option selected={sortBy === "eventName"} value="eventName">
                  Sort by event name
                </option>
                <option selected={sortBy === "date"} value="date">
                  Sort by date
                </option>
              </select>
            </div>
          </div>
          <EventsList
            events={sortEvents(getFilteredSavedEvents())}
            listTitle="Saved Events"
          />
        </div>
      </div>
    </div>
  );
};

export default SavedEvents;
