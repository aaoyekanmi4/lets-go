package learn.letsgo.Data;

import learn.letsgo.Models.SavedEvent;

import java.util.List;

public interface SavedEventRepository extends BridgeTableRepository<SavedEvent> {
    boolean addEventToUser(int eventId, int appUserId);

    boolean removeEventFromUser(int eventId, int appUserId);

    boolean addContactToEvent(int contactId, int savedEventId);

    boolean removeContactFromEvent(int contactId, int savedEventId);

    boolean addGroupToEvent(int groupId, int savedEventId);

    boolean removeGroupFromEvent(int groupId, int savedEventId);

    Integer getSavedEventId(int eventId, int appUserId);

    SavedEvent findSavedEventForUser(int eventId, int appUserId);

    List<SavedEvent> findAll(int appUserId);

    SavedEvent findById(int savedEventId);

    boolean batchAddContactsToEvent(List<Integer> contactIds, int savedEventId);

    boolean batchUpdateContactsInEvent(List<Integer> contactIds, int savedEventId);

    boolean batchAddGroupsToEvent(List<Integer> groupIds, int savedEventId);

    boolean batchUpdateGroupsInEvent(List<Integer> groupIds, int savedEventId);
}
