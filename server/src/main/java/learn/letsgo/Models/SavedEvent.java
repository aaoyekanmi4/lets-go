package learn.letsgo.Models;

import java.util.ArrayList;
import java.util.List;

public class SavedEvent extends Identifiable {
    private int savedEventId;
    private int appUserId;
    private Event event;

    private List<Group> groups = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();

    public SavedEvent() {
    }

    public SavedEvent(int appUserId, Event event) {
        this.appUserId = appUserId;
        this.event = event;
    }

    public int getSavedEventId() {
        return savedEventId;
    }

    public int getId() { return savedEventId; };

    public void setSavedEventId(int savedEventId) {
        this.savedEventId = savedEventId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Group> getGroups() {
        return new ArrayList<>(groups);
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Contact> getContacts() {
        return new ArrayList<> (contacts);
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "SavedEvent{" +
                "savedEventId=" + savedEventId +
                ", appUserId=" + appUserId +
                ", event=" + event +
                ", groups=" + groups +
                ", contacts=" + contacts +
                '}';
    }
}
