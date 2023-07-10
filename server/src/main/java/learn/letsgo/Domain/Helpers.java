package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Data.BridgeTableRepository;
import learn.letsgo.Data.EventRepository;
import learn.letsgo.Data.VenueRepository;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Identifiable;
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

    public static String capitalizeFirst(String lowercaseStr) {
        return lowercaseStr.substring(0, 1).toUpperCase() + lowercaseStr.substring(1);
    }

    public static <S, T> Result<T> validateBothEntitiesExist(BridgeTableRepository<S> childRepo,
                                                                int childId, String childName,
                                                                BridgeTableRepository<T> parentRepo,
                                                                int parentId, String parentName) {
        Result<T> result = new Result<>();
        S child = childRepo.findById(childId);
        if (child == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find %s with %sId: %s", childName, childName, childId));
        }

        T parent = parentRepo.findById(parentId);
        if (parent == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find %s with %sId: %s", parentName, parentName, parentId));
        }
        return result;
    }

    public static <S extends Identifiable, T> Result<T> validateCanPerformBridgeAction(int childId, String childName, String parentName,
            List<S> parentListOfChild, BridgeTableOperation operation) {
        Result<T> result = new Result<>();

            boolean containsChild = parentListOfChild.stream().map(c -> c.getId()).anyMatch(id -> id == childId);

            if (containsChild && operation == BridgeTableOperation.ADD) {
                result.addMessage(ResultType.INVALID,
                        String.format("%s with id %s already in %s", capitalizeFirst(childName), childId, parentName));
            } else if (!containsChild && operation == BridgeTableOperation.REMOVE) {
                result.addMessage(ResultType.INVALID,
                        String.format("%s with id %s not in %s so cannot be removed", capitalizeFirst(childName),
                                childId, parentName));
            }
        return result;
    }
}
