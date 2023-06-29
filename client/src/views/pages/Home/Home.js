import React from "react";

import Header from "../../../components/Header/Header.js";
import EventCard from "../../../components/EventCard/EventCard.js";

import "./Home.scss";
const Home = () => {
  return (
    <div className="Home">
      <Header />
      <EventCard
        dateTime="2016-03-18T14:00:00Z"
        venue="Madison Square Garden"
        eventName="WGC Cadillac Championship - Sunday Ticket"
        image="http://s1.ticketm.net/dam/a/063/1689bfea-ae98-4c7e-a31d-bbca2dd14063_54361_RECOMENDATION_16_9.jpg"
        category="Music"
      />
    </div>
  );
};

export default Home;
