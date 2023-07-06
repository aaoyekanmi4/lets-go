import React, { useEffect, useState } from "react";

import Header from "../../../components/Header/Header.js";
import EventsList from "../../../components/EventsList/EventsList.js";
import "./Home.scss";
import normalizeApiEvents from "../../../normalizeApiEvents.js";
let saveEventResultIndicatorId;
import SearchField from "../../../components//SearchField/SearchField.js";

const api_url = "https://api.opencagedata.com/geocode/v1/json";
const api_key = "504e225fe87f4659a2b35a2d377d922b";

const findZipCodeFromUserLocation = async (lat, lng) => {
  const query = `${lat},${lng}`;
  const requestUrl = `${api_url}?key=${api_key}&q=${encodeURIComponent(
    query
  )}&pretty=1&no_annotations=1`;

  const response = await fetch(requestUrl);
  if (response.status !== 200) {
    throw new Error("Could not get postal code from user location");
  }
  const responseJSON = await response.json();
  return responseJSON.results[0].components.postcode;
};

const Home = () => {
  const [events, setEvents] = useState([]);
  const [postalCode, setPostalCode] = useState("");

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onGeolocationSuccess,
        onGeolocationError
      );
    } else {
      console.log("Geolocation not supported");
    }
  }, []);

  const onGeolocationSuccess = async (position) => {
    const { latitude, longitude } = position.coords;
    try {
      const postalCode = await findZipCodeFromUserLocation(latitude, longitude);
      setPostalCode(postalCode);
      fetchEvents(postalCode);
    } catch (err) {
      console.log(err);
    }
  };

  const onGeolocationError = (error) => {};

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

  useEffect(() => {
    fetchEvents(postalCode);
  }, [postalCode]);

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
