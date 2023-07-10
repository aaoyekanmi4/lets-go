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
        Result<Event> result = validateFields(event);
        if (!result.isSuccess()) {
            return result;
        }
        Event existingEvent = Helpers.findEventIfExists(event, eventRepository);

        if (existingEvent != null) {
            result = validateCanBridgeEventToUser(existingEvent.getEventId(), appUserId, BridgeTableOperation.ADD);
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
                result = validateCanBridgeEventToUser(newEvent.getEventId(), appUserId, BridgeTableOperation.ADD);
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
        Result<Event> result = validateCanBridgeEventToUser(eventId, appUserId, BridgeTableOperation.REMOVE);
        boolean didRemoveEvent = savedEventRepository.removeEventFromUser(eventId, appUserId);
        if (!didRemoveEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove event from saved events");
        }
        return result;
    }

    public Result<Event> update(Event event) {
        Result<Event> result = validateFields(event);
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

    private Result<Event> validateFields(Event event) {
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

    private Result<Event> validateCanBridgeEventToUser (int eventId, int appUserId, BridgeTableOperation operation) {
        Result<Event> result = Helpers.validateBothEntitiesExist(
                appUserRepository, appUserId, "user", eventRepository, eventId, "event");
        if (!result.isSuccess()) {
            return result;
        }
        if (operation != BridgeTableOperation.UPDATE) {
            List<Event> eventsList = appUserRepository.findById(appUserId).getEvents();

            result = Helpers.validateCanPerformBridgeAction(eventId, "event", "user",
                    eventsList, operation);
        }
        return result;
    }
}
