package learn.letsgo.Data;

import learn.letsgo.Models.Group;

import java.util.List;

public interface GroupRepository {
    Group findById(int groupId);

    Group create(Group group);

    boolean update(Group group);

    boolean deleteById(int groupId);

    List<Group> findAllByUserId(int appUserId);

    List<Group> findAllBySavedEventId(int savedEventId);

    boolean addContactToGroup(int contactId, int groupId);

    boolean removeContactFromGroup(int contactId, int groupId);
}
