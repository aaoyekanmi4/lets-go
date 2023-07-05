package learn.letsgo.Domain;

import learn.letsgo.Data.*;
import learn.letsgo.Models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final SavedEventRepository savedEventRepository;
    private final AppUserRepository appUserRepository;
    private final GroupRepository groupRepository;
    private final ContactRepository contactRepository;

    public EventService(EventRepository eventRepository, SavedEventRepository savedEventRepository, AppUserRepository appUserRepository, GroupRepository groupRepository, ContactRepository contactRepository) {
        this.eventRepository = eventRepository;
        this.savedEventRepository = savedEventRepository;
        this.appUserRepository = appUserRepository;
        this.groupRepository = groupRepository;

        this.contactRepository = contactRepository;
    }

    public List<Event> findAllByUserId(int appUserId) {
        return eventRepository.findAllByUserId(appUserId);
    }

    public Event findById(int eventId) {
        return eventRepository.findById(eventId);
    }

    public SavedEvent findSavedEventForUser(int eventId, int appUserId) {
        return savedEventRepository.findSavedEventForUser(eventId, appUserId);
    }

    public Result<Event> saveEventToUser(Event event, int appUserId) {
        Result<Event> result = validate(event);
        if (!result.isSuccess()) {
            return result;
        }
        Event existingEvent = Helpers.findEventIfExists(event, eventRepository);

        if (existingEvent != null) {
            result = validateCanPerformBridgeAction(existingEvent.getEventId(), appUserId, true);
            if (!result.isSuccess()) {
                return result;
            }
            boolean didAddEventToUser = savedEventRepository.addEventToUser(existingEvent.getEventId(), appUserId);
            if (!didAddEventToUser) {
                result.addMessage(ResultType.INVALID, "Could not add event to saved events");
                return result;
            }
            result.setPayload(existingEvent);
        } else {
            Event newEvent = eventRepository.create(event);
            if (newEvent != null) {
                result = validateCanPerformBridgeAction(newEvent.getEventId(), appUserId, true);
                if (!result.isSuccess()) {
                    return result;
                }
                boolean didAddEventToUser = savedEventRepository.addEventToUser(newEvent.getEventId(), appUserId);
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

    public Result<Event> removeEventFromUser(int eventId, int appUserId) {
        Result<Event> result = validateCanPerformBridgeAction(eventId, appUserId, false);
        boolean didRemoveEvent = savedEventRepository.removeEventFromUser(eventId, appUserId);
        if (!didRemoveEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove event from saved events");
        }
        return result;
    }



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
        if (Helpers.isNullOrBlank(event.getEventName())) {
            result.addMessage(ResultType.INVALID, "Event name is required");
        }
        if (event.getDateTime() == null) {
            result.addMessage(ResultType.INVALID, "Datetime is required");
        }
        return result;
    }

    //TODO REFACTOR 4: EventForUser, ContactsForGroup, GroupsForEvent, ContactsForEvent
    private Result<Event> validateCanPerformBridgeAction(int eventId, int appUserId, boolean isAdding) {

        Result<Event> result = new Result<>();

        Event event = eventRepository.findById(eventId);

        if (event == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find event with eventId: %s", eventId));
        }

        AppUser user = appUserRepository.findById(appUserId);

        if (user == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find user with userId: %s", appUserId));
        } else {
            boolean alreadyHasEventSaved = user.getEvents()
                    .stream().map(currEvent -> currEvent.getEventId()).anyMatch(id -> id == eventId);

            if (alreadyHasEventSaved && isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Event with id %s already in saved events for user", eventId));
            } else if (!alreadyHasEventSaved && !isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Event with id %s not in user's saved events for removal", eventId));
            }
        }
        return result;
    }
}
