package learn.letsgo.Controller;

import learn.letsgo.Domain.Result;
import learn.letsgo.Domain.SavedEventService;
import learn.letsgo.Models.SavedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/event")
public class SavedEventController {
    private final SavedEventService savedEventService;

    public SavedEventController(SavedEventService savedEventService) {
        this.savedEventService = savedEventService;
    }

    @GetMapping("/saved/{savedEventId}")
    public SavedEvent findBySavedId(@PathVariable int savedEventId) {
        return savedEventService.findById(savedEventId);
    }


    @GetMapping("/user/{appUserId}")
    public List<SavedEvent> findAllSaved(@PathVariable int appUserId) {
        return savedEventService.findAll(appUserId);
    }

    @PostMapping("/user/contact/{contactId}/{eventId}/{appUserId}")
    public ResponseEntity<?> addContactToEvent (@PathVariable int contactId,
                                                @PathVariable int eventId,
                                                @PathVariable int appUserId) {
        Result<Void> result = savedEventService.addContactToEvent(contactId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/contact/{contactId}/{eventId}/{appUserId}")
    public ResponseEntity<?> removeContactFromEvent(@PathVariable int contactId,
                                                    @PathVariable int eventId,
                                                    @PathVariable int appUserId) {
        Result<Void> result = savedEventService.removeContactFromEvent(contactId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/user/group/{groupId}/{eventId}/{appUserId}")
    public ResponseEntity<?> addGroupToEvent (@PathVariable int groupId,
                                              @PathVariable int eventId,
                                              @PathVariable int appUserId) {
        Result<Void> result = savedEventService.addGroupToEvent(groupId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/group/{groupId}/{eventId}/{appUserId}")
    public ResponseEntity<?> removeGroupFromEvent(@PathVariable int groupId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int appUserId) {
        Result<Void> result = savedEventService.removeGroupFromEvent(groupId, eventId, appUserId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
