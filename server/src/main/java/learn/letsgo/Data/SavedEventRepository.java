package learn.letsgo.Data;

import learn.letsgo.Models.SavedEvent;

import java.util.List;

public interface SavedEventRepository {
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
}
