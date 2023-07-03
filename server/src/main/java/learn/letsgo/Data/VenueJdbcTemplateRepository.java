package learn.letsgo.Data;

import learn.letsgo.Data.Mappers.EventMapper;
import learn.letsgo.Data.Mappers.VenueMapper;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VenueJdbcTemplateRepository implements VenueRepository {

    private final JdbcTemplate jdbcTemplate;

    public VenueJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Venue findById(int venueId) {
        final String sql = "select venue_id, venue_name, address, city, state, country, zipcode "
                + "from venue "
                + "where venue_id = ?;";

        Venue result = jdbcTemplate.query(sql, new VenueMapper(), venueId).stream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            addEvents(result);
        }

        return result;
    }

    @Override
    public Venue create(Venue venue) {
        final String sql = "insert into venue (venue_name, address, city, state, country, zipcode)"
                + "values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, venue.getVenueName());
            ps.setString(2, venue.getAddress());
            ps.setString(3, venue.getCity());
            ps.setString(4, venue.getState());
            ps.setString(5, venue.getCountry());
            ps.setInt(6, venue.getZipCode());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        venue.setVenueId(keyHolder.getKey().intValue());
        return venue;
    }

    @Override
    public boolean update(Venue venue) {

        final String sql = "update venue set "
                + "venue_name = ?, "
                + "address = ?, "
                + "city = ?, "
                + "state = ?, "
                + "country = ?, "
                + "zipcode = ? "
                + "where venue_id = ?;";

        return jdbcTemplate.update(sql,
                venue.getVenueName(),
                venue.getAddress(),
                venue.getCity(),
                venue.getState(),
                venue.getCountry(),
                venue.getZipCode(),
                venue.getVenueId()) > 0;
    }

    @Override
    public List<Venue> findAll() {
        final String sql = "select venue_id, venue_name, address, city, state, country, zipcode "
                + "from venue limit 100;";
        return jdbcTemplate.query(sql, new VenueMapper());
    }

    private void addEvents(Venue venue) {
        final String sql = "select e.event_id, e.category, e.event_name, e.image_url, e.description, e.event_date, "
                + "e.source, e.source_id, e.event_link, e.venue_id, "
                + "v.venue_name, v.address, v.city, v.state, v.country, v.zipcode "
                + "from event e "
                + "inner join venue v on e.venue_id = v.venue_id "
                + "where e.venue_id = ?";

        List<Event> events = jdbcTemplate.query(sql, new EventMapper(), venue.getVenueId());
        venue.setEvents(events);
    }
}
