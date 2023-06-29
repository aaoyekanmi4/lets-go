package learn.letsgo.Models;

public class EventPost {
    private int postId;
    private int eventId;
    private int appUserId;
    private String postBody;
    private int likes;

    public EventPost(){

    }

    public EventPost(int eventId, int appUserID, String postBody, int likes) {
        this.eventId = eventId;
        this.appUserId = appUserId;
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
}
