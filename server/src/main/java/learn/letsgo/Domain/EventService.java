package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Data.EventRepository;
import learn.letsgo.Models.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    public EventService(EventRepository eventRepository, AppUserRepository appUserRepository) {
        this.eventRepository = eventRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Event> findAllByUserId(int appUserId) {
        return eventRepository.findAllByUserId(appUserId);
    }

    public Event findById(int eventId) {
        return eventRepository.findById(eventId);
    }

    //TODO test save event
    public Result<Event> save(Event event, int appUserId) {
        Result<Event> result = validate(event);
        if (!result.isSuccess()) {
            return result;
        }
        if (Validations.eventExistsInDatabase(event, eventRepository)) {
            boolean didAddEventToUser = appUserRepository.addEventToUser(event.getEventId(), appUserId);
            if (!didAddEventToUser) {
                result.addMessage(ResultType.INVALID, "Could not add event to saved events");
                return result;
            }
            result.setPayload(event);
        } else {
            Event newEvent = eventRepository.create(event);
            if (newEvent != null) {
                boolean didAddEventToUser = appUserRepository.addEventToUser(newEvent.getEventId(), appUserId);
                if (!didAddEventToUser) {
                    result.addMessage(ResultType.INVALID, "Could not add event to saved events");
                    return result;
                }
                result.setPayload(newEvent);
            } else {
                result.addMessage(ResultType.INVALID, "Could not create event");
            }
        }
        return result;
    }

//    public Result<Void> removeEventFromUser(int eventId, int appUserId) {
//        Result<Void> result = new Result<>();
//        boolean didRemoveEvent = appUserRepository.removeEventFromUser(eventId, appUserId);
//        if (!didRemoveEvent) {
//            result.addMessage(ResultType.INVALID, "Could not remove event from saved events");
//        }
//        return result;
//    }

    public Result<Event> update(Event event) {
        Result<Event> result = validate(event);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didUpdate = eventRepository.update(event);
        if (!didUpdate) {
            String msg = String.format("eventId: %s, not found", event.getEventId());
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    public boolean deleteById(int eventId) {
        return eventRepository.deleteById(eventId);
    }

    private Result<Event> validate(Event event) {
        Result<Event> result = new Result<>();
        if (event == null) {
            result.addMessage(ResultType.INVALID, "Event cannot be null");
            return result;
        }
        if (Validations.isNullOrBlank(event.getEventName())) {
            result.addMessage(ResultType.INVALID, "Event name is required");
        }
        if (event.getDateTime() == null) {
            result.addMessage(ResultType.INVALID, "Datetime is required");
        }
        return result;
    }
}
