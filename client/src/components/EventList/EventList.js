// EventList.js

import normalizeApiEvents from "../../normalizeApiEvents";
import React from "react";
import EventCard from "../EventCard/EventCard.js";

const EventList = ({ events }) => {
  return (
    <div>
      {normalizeApiEvents(events).map((event, index) => (
        <EventCard
          sourceId={event.sourceId}
          key={event.sourceId}
          dateTime={event.dateTime}
          venue={event.venue}
          source={event.source}
          eventName={event.eventName}
          imageUrl={event.imageUrl}
        />
      ))}
    </div>
  );
};

export default EventList;
