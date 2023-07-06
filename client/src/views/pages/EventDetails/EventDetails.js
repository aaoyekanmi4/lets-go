import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import moment from "moment";
import { useParams, Link } from "react-router-dom";

import { findApiEvent } from "./helpers.js";
import Header from "../../../components/Header/Header.js";
import EventPosts from "./EventPosts/EventPosts.js";
import "./EventDetails.scss";

const defaultValues = {
  dateTime: "",
  eventLink: "",
  eventName: "",
  imageUrl: "",
  source: "",
  sourceId: "",
  venue: {},
};

const EventDetails = () => {
  //sourceValue of 1 is TickerMaster, 2 is SeatGeek
  const user = useSelector((state) => {
    return state.user;
  });

  const savedEvents = useSelector((state) => {
    return state.savedEvents;
  });

  const { sourceValue, sourceId } = useParams();

  const [details, setDetails] = useState(defaultValues);

  const [errorMessages, setErrorMessages] = useState([]);

  useEffect(() => {
    const getDetails = async () => {
      const response = await findApiEvent(sourceValue, sourceId);
      if (response.status === 200) {
        setDetails(response.data[0]);
      } else {
        setErrorMessages(response.errorMessages);
      }
    };

    getDetails();
  }, []);

  const renderEventPosts = () => {
    if (!user) {
      return (
        <p>
          You must be logged in to see event posts{" "}
          <Link to="/login" className="button-text button-text--primary">
            Click to log in
          </Link>
        </p>
      );
    }

    if (!savedEvents[sourceId]) {
      return <p>Save this event to see the discussion around it</p>;
    }

    return (
      <EventPosts
        user={user}
        eventId={savedEvents[sourceId]["event"]["eventId"]}
      />
    );
  };

  return (
    <div className="EventDetails">
      <Header />
      <div className="EventDetails__main">
        <div className="EventDetails__container container">
          <div className="EventDetails__info">
            <div className="EventDetails__info-main">
              <h1 className="EventDetails__name">{details.eventName}</h1>
              <div className="EventDetails__image-container">
                <img className="EventDetails__image" src={details.imageUrl} />
              </div>
              <p className="EventDetails__detail-text">
                {`${moment(details.dateTime).format(
                  "dddd, MMMM Do YYYY"
                )} at ${moment(details.dateTime).format("h:mm a")}`}
              </p>
              <address className="EventDetails__venue EventDetails__detail-text">
                {details.venue.venueName} <br />
                {details.venue.address}
                <br />
                <span>{`${details.venue.city},
                ${details.venue.state}`}</span>
                <br />
                {details.venue.country}
              </address>
              <a
                href={details.eventLink}
                target="new_page"
                className="EventDetails__link EventDetails__detail-text"
              >
                Go to event link
              </a>
            </div>
            <div className="EventDetails__info-forms">
              <p>Info form</p>
            </div>
          </div>

          {renderEventPosts()}
        </div>
      </div>
    </div>
  );
};

export default EventDetails;
