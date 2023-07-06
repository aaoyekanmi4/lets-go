import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import moment from "moment";
import { useParams, Link, useNavigate } from "react-router-dom";

import { findApiEvent } from "./helpers.js";
import Header from "../../../components/Header/Header.js";
import ErrorPage from "../../../components/ErrorPage/ErrorPage.js";
import EventPosts from "./EventPosts/EventPosts.js";
import { getSavedEvents } from "../../../actions";
import { saveEvent } from "../../..//components/EventCard/helpers.js";
import useResultIndicator from "../../../hooks/useResultIndicator.js";
import getResultIndicator from "../../../getResultIndicator.js";
import AttachedGroups from "./AttachedGroups/AttachedGroups.js";
import "./EventDetails.scss";

const defaultValues = {
  dateTime: "",
  eventLink: "",
  eventName: "",
  imageUrl: "",
  source: "",
  sourceId: "",
  venue: {
    venueId: "",
    venueName: "",
    address: "",
    city: "",
    state: "",
    country: "",
    zipCode: "",
  },
};

let saveResultIndicatorId;

const EventDetails = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  //sourceValue of 1 is TickerMaster, 2 is SeatGeek
  const user = useSelector((state) => {
    return state.user;
  });

  const { sourceValue, sourceId } = useParams();

  const savedEvents = useSelector((state) => {
    return state.savedEvents;
  });

  const [details, setDetails] = useState(defaultValues);

  const [errorMessages, setErrorMessages] = useState([]);

  const [saveResultIndicator, setSaveResultIndicator] = useResultIndicator(
    saveResultIndicatorId
  );

  useEffect(() => {
    const getDetails = async () => {
      const response = await findApiEvent(sourceValue, sourceId);

      if (response.status === 200) {
        setDetails(response.data[0]);
      } else {
        //if error getting the event details currently(most likely
        // because no longer existing), but event is already saved,
        // upload saved event. Otherwise,send error
        if (savedEvents[sourceId] && savedEvents[sourceId]["event"]) {
          setDetails(savedEvents[sourceId]["event"]);
        } else {
          setErrorMessages(response.errorMessages);
        }
      }
    };

    getDetails();
  }, []);

  const onSaveClick = async () => {
    if (!user) {
      navigate("/login");
    }

    const response = await saveEvent(user.appUserId, user.jwtToken, details);

    if (response.status === 201) {
      await dispatch(getSavedEvents());

      setSaveResultIndicator({
        show: true,
        outcome: "success",
        operation: "save",
        message: "Added to my saved account",
      });
    } else {
      setSaveResultIndicator({
        show: true,
        outcome: "fail",
        operation: "save",
        message: "Unable to add to my saved account",
      });
    }
  };

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
      return (
        <p className="EventDetails__click-to-save">
          Save this event to see the discussion around it.{" "}
          <span
            className="button-text button-text--primary"
            onClick={onSaveClick}
          >
            Click here to save this event
          </span>
        </p>
      );
    }

    return (
      <EventPosts
        user={user}
        eventId={savedEvents[sourceId]["event"]["eventId"]}
      />
    );
  };

  const renderEventGroups = () => {
    if (!user) {
      return (
        <p>
          You must be logged in to add groups to this event{" "}
          <Link to="/login" className="button-text button-text--primary">
            Click to log in
          </Link>
        </p>
      );
    }

    if (!savedEvents[sourceId]) {
      return (
        <p>
          <span
            className="EventDetails__save-link button-text button-text--primary"
            onClick={onSaveClick}
          >
            Save this event to add groups to it
          </span>
        </p>
      );
    }

    return (
      <div className="EventDetails__groups">
        <AttachedGroups sourceId={sourceId} details={details} />
      </div>
    );
  };

  const displayContent = () => {
    if (errorMessages.length) {
      return <ErrorPage message="Unable to find event" />;
    }

    return (
      <>
        <div className="EventDetails">
          <Header />
          <div className="EventDetails__main">
            <div className="EventDetails__container container">
              <div className="EventDetails__info">
                <div className="EventDetails__info-main">
                  <h1 className="EventDetails__name">{details.eventName}</h1>
                  <div className="EventDetails__image-container">
                    <img
                      className="EventDetails__image"
                      src={details.imageUrl}
                    />
                  </div>
                  <p className="EventDetails__detail-text">
                    {details.dateTime
                      ? `${moment(details.dateTime).format(
                          "dddd, MMMM Do YYYY"
                        )} at ${moment(details.dateTime).format("h:mm a")}`
                      : null}
                  </p>
                  <address className="EventDetails__venue EventDetails__detail-text">
                    {details.venue.venueName} <br />
                    {details.venue.address}
                    <br />
                    <span>{`${details.venue.city},
                  ${details.venue.state}, ${details.venue.zipCode}`}</span>
                    <br />
                    {details.venue.country}
                  </address>
                  <a
                    href={details.eventLink}
                    target="new_page"
                    className="EventDetails__link EventDetails__detail-text"
                  >
                    {details.eventLink ? "Go to event link" : null}
                  </a>
                </div>

                {renderEventGroups()}
              </div>
              {renderEventPosts()}
            </div>
          </div>
        </div>

        {saveResultIndicator.show
          ? getResultIndicator(saveResultIndicator)
          : null}
      </>
    );
  };

  return displayContent();
};

export default EventDetails;
