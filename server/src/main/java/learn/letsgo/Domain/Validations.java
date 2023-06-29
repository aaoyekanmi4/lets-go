package learn.letsgo.Domain;

import learn.letsgo.Data.EventRepository;

public class Validations {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

//    public static boolean eventExistsInDatabase(String source, String source_id, EventRepository repository) {
//        //TODO need repository.findAll()
//        // List<Event> events = repository.findAll();
//        // for (Event event: events) {
//        // if (event.getSource().equals(current.getSource()) && event.getSourceId().equals(current.getSource()) {
//        //  return true;
//        // }
//        // return false;
//        // }
//    }
}
