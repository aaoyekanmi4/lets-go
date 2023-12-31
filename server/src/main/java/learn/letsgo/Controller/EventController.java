package learn.letsgo.Controller;

import learn.letsgo.Domain.EventService;
import learn.letsgo.Domain.Result;
import learn.letsgo.Domain.SavedEventService;
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

    public EventController(EventService eventService, SavedEventService savedEventService) {
        this.eventService = eventService;
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
        Result<Event> result = eventService.removeEventFromUser(eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
