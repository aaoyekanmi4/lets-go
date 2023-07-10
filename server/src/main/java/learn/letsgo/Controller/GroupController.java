package learn.letsgo.Controller;

import learn.letsgo.Domain.GroupService;
import learn.letsgo.Domain.Result;

import learn.letsgo.Models.Group;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/user/{appUserId}")
    public List<Group> findAllGroupsForUser(@PathVariable int appUserId) {
        return groupService.findAllByUserId(appUserId);
    }

    @GetMapping("/event/{savedEventId}")
    public List<Group> findAllGroupsForSavedEvent(@PathVariable int savedEventId) {
        return groupService.findAllBySavedEventId(savedEventId);
    }

    @GetMapping("/{groupId}")
    public Group findGroupById(@PathVariable int groupId) {
        return groupService.findById(groupId);
    }

    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody Group group) {
        Result<Group> result = groupService.create(group);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Object> updateGroup(@RequestBody Group group, @PathVariable int groupId){
        if (groupId != group.getGroupId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Group> result = groupService.update(group);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable int groupId) {
        if(groupService.deleteById(groupId)) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{contactId}/{groupId}")
    public ResponseEntity<?> addContactToGroup (@PathVariable int contactId, @PathVariable int groupId) {
        Result<Group> result = groupService.addContactToGroup(contactId, groupId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<?> batchAddContactsToGroup (@RequestBody List<Integer> contactIds, @PathVariable int groupId) {
        Result<Group> result = groupService.batchAddContactsToGroup(contactIds, groupId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/batch/{groupId}")
    public ResponseEntity<?> batchUpdateContactsInGroup (@RequestBody List<Integer> contactIds, @PathVariable int groupId) {
        Result<Group> result = groupService.batchUpdateContactsInGroup(contactIds, groupId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{contactId}/{groupId}")
    public ResponseEntity<?> removeContactFromGroup(@PathVariable int contactId,
                                                  @PathVariable int groupId) {
        Result<Group> result = groupService.removeContactFromGroup(contactId, groupId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
