package learn.letsgo.Models;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage {

    private List<String> recipients = new ArrayList<>();
    private SavedEvent savedEvent;
    private String eventDetailUrl;

    public EmailMessage() {
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

    public void setEventDetailURL(String eventDetailUrl) {
        this.eventDetailUrl = eventDetailUrl;
    }

    public List<String> getRecipients() {
        return new ArrayList<>(recipients);
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
