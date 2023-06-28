package learn.letsgo.Data;

import learn.letsgo.Models.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VenueJdbcTemplateRepository implements VenueRepository{
    @Override
    public Venue findById(int venueId) {
        return null;
    }

    @Override
    public Venue create(Venue venue) {
        return null;
    }

    @Override
    public boolean update(Venue venue) {
        return false;
    }

    @Override
    public List<Venue> findAll() {
        return null;
    }
}
