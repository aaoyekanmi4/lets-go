package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Data.SavedEventRepository;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Group;
import learn.letsgo.Models.SavedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedEventService {
    private final SavedEventRepository savedEventRepository;
    private final ContactRepository contactRepository;
    private final GroupRepository groupRepository;

    public SavedEventService(SavedEventRepository savedEventRepository, ContactRepository contactRepository, GroupRepository groupRepository) {
        this.savedEventRepository = savedEventRepository;
        this.contactRepository = contactRepository;
        this.groupRepository = groupRepository;
    }

    public List<SavedEvent> findAll(int appUserId) {
        return savedEventRepository.findAll(appUserId);
    }

    public SavedEvent findById(int savedEventId) {
        return savedEventRepository.findById(savedEventId);
    }

    public Result<Contact> addContactToEvent(int contactId, int savedEventId) {
        Result<Contact> result = validateCanBridgeContactToSavedEvent(contactId, savedEventId, BridgeTableOperation.ADD);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didAddContactToEvent = savedEventRepository.addContactToEvent(contactId, savedEventId);
        if (!didAddContactToEvent) {
            result.addMessage(ResultType.INVALID, "Could not add contact to event");
        }
        return result;
    }

    public Result<Contact> removeContactFromEvent(int contactId, int savedEventId) {
        Result<Contact> result = validateCanBridgeContactToSavedEvent(contactId, savedEventId,BridgeTableOperation.REMOVE);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didRemoveContactFromEvent = savedEventRepository.removeContactFromEvent(contactId, savedEventId);
        if (!didRemoveContactFromEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove contact from event");
        }
        return result;
    }

    public Result<Group> addGroupToEvent(int groupId, int savedEventId) {
        Result<Group> result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, BridgeTableOperation.ADD);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didAddGroupToEvent = savedEventRepository.addGroupToEvent(groupId, savedEventId);
        if (!didAddGroupToEvent) {
            result.addMessage(ResultType.INVALID, "Could not add group to event");
        }
        return result;
    }

    public Result<Group> removeGroupFromEvent(int groupId, int savedEventId) {
        Result<Group> result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, BridgeTableOperation.REMOVE);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didRemoveContactFromEvent = savedEventRepository.removeGroupFromEvent(groupId, savedEventId);
        if (!didRemoveContactFromEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove group from event");
        }
        return result;
    }

    public Result<Contact> batchAddContactsToSavedEvent(List<Integer> contactIds, int savedEventId) {

        Result<Contact> result = new Result<>();
        for (Integer contactId : contactIds) {
            result = validateCanBridgeContactToSavedEvent(contactId, savedEventId, BridgeTableOperation.ADD);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didAddContacts = savedEventRepository.batchAddContactsToEvent(contactIds, savedEventId);
        if (!didAddContacts) {
            result.addMessage(ResultType.INVALID, "Could not add all contacts to savedEvent");
        }
        return result;
    }

    public Result<Contact> batchUpdateContactsInSavedEvent(List<Integer> contactIds, int savedEventId) {

        Result<Contact> result = new Result<>();
        for (Integer contactId : contactIds) {
            result = validateCanBridgeContactToSavedEvent(contactId, savedEventId, BridgeTableOperation.UPDATE);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didUpdateContacts = savedEventRepository.batchUpdateContactsInEvent(contactIds, savedEventId);
        if (!didUpdateContacts) {
            result.addMessage(ResultType.INVALID, "Could not add all contacts to saved event");
        }
        return result;
    }

    public Result<Group> batchAddGroupsToSavedEvent(List<Integer> groupIds, int savedEventId) {

        Result<Group> result = new Result<>();
        for (Integer groupId : groupIds) {
            result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, BridgeTableOperation.ADD);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didAddGroups = savedEventRepository.batchAddGroupsToEvent(groupIds, savedEventId);
        if (!didAddGroups) {
            result.addMessage(ResultType.INVALID, "Could not add all groups to savedEvent");
        }
        return result;
    }

    public Result<Group> batchUpdateGroupsInSavedEvent(List<Integer> groupIds, int savedEventId) {

        Result<Group> result = new Result<>();
        for (Integer groupId : groupIds) {
            result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, BridgeTableOperation.UPDATE);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didUpdateGroups = savedEventRepository.batchUpdateGroupsInEvent(groupIds, savedEventId);
        if (!didUpdateGroups) {
            result.addMessage(ResultType.INVALID, "Could not add all groups to saved event");
        }
        return result;
    }

    private Result<Contact> validateCanBridgeContactToSavedEvent(int contactId, int savedEventId, BridgeTableOperation operation) {

        return Helpers.validateCanPerformBridgeAction(savedEventRepository, savedEventId, "saved event", "getContacts",
                contactRepository, contactId, "contact",
                operation);
    }

    private Result<Group> validateCanBridgeGroupToSavedEvent(int groupId, int savedEventId, BridgeTableOperation operation) {

        return Helpers.validateCanPerformBridgeAction(savedEventRepository, savedEventId, "saved event", "getGroups",
                groupRepository, groupId, "group",
                operation);
    }

}
