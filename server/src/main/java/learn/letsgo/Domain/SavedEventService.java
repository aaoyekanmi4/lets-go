package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Data.SavedEventRepository;
import learn.letsgo.Models.Contact;
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

    public Result<Void> addContactToEvent(int contactId, int savedEventId) {
        Result<Void> result = validateCanBridgeContactToSavedEvent(contactId, savedEventId, true);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didAddContactToEvent = savedEventRepository.addContactToEvent(contactId, savedEventId);
        if (!didAddContactToEvent) {
            result.addMessage(ResultType.INVALID, "Could not add contact to event");
        }
        return result;
    }

    public Result<Void> removeContactFromEvent(int contactId, int savedEventId) {
        Result<Void> result = validateCanBridgeContactToSavedEvent(contactId, savedEventId, false);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didRemoveContactFromEvent = savedEventRepository.removeContactFromEvent(contactId, savedEventId);
        if (!didRemoveContactFromEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove contact from event");
        }
        return result;
    }

    public Result<Void> addGroupToEvent(int groupId, int savedEventId) {
        Result<Void> result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, true);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didAddGroupToEvent = savedEventRepository.addGroupToEvent(groupId, savedEventId);
        if (!didAddGroupToEvent) {
            result.addMessage(ResultType.INVALID, "Could not add group to event");
        }
        return result;
    }

    public Result<Void> removeGroupFromEvent(int groupId, int savedEventId) {
        Result<Void> result = validateCanBridgeGroupToSavedEvent(groupId, savedEventId, false);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didRemoveContactFromEvent = savedEventRepository.removeGroupFromEvent(groupId, savedEventId);
        if (!didRemoveContactFromEvent) {
            result.addMessage(ResultType.INVALID, "Could not remove group from event");
        }
        return result;
    }

    //TODO REFACTOR THESE INTO ONE METHOD
    private Result<Void> validateCanBridgeGroupToSavedEvent(int groupId, int savedEventId, boolean isAdding) {

        Result<Void> result = new Result<>();

        Group group = groupRepository.findById(groupId);

        if (group == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find group with groupId: %s", groupId));
        }

        SavedEvent savedEvent = savedEventRepository.findById(savedEventId);

        if (savedEvent == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find savedEvent with savedEventId: %s", savedEventId));
        } else {
            boolean alreadyHasGroup = savedEvent.getGroups()
                    .stream().map(currGroup -> currGroup.getGroupId()).anyMatch(id -> id == groupId);
            System.out.println(alreadyHasGroup);
            if (alreadyHasGroup && isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Group id %s already in this saved event", groupId));
            } else if (!alreadyHasGroup && !isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Group with id %s not in this saved event for removal", groupId));
            }
        }
        return result;
    }

    private Result<Void> validateCanBridgeContactToSavedEvent(int contactId, int savedEventId, boolean isAdding) {

        Result<Void> result = new Result<>();

        Contact contact = contactRepository.findById(contactId);

        if (contact == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find contact with contactId: %s", contactId));
        }

        SavedEvent savedEvent = savedEventRepository.findById(savedEventId);

        if (savedEvent == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find savedEvent with savedEventId: %s", savedEventId));
        } else {
            boolean alreadyHasContact = savedEvent.getContacts()
                    .stream().map(currContact -> currContact.getContactId()).anyMatch(id -> id == contactId);

            if (alreadyHasContact && isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Contact id %s already in this saved event", contactId));
            } else if (!alreadyHasContact && !isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Contact with id %s not in this saved event for removal", contactId));
            }
        }
        return result;
    }

}
