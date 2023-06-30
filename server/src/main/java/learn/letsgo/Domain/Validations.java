package learn.letsgo.Domain;

import learn.letsgo.Data.EventRepository;
import learn.letsgo.Data.VenueRepository;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;

import java.util.List;

public class Validations {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean eventExistsInDatabase(Event current, EventRepository repository) {
        List<Event> events = repository.findAll();
        for (Event event : events) {
            if (event.getSource().equals(current.getSource()) && event.getSourceId().equals(current.getSource())) {
                return true;
            }
        }
        return false;
    }

    public static Venue findVenueIfExists(Venue current, VenueRepository repository) {
        List<Venue> venues = repository.findAll();
        for (Venue venue: venues)
            if (venue.getZipCode() == current.getZipCode()
            && venue.getVenueName().equals(current.getVenueName())
            && venue.getAddress().equals(current.getAddress())
            && venue.getCity().equals(current.getCity())) {
                return venue;
            }
        return null;
    }
}
