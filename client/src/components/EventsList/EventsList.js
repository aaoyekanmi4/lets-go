import React from "react";

import useResultIndicator from "../../hooks/useResultIndicator.js";
import getResultIndicator from "../../getResultIndicator.js";
import EventCard from "../EventCard/EventCard.js";
import "./EventsList.scss";

let eventResultIndicatorId;

const EventsList = ({ events, listTitle }) => {
  const [eventResultIndicator, setEventResultIndicator] = useResultIndicator(
    eventResultIndicatorId
  );

  return (
    <>
      <div className="EventsList">
        <div className="container">
          <h1 className="EventsList__header">{listTitle}</h1>
          <div
            className="EventsList__events-container"
            style={
              events.length >= 4
                ? {
                    gridTemplateColumns: "repeat(auto-fit, minmax(28rem, 1fr))",
                  }
                : { gridTemplateColumns: "repeat(auto-fit, 30rem" }
            }
          >
            {events.map((event) => (
              <EventCard
                key={event.sourceId}
                eventData={event}
                setEventResultIndicator={setEventResultIndicator}
              />
            ))}
          </div>
        </div>
      </div>

      {eventResultIndicator.show
        ? getResultIndicator(eventResultIndicator)
        : null}
    </>
  );
};

export default EventsList;
