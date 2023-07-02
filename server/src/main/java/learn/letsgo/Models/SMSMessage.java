package learn.letsgo.Models;

import java.util.ArrayList;
import java.util.List;

public class SMSMessage {
    private String recipient;
    private SavedEvent savedEvent;
    private String eventDetailUrl;

    public SMSMessage () {

    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public SavedEvent getSavedEvent() {
        return savedEvent;
    }

    public void setSavedEvent(SavedEvent savedEvent) {
        this.savedEvent = savedEvent;
    }

    public String getEventDetailUrl() {
        return eventDetailUrl;
    }

    public void setEventDetailUrl(String eventDetailUrl) {
        this.eventDetailUrl = eventDetailUrl;
    }
}
