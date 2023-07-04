import React, { useEffect, useState } from "react";
import Header from "../../../components/Header/Header.js";
import EventList from "../../../components/EventList/EventList.js";
import "./Home.scss";

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
        console.error(e);
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
      <form onSubmit={handleSearch}>
        <label>
          Postal Code:
          <input
            type="text"
            value={postalCode}
            onChange={(e) => setPostalCode(e.target.value)}
          />
        </label>
        <button type="submit">Search</button>
      </form>
      <EventList events={events} />
    </div>
  );
};

export default Home;
