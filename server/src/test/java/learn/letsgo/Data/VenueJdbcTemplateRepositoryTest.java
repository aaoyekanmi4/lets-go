package learn.letsgo.Data;

import learn.letsgo.Models.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VenueJdbcTemplateRepositoryTest {

    @Autowired
    VenueJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllVenues() {
        List<Venue> venues = repository.findAll();
        assertNotNull(venues);
        assertTrue(venues.size() > 0);
    }

    @Test
    void shouldFindById() {
        Venue actual = repository.findById(1);
        assertNotNull(actual);
        assertEquals(1, actual.getVenueId());
        assertEquals(1, actual.getEvents().size());
    }

    @Test
    void shouldCreateVenue() {
        Venue actual = repository.create(makeVenue());
        assertNotNull(actual);
        assertEquals("New York", actual.getCity());
    }

    @Test
    void shouldUpdateVenue() {
        Venue venueToUpdate = repository.findById(2);
        venueToUpdate.setCity("New Orleans");
        boolean actual = repository.update(venueToUpdate);
        assertTrue(actual);
    }

    Venue makeVenue () {
        Venue venue = new Venue("Yankee Stadium", "1111 First St.",
                "New York", "NY", "United States", 77282);
        return venue;
    }
}