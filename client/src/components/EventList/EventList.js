// EventList.js
import React, { useState } from "react";

import normalizeApiEvents from "../../normalizeApiEvents";
import useResultIndicator from "../../hooks/useResultIndicator.js";
import getResultIndicator from "../../getResultIndicator.js";
import EventCard from "../EventCard/EventCard.js";

let saveEventResultId;

const EventList = ({ events }) => {
  const [showSaveEventResult, setShowSaveEventResult] =
    useResultIndicator(saveEventResultId);

  return (
    <>
      <div>
        {normalizeApiEvents(events).map((event) => (
          <EventCard
            sourceId={event.sourceId}
            key={event.sourceId}
            dateTime={event.dateTime}
            venue={event.venue}
            source={event.source}
            eventName={event.eventName}
            imageUrl={event.imageUrl}
            setShowSaveEventResult={setShowSaveEventResult}
          />
        ))}
      </div>

      {showSaveEventResult.show
        ? getResultIndicator(showSaveEventResult.type, {
            operation: "save",
          })
        : null}
    </>
  );
};

export default EventList;
