import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { AiOutlineStar } from "react-icons/ai";
import moment from "moment";

import { saveEvent } from "./helpers";
import AuthenticateModal from "../AuthenticateModal/AuthenticateModal.js";
import "./EventCard.scss";

const EventCard = ({
  imageUrl,
  dateTime,
  eventName,
  venue,
  sourceId,
  source,
}) => {
  const user = useSelector((state) => {
    return state.user;
  });

  const [showAuthenticateModal, setShowAuthenticateModal] = useState(false);

  const [saveEventErrors, setSaveEventErrors] = useState([]);

  const eventData = {
    imageUrl,
    eventName,
    dateTime,
    venue,
    sourceId,
    source,
  };

  const onSaveClick = async (e) => {
    if (!user) {
      setShowAuthenticateModal(true);
    } else {
      const response = await saveEvent(
        user.appUserId,
        user.jwtToken,
        eventData
      );

      if (response.status === 201) {
        //
      } else {
        setSaveEventErrors(response.errors);
      }
    }

    e.preventDefault();

    e.stopPropagation();

    e.nativeEvent.stopImmediatePropagation();
  };

  return (
    <>
      <Link className="EventCard" to="/contacts">
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
            <AiOutlineStar className="EventCard__icon" />
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
