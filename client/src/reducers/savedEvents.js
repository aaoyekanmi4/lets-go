import types from "../actions/types.js";

const savedEvents = (state = [], action) => {
  switch (action.type) {
    case types.GET_SAVED_EVENTS: {
      const events = {};
      action.payload.forEach(
        (savedEvent) => (events[savedEvent.event.sourceId] = savedEvent)
      );
      return events;
    }
    default:
      return state;
  }
};

export default savedEvents;
