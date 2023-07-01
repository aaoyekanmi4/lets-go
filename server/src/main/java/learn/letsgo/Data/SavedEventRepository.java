package learn.letsgo.Data;

import learn.letsgo.Models.SavedEvent;

public interface SavedEventRepository {
    boolean addEventToUser(int eventId, int appUserId);

    boolean removeEventFromUser(int eventId, int appUserId);

    boolean addContactToEvent(int contactId, int savedEventId);

    boolean removeContactFromEvent(int contactId, int savedEventId);

    boolean addGroupToEvent(int groupId, int savedEventId);

    boolean removeGroupFromEvent(int groupId, int savedEventId);

    Integer getSavedEventId(int eventId, int appUserId);

    SavedEvent findSavedEventForUser(int eventId, int appUserId);
}
