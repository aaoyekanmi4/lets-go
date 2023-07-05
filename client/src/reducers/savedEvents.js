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
    case types.CLEAR_SAVED_EVENTS:
      return [];
    default:
      return state;
  }
};

export default savedEvents;
