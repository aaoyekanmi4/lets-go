package learn.letsgo.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private int eventId;
    private String category;
    private String imageUrl;
    private String description;
    private LocalDateTime dateTime;
    private String source;
    private String sourceId;
    private String eventLink;
    private Venue venue;

    private List<AppUser> appUsers;

    private List<EventPost> eventPosts;

    public Event(){

    }

    public Event(String category, String imageUrl, String description, LocalDateTime dateTime, String source, String sourceId, String eventLink, Venue venue) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.dateTime = dateTime;
        this.source = source;
        this.sourceId = sourceId;
        this.eventLink = eventLink;
        this.venue = venue;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
