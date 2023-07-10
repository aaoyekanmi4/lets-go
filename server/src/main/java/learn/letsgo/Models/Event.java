package learn.letsgo.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event extends Identifiable {
    private int eventId;
    private String eventName;
    private String category;
    private String imageUrl;
    private String description;
    private LocalDateTime dateTime;
    private String source;
    private String sourceId;
    private String eventLink;
    private Venue venue;

    private List<EventPost> eventPosts = new ArrayList<>();

    public Event(){

    }

    public Event( String category, String eventName, String imageUrl,
                 String description, LocalDateTime dateTime, String source, String sourceId, String eventLink, Venue venue) {
        this.category = category;
        this.eventName = eventName;
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

    public int getId() { return eventId; };

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public List<EventPost> getEventPosts() {
        return new ArrayList<>(eventPosts);
    }

    public void setEventPosts(List<EventPost> eventPosts) {
        this.eventPosts = eventPosts;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", eventLink='" + eventLink + '\'' +
                ", venue=" + venue +
                ", eventPosts=" + eventPosts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId && Objects.equals(eventName, event.eventName) && Objects.equals(category, event.category) && Objects.equals(imageUrl, event.imageUrl) && Objects.equals(description, event.description) && Objects.equals(dateTime, event.dateTime) && Objects.equals(source, event.source) && Objects.equals(sourceId, event.sourceId) && Objects.equals(eventLink, event.eventLink) && Objects.equals(venue, event.venue) && Objects.equals(eventPosts, event.eventPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, category, imageUrl, description, dateTime, source, sourceId, eventLink, venue, eventPosts);
    }
}
