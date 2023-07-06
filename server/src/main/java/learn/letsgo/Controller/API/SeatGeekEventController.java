package learn.letsgo.Controller.API;

import learn.letsgo.Domain.API.SeatGeekEventService;
import learn.letsgo.Models.API.SeatGeekEvent;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SeatGeekEventController {

    private final SeatGeekEventService eventService;

    public SeatGeekEventController(SeatGeekEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/seatgeek/events")
    public List<SeatGeekEvent> getEvents(@RequestParam(name = "postalCode", required = false) String postalCode) {
        return eventService.getEvents(postalCode);
    }

    @GetMapping("/seatgeek/events/id/{id}")
    public SeatGeekEvent getEventById(@PathVariable String id) {
        return eventService.getEventById(id);
    }
}
