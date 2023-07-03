package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    private final ContactRepository contactRepository;

    public GroupService(GroupRepository groupRepository, ContactRepository contactRepository) {
        this.groupRepository = groupRepository;
        this.contactRepository = contactRepository;
    }

    public List<Group> findAllByUserId(int appUserId) {
        return groupRepository.findAllByUserId(appUserId);
    }


    public List<Group> findAllBySavedEventId(int savedEventId) {
        return groupRepository.findAllBySavedEventId(savedEventId);
    }

    public Group findById(int groupId) {
        return groupRepository.findById(groupId);
    }

    public Result<Group> create(Group group) {
        Result<Group> result = validate(group);
        if (!result.isSuccess()) {
            return result;
        }
        Group newGroup = groupRepository.create(group);
        result.setPayload(newGroup);
        return result;
    }

    public Result<Group> update(Group group) {
        Result<Group> result = validate(group);
        if (!result.isSuccess()) {
            return result;
        }

        if (group.getGroupId() <= 0) {
            result.addMessage(ResultType.INVALID, "groupId must be set for `update` operation");
            return result;
        }

        boolean groupDidUpdate = groupRepository.update(group);

        if (!groupDidUpdate) {
            String msg = String.format("groupId: %s, not found", group.getGroupId());
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    public boolean deleteById(int groupId) {
        return groupRepository.deleteById(groupId);
    }

    public Result<Void> addContactToGroup(int contactId, int groupId) {
        Result<Void> result = validateCanPerformBridgeAction(contactId, groupId, true);
        if (!result.isSuccess()) {
            return result;
        }

        boolean didAddContact = groupRepository.addContactToGroup(contactId, groupId);
        if (!didAddContact) {
            result.addMessage(ResultType.INVALID, "Could not add contact to group");
        }
        return result;
    }

    public Result<Void> batchAddContactsToGroup(List<Integer> contactIds, int groupId) {

        Result<Void> result = new Result<>();
        for (Integer num : contactIds) {
            result = validateCanPerformBridgeAction(num, groupId, true);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didAddContacts = groupRepository.batchAddContactsToGroup(contactIds, groupId);
        if (!didAddContacts) {
            result.addMessage(ResultType.INVALID, "Could not add all contacts to group");
        }
        return result;
    }

    public Result<Void> removeContactFromGroup(int contactId, int groupId) {
        Result<Void> result = validateCanPerformBridgeAction(contactId, groupId, false);
        if (!result.isSuccess()) {
            return result;
        }

        boolean didRemoveContact = groupRepository.removeContactFromGroup(contactId, groupId);
        if (!didRemoveContact) {
            result.addMessage(ResultType.INVALID, "Could not remove contact from group");
        }
        return result;
    }

    private Result<Group> validate(Group group) {
        Result<Group> result = new Result<>();
        if (group == null) {
            result.addMessage(ResultType.INVALID, "Group cannot be null");
            return result;
        }

        if (Helpers.isNullOrBlank(group.getName())) {
            result.addMessage(ResultType.INVALID, "Name is required");
        }
        return result;
    }

    private Result<Void> validateCanPerformBridgeAction(int contactId, int groupId, boolean isAdding) {

        Result<Void> result = new Result<>();

        Contact contactToAdd = contactRepository.findById(contactId);

        if (contactToAdd == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find contact with contactId: %s", contactId));
        }

        Group groupToAddContact = groupRepository.findById(groupId);

        if (groupToAddContact == null) {
            result.addMessage(ResultType.NOT_FOUND,
                    String.format("Could not find group with groupId: %s", groupId));
        } else {
            //check if contactId present in group's contacts
            boolean containsContact = groupToAddContact.getContacts()
                    .stream().map(contact -> contact.getContactId()).anyMatch(id -> id == contactId);

            if (containsContact && isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Contact with id %s already in group", contactId));
            } else if (!containsContact && !isAdding) {
                result.addMessage(ResultType.INVALID,
                        String.format("Contact with id %s not in group so cannot be removed", contactId));
            }
        }
        return result;
    }
}
