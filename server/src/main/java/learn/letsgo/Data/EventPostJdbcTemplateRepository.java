package learn.letsgo.Data;

import learn.letsgo.Models.EventPost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventPostJdbcTemplateRepository implements EventPostRepository{
    @Override
    public EventPost findById(int postId) {
        return null;
    }

    @Override
    public EventPost create(EventPost eventPost) {
        return null;
    }

    @Override
    public boolean update(EventPost eventPost) {
        return false;
    }

    @Override
    public boolean deleteById(int postId) {
        return false;
    }

    @Override
    public List<EventPost> findAllByEventId(int eventId) {
        return null;
    }
}
