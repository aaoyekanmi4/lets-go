package learn.letsgo.Controller;

import learn.letsgo.Domain.EventService;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.SavedEvent;
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
}
