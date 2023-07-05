import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { AiOutlineStar } from "react-icons/ai";
import { AiFillStar } from "react-icons/ai";
import moment from "moment";

import { getSavedEvents } from "../../actions";
import { saveEvent, removeEvent } from "./helpers";
import AuthenticateModal from "../AuthenticateModal/AuthenticateModal.js";
import "./EventCard.scss";

const EventCard = ({ eventData, setEventResultIndicator }) => {
  const {
    imageUrl,
    eventName,
    dateTime,
    venue,
    sourceId,
    source,
    eventId = null, //if event is coming from ticketmaster or seatgeek api, there is no eventId
  } = eventData;

  const dispatch = useDispatch();

  const user = useSelector((state) => {
    return state.user;
  });

  const savedEvents = useSelector((state) => {
    return state.savedEvents;
  });

  const [showAuthenticateModal, setShowAuthenticateModal] = useState(false);

  const [saveEventErrors, setSaveEventErrors] = useState([]);

  const onSaveClick = async (e) => {
    e.preventDefault();

    e.stopPropagation();

    e.nativeEvent.stopImmediatePropagation();

    if (!user) {
      setShowAuthenticateModal(true);
      return true;
    }

    if (!isEventSaved()) {
      const response = await saveEvent(user.appUserId, user.jwtToken, {
        imageUrl,
        eventName,
        dateTime,
        venue,
        sourceId,
        source,
      });

      if (response.status === 201) {
        await dispatch(getSavedEvents());
        displayEventResultIndicator("success", "save");
      } else {
        displayEventResultIndicator("fail", "save");
      }
    }

    if (isEventSaved()) {
      const response = await removeEvent(
        user.appUserId,
        user.jwtToken,
        savedEvents[sourceId]["event"]["eventId"]
      );

      if (response.status === 204) {
        await dispatch(getSavedEvents());
        displayEventResultIndicator("success", "delete");
      } else {
        displayEventResultIndicator("fail", "delete");
      }
    }
  };

  const displayEventResultIndicator = (outcome, operation) => {
    let message = "";

    if (outcome === "success") {
      if (operation === "delete") {
        message = "Removed from saved events";
      } else {
        message = "Added to saved events";
      }
    } else if (outcome === "fail") {
      if (operation === "delete") {
        message = "Unable to remove from saved events";
      } else {
        message = "Unable to add to saved events";
      }
    }

    setEventResultIndicator({
      show: true,
      outcome: outcome,
      operation: operation,
      message: message,
    });
  };

  const isEventSaved = () => {
    return savedEvents[sourceId] ? true : false;
  };

  return (
    <>
      <Link className="EventCard" to={`events/${sourceId}`}>
        <div className="EventCard__image-container">
          <img src={imageUrl} className="EventCard__image" alt={eventName} />
        </div>

        <div className="EventCard__details">
          <div className="EventCard__month-date">
            <p className="EventCard__month">{moment(dateTime).format("MMM")}</p>
            <p className="EventCard__date">{moment(dateTime).date()}</p>
          </div>
          <div className="EventCard__info">
            <h2 className="EventCard__name">{eventName}</h2>
            <p className="EventCard__detail-text">
              {venue ? venue.name : null}
            </p>
            <p className="EventCard__detail-text">
              {`${moment(dateTime).format("dddd, MMMM Do YYYY")} at ${moment(
                dateTime
              ).format("h:mm a")}`}
            </p>
          </div>

          <span
            type="button"
            className="EventCard__icon-button"
            onClick={onSaveClick}
          >
            {isEventSaved() ? (
              <AiFillStar className="EventCard__icon-saved" />
            ) : (
              <AiOutlineStar className="EventCard__icon-not-saved" />
            )}
          </span>
        </div>
      </Link>

      {showAuthenticateModal ? (
        <AuthenticateModal
          closeModal={() => {
            setShowAuthenticateModal(false);
          }}
        />
      ) : null}
    </>
  );
};

export default EventCard;
