package learn.letsgo.Data.Mappers;

import learn.letsgo.Models.Venue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VenueMapper implements RowMapper<Venue> {

    @Override
    public Venue mapRow(ResultSet rs, int i) throws SQLException {
        Venue venue = new Venue();
        venue.setVenueId(rs.getInt("venue_id"));
        venue.setVenueName(rs.getString("venue_name"));
        venue.setAddress(rs.getString("address"));
        venue.setCity(rs.getString("city"));
        venue.setState(rs.getString("state"));
        venue.setCountry(rs.getString("country"));
        venue.setZipCode(rs.getInt("zipcode"));
        return venue;
    }
}
