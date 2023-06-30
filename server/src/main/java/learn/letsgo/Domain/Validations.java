package learn.letsgo.Domain;

import learn.letsgo.Data.EventRepository;
import learn.letsgo.Models.Event;

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
}
