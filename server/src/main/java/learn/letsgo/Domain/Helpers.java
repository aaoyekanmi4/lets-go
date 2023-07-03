package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Data.EventRepository;
import learn.letsgo.Data.VenueRepository;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static Event findEventIfExists(Event current, EventRepository repository) {
        List<Event> events = repository.findAll();
        for (Event event : events) {
            if (event.getSource().equals(current.getSource()) && event.getSourceId().equals(current.getSourceId())) {
                return event;
            }
        }
        return null;
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

    public static String getUserFullName(AppUserRepository userRepository, int appUserId) {
        AppUser user = userRepository.findById(appUserId);
        if (user == null) {
            return null;
        }
        return user.getFirstName() + " " + user.getLastName();
    }

    public static boolean isValidEmail(String email) {
        //RFC-5322
        String emailRegex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
