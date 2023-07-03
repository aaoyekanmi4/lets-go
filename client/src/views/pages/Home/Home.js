import React from "react";
import { Link } from "react-router-dom";
import Header from "../../../components/Header/Header.js";
import EventCard from "../../../components/EventCard/EventCard.js";
import ContactCard from "../../../components/ContactCard/ContactCard.js";
import GroupCard from "../../../components/GroupCard/GroupCard.js";
import CreateGroup from "../../../components/forms/CreateGroupForm/CreateGroupForm.js";
import EventList from "../../../components/EventList/EventList.js";

import "./Home.scss";
const Home = () => {
  return (
    <div className="Home">
      <Header />
      <EventCard
        dateTime="2016-03-18T14:00:00Z"
        venue="Madison Square Garden"
        eventName="WGC Cadillac Championship - Sunday Ticket"
        imageUrl="http://s1.ticketm.net/dam/a/063/1689bfea-ae98-4c7e-a31d-bbca2dd14063_54361_RECOMENDATION_16_9.jpg"
        category="Music"
      />
      <ContactCard
        firstName="Eric"
        lastName="Baffour-Addo"
        email="ericdoes13@gmail.com"
        phone="6788022654"
      />
      <Link
        to="/contacts/1
      "
      >
        Create Group
      </Link>
      <GroupCard groupName="The Ballers we call" />
      <EventList />
    </div>
  );
};

export default Home;
