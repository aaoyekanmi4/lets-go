package learn.letsgo.Data;

import learn.letsgo.Models.Venue;

import java.util.List;

public interface VenueRepository {
    Venue findById(int venueId);

    Venue create(Venue venue);

    boolean update(Venue venue);

    List<Venue> findAll();
}
