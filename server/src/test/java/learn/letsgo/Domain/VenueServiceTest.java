package learn.letsgo.Domain;

import learn.letsgo.Data.VenueRepository;
import learn.letsgo.Models.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class VenueServiceTest {
    @Autowired
    VenueService venueService;

    @MockBean
    VenueRepository venueRepository;

    @Test
    void shouldCreateVenue () {
        Venue venue = new Venue("The Rock Bar", "1002 2nd Street", "New York", "NY",
                "United States", 123456);
        Venue venueOut = venue;
        venueOut.setVenueId(4);
        when(venueRepository.create(venue)).thenReturn(venueOut);
        Result<Venue> actual = venueService.upsert(venue);
        assertEquals(venueOut, actual.getPayload());
    }

    @Test
    void shouldReturnExistingVenue() {
        Venue venue1 = new Venue("The Rock Bar", "1002 2nd Street", "New York", "NY",
                "United States", 123456);
        venue1.setVenueId(1);
        Venue venue2 = new Venue("The Flamingo", "301 Las Vegas Blvd", "Las Vegas", "NV",
                "United States", 123456);
        venue2.setVenueId(2);
        List<Venue> existingVenues = List.of(venue1, venue1);
        when(venueRepository.findAll()).thenReturn(existingVenues);
        Venue newVenue = new Venue("The Rock Bar", "1002 2nd Street", "New York", "NY",
                "United States", 123456);
        newVenue.setVenueId(0);
        Result<Venue> actual = venueService.upsert(newVenue);
        assertEquals(venue1, actual.getPayload());
    }

    @Test
    void shouldNotUpsertWhenInvalidName() {
        Venue venue = new Venue("", "1002 2nd Street", "New York", "NY",
                "United States", 123456);
        Result<Venue> actual = venueService.upsert(venue);
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotUpsertWhenInvalidAddress() {
        Venue venue = new Venue("The Karaoke Bar", "", "New York", "NY",
                "United States", 123456);
        Result<Venue> actual = venueService.upsert(venue);
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldUpdateVenue() {
        Venue venue = new Venue("The Karaoke Bar", "333 NW Fwy", "New York", "NY",
                "United States", 123456);
        venue.setVenueId(6);
        when(venueRepository.update(venue)).thenReturn(true);
        Result<Venue> actual = venueService.update(venue);
        assertEquals(ResultType.SUCCESS, actual.getStatus());
    }

    @Test
    void shouldNotUpdateInvalidVenue(){
        Venue venue = new Venue("The Karaoke Bar", "", "New York", "NY",
                "United States", 123456);
        venue.setVenueId(6);
        when(venueRepository.update(venue)).thenReturn(true);
        Result<Venue> actual = venueService.update(venue);
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotUpdateNonExistingVenue() {
        Venue venue = new Venue("The Karaoke Bar", "444 Bourbon St", "New Orleans", "LA",
                "United States", 123456);
        venue.setVenueId(6);
        when(venueRepository.update(venue)).thenReturn(false);
        Result<Venue> actual = venueService.update(venue);
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }
}