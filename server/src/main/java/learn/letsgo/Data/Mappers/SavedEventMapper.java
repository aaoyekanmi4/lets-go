package learn.letsgo.Data.Mappers;


import learn.letsgo.Data.EventJdbcTemplateRepository;
import learn.letsgo.Data.EventRepository;
import learn.letsgo.Models.SavedEvent;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SavedEventMapper implements RowMapper<SavedEvent> {
    private final EventRepository eventRepository;

    public SavedEventMapper(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public SavedEvent mapRow(ResultSet rs, int i) throws SQLException {

        EventMapper eventMapper = new EventMapper();

        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(rs.getInt("saved_event_id"));
        savedEvent.setAppUserId(rs.getInt("app_user_id"));
        savedEvent.setEvent(eventRepository.findById(rs.getInt("event_id")));
        return savedEvent;
    }
}