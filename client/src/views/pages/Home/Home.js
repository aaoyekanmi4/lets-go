import React, { useEffect, useState } from "react";

import Header from "../../../components/Header/Header.js";
import EventsList from "../../../components/EventsList/EventsList.js";
import "./Home.scss";
import normalizeApiEvents from "../../../normalizeApiEvents.js";
let saveEventResultIndicatorId;
import SearchField from "../../../components//SearchField/SearchField.js";
const Home = () => {
  const [events, setEvents] = useState([]);
  const [postalCode, setPostalCode] = useState("");

  useEffect(() => {
    fetchEvents(postalCode);
  }, [postalCode]);

  const fetchEvents = (postalCode = "") => {
    Promise.all([
      fetch(`http://localhost:8080/seatgeek/events?postalCode=${postalCode}`),
      fetch(
        `http://localhost:8080/ticketmaster/events?postalCode=${postalCode}`
      ),
    ])
      .then(async ([res1, res2]) => {
        const seatGeekEvents = await res1.json();
        const ticketMasterEvents = await res2.json();
        return [...seatGeekEvents, ...ticketMasterEvents];
      })
      .then((combinedEvents) => {
        setEvents(combinedEvents);
      })
      .catch((e) => {
        setEvents([]);
      });
  };

  const handleSearch = (e) => {
    e.preventDefault();
    fetchEvents(postalCode);
  };

  return (
    <div className="Home">
      <Header />

      <div className="Home__search-field-container">
        <SearchField
          placeholder="Enter your postal code"
          onChange={setPostalCode}
          value={postalCode}
          onSearch={(e) => setPostalCode(e.target.value)}
        />
      </div>

      <EventsList
        events={normalizeApiEvents(events)}
        listTitle="All events near you"
      />
    </div>
  );
};

export default Home;
