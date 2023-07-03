package learn.letsgo.Models;

import java.time.LocalDateTime;

public class EventPost {
    private int postId;
    private int eventId;
    private int appUserId;
    private String author;
    private LocalDateTime postDate;
    private String postBody;
    private int likes;

    public EventPost(){

    }

    public EventPost(int eventId, int appUserId,  String author, LocalDateTime postDate, String postBody, int likes) {
        this.eventId = eventId;
        this.appUserId = appUserId;
        this.author = author;
        this.postDate = postDate;
        this.postBody = postBody;
        this.likes = likes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }
}
