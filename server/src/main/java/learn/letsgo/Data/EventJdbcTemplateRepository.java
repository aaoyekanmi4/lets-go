package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.AppUserMapper;
import learn.letsgo.Data.Mappers.EventMapper;
import learn.letsgo.Data.Mappers.EventPostMapper;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.EventPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class EventJdbcTemplateRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAll() {
        final String sql = "select e.event_id, e.category, e.event_name, e.image_url, e.description, e.event_date, "
                + "e.source, e.source_id, e.event_link, e.venue_id, "
                + "v.venue_name, v.address, v.city, v.state, v.country, v.zipcode "
                + "from event e "
                + "inner join venue v on e.venue_id = v.venue_id;";
        return jdbcTemplate.query(sql, new EventMapper());
    }

    @Override
    public List<Event> findAllByUserId(int appUserId) {
        final String sql = "select e.event_id, e.category, e.event_name, e.image_url, e.description, e.event_date, "
                + "e.source, e.source_id, e.event_link, e.venue_id, "
                + "v.venue_name, v.address, v.city, v.state, v.country, v.zipcode "
                + "from event e "
                + "inner join venue v on e.venue_id = v.venue_id "
                + "inner join saved_event se on se.event_id = e.event_id "
                + "where se.app_user_id = ?;";
        return jdbcTemplate.query(sql, new EventMapper(), appUserId);
    }

    @Override
    public Event findById(int eventId) {
        final String sql = "select e.event_id, e.category, e.event_name, e.image_url, e.description, e.event_date, "
                + "e.source, e.source_id, e.event_link, e.venue_id, "
                + "v.venue_name, v.address, v.city, v.state, v.country, v.zipcode "
                + "from event e "
                + "inner join venue v on e.venue_id = v.venue_id "
                + "where e.event_id = ?;";
        Event result = jdbcTemplate.query(sql, new EventMapper(), eventId).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            addEventPosts(result);
        }
        return result;
    }

    @Override
    @Transactional
    public Event create(Event event) {
        final String sql = "insert into event (category, event_name, image_url, "
                + "description, event_date, source, source_id, event_link, venue_id)"
                + "values (?,?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getCategory());
            ps.setString(2, event.getEventName());
            ps.setString(3, event.getImageUrl());
            ps.setString(4, event.getDescription());
            ps.setTimestamp(5, Timestamp.valueOf(event.getDateTime()));
            ps.setString(6, event.getSource());
            ps.setString(7, event.getSourceId());
            ps.setString(8, event.getEventLink());
            ps.setInt(9, event.getVenue().getVenueId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        event.setEventId(keyHolder.getKey().intValue());
        return event;
    }

    @Override
    public boolean update(Event event) {

        final String sql = "update event set "
                + "category = ?, "
                + "event_name = ?, "
                + "image_url = ?, "
                + "description = ?, "
                + "event_date = ?, "
                + "source = ?, "
                + "source_id = ?, "
                + "event_link = ?, "
                + "venue_id = ? "
                + "where event_id = ?;";

        return jdbcTemplate.update(sql,
                event.getCategory(),
                event.getEventName(),
                event.getImageUrl(),
                event.getDescription(),
                event.getDateTime(),
                event.getSource(),
                event.getSourceId(),
                event.getEventLink(),
                event.getVenue().getVenueId(),
                event.getEventId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int eventId) {
        jdbcTemplate.update("delete from saved_event where event_id=?;",eventId);
        jdbcTemplate.update("delete from event_post where event_id=?;",eventId);
        return jdbcTemplate.update("delete from event where event_id=?;",eventId) > 0;
    }

    private void addEventPosts(Event event) {
        final String sql = "select ep.event_post_id, ep.event_id, ep.app_user_id, "
                + "ep.author, ep.post_date, ep.post_body, ep.likes "
                + "from event_post ep "
                + "where ep.event_id = ?";

        List<EventPost> posts = jdbcTemplate.query(sql, new EventPostMapper(), event.getEventId());
        event.setEventPosts(posts);
    }
}
