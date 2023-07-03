// EventList.js

import React from "react";
import EventCard from "../EventCard/EventCard.js";

const EventList = ({ events }) => {
    return (
        <div>
            {events.map((event, index) => (
                <EventCard
                    key={index}
                    dateTime={event.datetime_local || (event.dates && event.dates.start && event.dates.start.dateTime)}
                    venue={(event.venue && event.venue.name) || (event._embedded.venues[0].name)}
                    eventName={event.title || event.name}
                    imageUrl={(event.performers && event.performers[0].images.huge) || (event.images && event.images[0].url)}
                />
            ))}
        </div>
    );
}

export default EventList;
