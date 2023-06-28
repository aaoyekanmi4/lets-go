package learn.letsgo.Data;

import learn.letsgo.Models.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventJdbcTemplateRepository implements EventRepository{
    @Override
    public Event findById(int eventId) {
        return null;
    }

    @Override
    public Event create(Event event) {
        return null;
    }

    @Override
    public boolean update(Event event) {
        return false;
    }

    @Override
    public boolean deleteById(Event event) {
        return false;
    }

    @Override
    public List<Event> findAllByUserId(int appUserId) {
        return null;
    }
}
