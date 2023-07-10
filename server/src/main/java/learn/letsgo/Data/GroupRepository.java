package learn.letsgo.Data;

import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;

import java.util.List;

public interface GroupRepository extends BridgeTableRepository<Group> {
    Group findById(int groupId);

    Group create(Group group);

    boolean update(Group group);

    boolean deleteById(int groupId);

    List<Group> findAllByUserId(int appUserId);

    List<Group> findAllBySavedEventId(int savedEventId);

    boolean addContactToGroup(int contactId, int groupId);

    boolean batchAddContactsToGroup(List<Integer> contactIds, int groupId);

    boolean removeContactFromGroup(int contactId, int groupId);

    boolean batchUpdateContactsInGroup(List<Integer> contactIds, int groupId);
}
