package learn.letsgo.Controller;

import learn.letsgo.Domain.Result;
import learn.letsgo.Domain.SavedEventService;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
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

    @PostMapping("/user/contact/{contactId}/{savedEventId}")
    public ResponseEntity<?> addContactToEvent (@PathVariable int contactId,
                                                @PathVariable int savedEventId) {
        Result<Contact> result = savedEventService.addContactToEvent(contactId, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/contact/{contactId}/{savedEventId}")
    public ResponseEntity<?> removeContactFromEvent(@PathVariable int contactId,
                                                    @PathVariable int savedEventId) {
        Result<Contact> result = savedEventService.removeContactFromEvent(contactId, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/user/group/{groupId}/{savedEventId}")
    public ResponseEntity<?> addGroupToEvent (@PathVariable int groupId,
                                              @PathVariable int savedEventId) {
        Result<Group> result = savedEventService.addGroupToEvent(groupId, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/user/group/{groupId}/{savedEventId}")
    public ResponseEntity<?> removeGroupFromEvent(@PathVariable int groupId,
                                                  @PathVariable int savedEventId) {
        Result<Group> result = savedEventService.removeGroupFromEvent(groupId, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/batch/contacts/{savedEventId}")
    public ResponseEntity<?> batchAddContactsToSavedEvent (@RequestBody List<Integer> contactIds, @PathVariable int savedEventId) {
        Result<Contact> result = savedEventService.batchAddContactsToSavedEvent(contactIds, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/batch/contacts/{savedEventId}")
    public ResponseEntity<?> batchUpdateContactsInSavedEvent(@RequestBody List<Integer> contactIds, @PathVariable int savedEventId) {
        Result<Contact> result = savedEventService.batchUpdateContactsInSavedEvent(contactIds, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/batch/groups/{savedEventId}")
    public ResponseEntity<?> batchAddGroupsToSavedEvent (@RequestBody List<Integer> groupIds, @PathVariable int savedEventId) {
        Result<Group> result = savedEventService.batchAddGroupsToSavedEvent(groupIds, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/batch/groups/{savedEventId}")
    public ResponseEntity<?> batchUpdateGroupInSavedEvent(@RequestBody List<Integer> groupIds, @PathVariable int savedEventId) {
        Result<Group> result = savedEventService.batchUpdateGroupsInSavedEvent(groupIds, savedEventId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
