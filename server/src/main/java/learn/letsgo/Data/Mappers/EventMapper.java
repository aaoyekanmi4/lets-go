package learn.letsgo.Data.Mappers;

import learn.letsgo.Models.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int i) throws SQLException {
        LocalDateTime eventDateTime = rs.getTimestamp("event_date").toLocalDateTime();
        VenueMapper venueMapper = new VenueMapper();

        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setCategory(rs.getString("category"));
        event.setEventName(rs.getString("event_name"));
        event.setImageUrl(rs.getString("image_url"));
        event.setDescription(rs.getString("description"));
        event.setDateTime(eventDateTime);
        event.setSource(rs.getString("source"));
        event.setSourceId(rs.getString("source_id"));
        event.setEventLink(rs.getString("event_link"));
        event.setVenue(venueMapper.mapRow(rs, i));
        return event;
    }
}
