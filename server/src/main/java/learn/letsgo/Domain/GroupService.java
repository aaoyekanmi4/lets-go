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
        Result<Group> result = validateFields(group);
        if (!result.isSuccess()) {
            return result;
        }
        Group newGroup = groupRepository.create(group);
        result.setPayload(newGroup);
        return result;
    }

    public Result<Group> update(Group group) {
        Result<Group> result = validateFields(group);
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

    public Result<Group> addContactToGroup(int contactId, int groupId) {
        Result<Group> result = validateCanBridgeContactToGroup(contactId, groupId, BridgeTableOperation.ADD);
        if (!result.isSuccess()) {
            return result;
        }
        boolean didAddContact = groupRepository.addContactToGroup(contactId, groupId);
        if (!didAddContact) {
            result.addMessage(ResultType.INVALID, "Could not add contact to group");
        }
        return result;
    }

    public Result<Group> batchAddContactsToGroup(List<Integer> contactIds, int groupId) {

        Result<Group> result = new Result<>();
        for (Integer contactId : contactIds) {
            result = validateCanBridgeContactToGroup(contactId, groupId, BridgeTableOperation.ADD);
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

    public Result<Group> batchUpdateContactsInGroup(List<Integer> contactIds, int groupId) {

        Result<Group> result = new Result<>();
        for (Integer contactId : contactIds) {
            result = validateCanBridgeContactToGroup(contactId, groupId, BridgeTableOperation.UPDATE);
            if (!result.isSuccess()) {
                return result;
            }
        }

        boolean didUpdateContacts = groupRepository.batchUpdateContactsInGroup(contactIds, groupId);
        if (!didUpdateContacts) {
            result.addMessage(ResultType.INVALID, "Could not add all contacts to group");
        }
        return result;
    }

    public Result<Group> removeContactFromGroup(int contactId, int groupId) {

        Result<Group> result = validateCanBridgeContactToGroup(contactId, groupId, BridgeTableOperation.REMOVE);
        if (!result.isSuccess()) {
            return result;
        }

        boolean didRemoveContact = groupRepository.removeContactFromGroup(contactId, groupId);
        if (!didRemoveContact) {
            result.addMessage(ResultType.INVALID, "Could not remove contact from group");
        }
        return result;
    }

    private Result<Group> validateFields(Group group) {
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

    private Result<Group> validateCanBridgeContactToGroup(int contactId, int groupId, BridgeTableOperation operation) {
        Result<Group> result = Helpers.validateBothEntitiesExist(contactRepository, contactId, "contact",
                groupRepository, groupId, "group");
        if (!result.isSuccess()) {
            return result;
        }
        if (operation != BridgeTableOperation.UPDATE) {
            List<Contact> contactsList = groupRepository.findById(groupId).getContacts();

            result = Helpers.validateCanPerformBridgeAction(contactId, "contact", "group",
                    contactsList, operation);
        }
            return result;
    }
}

