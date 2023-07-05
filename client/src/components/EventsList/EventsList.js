import React from "react";

import useResultIndicator from "../../hooks/useResultIndicator.js";
import getResultIndicator from "../../getResultIndicator.js";
import EventCard from "../EventCard/EventCard.js";
import "./EventsList.scss";

let saveEventResultId;

const EventsList = ({ events, listTitle }) => {
  const [showSaveEventResult, setShowSaveEventResult] =
    useResultIndicator(saveEventResultId);

  return (
    <>
      <div className="EventsList">
        <div className="container">
          <h1 className="EventsList__header">{listTitle}</h1>
          <div className="EventsList__events-container">
            {events.map((event) => (
              <EventCard
                key={event.sourceId}
                eventData={event}
                setShowSaveEventResult={setShowSaveEventResult}
              />
            ))}
          </div>
        </div>
      </div>

      {showSaveEventResult.show
        ? getResultIndicator(showSaveEventResult.type, {
            operation: "save",
          })
        : null}
    </>
  );
};

export default EventsList;
