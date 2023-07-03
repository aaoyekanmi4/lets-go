package learn.letsgo.Data;

import learn.letsgo.Models.EventPost;

import java.util.List;

public interface EventPostRepository {
    EventPost findById(int postId);

    EventPost create(EventPost eventPost);

    boolean update(EventPost eventPost);

    boolean deleteById(int postId);

    List<EventPost> findAllByEventId(int eventId);
}
