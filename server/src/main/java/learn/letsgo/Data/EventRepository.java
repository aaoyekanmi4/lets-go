package learn.letsgo.Data;

import learn.letsgo.Models.Event;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository {
    Event findById(int eventId);

    @Transactional
    Event create(Event event);

    @Transactional
    boolean update(Event event);

    boolean deleteById(int eventId);

    List<Event> findAllByUserId(int appUserId);
}
