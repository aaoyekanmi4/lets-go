package learn.letsgo.Models;

public class EventPost {
    private int postId;
    private Event event;
    private AppUser appUser;
    private String postBody;
    private int likes;

    public EventPost(){

    }

    public EventPost(Event event, AppUser appUser, String postBody, int likes) {
        this.event = event;
        this.appUser = appUser;
        this.postBody = postBody;
        this.likes = likes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
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
