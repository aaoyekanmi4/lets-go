package learn.letsgo.Models;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage {

    private String subject;
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

    public String getClientEventDetailURL() {
        return eventDetailUrl;
    }

    public void setClientEventDetailURL(String eventDetailUrl) {
        this.eventDetailUrl = eventDetailUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getRecipients() {
        return new ArrayList<>(recipients);
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
