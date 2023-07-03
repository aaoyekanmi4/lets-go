package learn.letsgo.Controller.API;

import learn.letsgo.Domain.API.TicketMasterEventService;
import learn.letsgo.Models.API.TicketMasterEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TicketMasterEventController {
    private final TicketMasterEventService eventService;

    public TicketMasterEventController(TicketMasterEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/ticketmaster/events")
    public List<TicketMasterEvent> getEvents(@RequestParam(name = "postalCode", required = false) String postalCode) {
        return eventService.getEvents(postalCode);
    }
}
