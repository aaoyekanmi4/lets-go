package learn.letsgo.Controller;

import learn.letsgo.Domain.EventService;
import learn.letsgo.Domain.Result;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.SavedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/user/{appUserId}")
    public List<Event> findAllByUserId(@PathVariable int appUserId) {
        return eventService.findAllByUserId(appUserId);
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable int eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/user/{eventId}/{appUserId}")
    public SavedEvent findSavedEventForUser(@PathVariable int eventId, @PathVariable int appUserId) {
        return eventService.findSavedEventForUser(eventId, appUserId);
    }

    @PostMapping("/user/{appUserId}")
    public ResponseEntity<Object> saveEventToUser(@RequestBody Event event, @PathVariable int appUserId) {
        Result<Event> result = eventService.saveEventToUser(event, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/{eventId}/{appUserId}")
    public ResponseEntity<?> removeEventFromUser(@PathVariable int eventId, @PathVariable int appUserId) {
        Result<Void> result = eventService.removeEventFromUser(eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/user/contact/{contactId}/{eventId}/{appUserId}")
    public ResponseEntity<?> addContactToEvent (@PathVariable int contactId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int appUserId) {
        Result<Void> result = eventService.addContactToEvent(contactId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/contact/{contactId}/{eventId}/{appUserId}")
    public ResponseEntity<?> removeContactFromEvent(@PathVariable int contactId,
                                                    @PathVariable int eventId,
                                                    @PathVariable int appUserId) {
        Result<Void> result = eventService.removeContactFromEvent(contactId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/user/group/{groupId}/{eventId}/{appUserId}")
    public ResponseEntity<?> addGroupToEvent (@PathVariable int groupId,
                                                @PathVariable int eventId,
                                                @PathVariable int appUserId) {
        Result<Void> result = eventService.addGroupToEvent(groupId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/group/{groupId}/{eventId}/{appUserId}")
    public ResponseEntity<?> removeGroupFromEvent(@PathVariable int groupId,
                                                    @PathVariable int eventId,
                                                    @PathVariable int appUserId) {
        Result<Void> result = eventService.removeGroupFromEvent(groupId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
