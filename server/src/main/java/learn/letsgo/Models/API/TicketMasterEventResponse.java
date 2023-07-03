package learn.letsgo.Models.API;

import java.util.List;

public class TicketMasterEventResponse {

    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class Embedded {
        private List<TicketMasterEvent> events;

        public List<TicketMasterEvent> getEvents() {
            return events;
        }

        public void setEvents(List<TicketMasterEvent> events) {
            this.events = events;
        }
    }
}
