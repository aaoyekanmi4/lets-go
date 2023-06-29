package learn.letsgo.Data;

import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventJdbcTemplateRepositoryTest {

    @Autowired
    EventJdbcTemplateRepository eventRepository;

    @Autowired
    VenueJdbcTemplateRepository venueRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllEventsByUserId() {
        List<Event> events = eventRepository.findAllByUserId(1);
        assertNotNull(events);
        assertEquals(1, events.size());
    }

    @Test
    void shouldFindEventById() {
        Event actual = eventRepository.findById(1);
        assertNotNull(actual);
        assertEquals("concert", actual.getCategory());
        assertEquals(2, actual.getEventPosts().size());
        assertEquals(1, actual.getAppUsers().size());
    }

    @Test
    void shouldCreateEvent() {
        Event actual = eventRepository.create(makeEvent());
        assertNotNull(actual);
        assertEquals(3, actual.getVenue().getVenueId());
        assertEquals(4, actual.getEventId());
    }

    @Test
    void shouldUpdateEvent() {
        Event eventToUpdate = eventRepository.findById(2);
        eventToUpdate.setCategory("festivals");
        boolean actual = eventRepository.update(eventToUpdate);
        assertTrue(actual);
        Event updatedEvent = eventRepository.findById(2);
        assertEquals("festivals", updatedEvent.getCategory());
    }

    @Test
    void shouldDeleteEventById() {
        boolean actual = eventRepository.deleteById(3);
        assertTrue(actual);
        assertFalse(eventRepository.deleteById(3));
    }

    Event makeEvent () {
        Venue venue = venueRepository.findById(3);
        Event event = new Event("sports",
                "Bulls vs Spurs", "www.coolpic4.net/image/large.jpg", "",
                LocalDateTime.parse("2018-08-09T19:00:00"), "TicketMaster",
                "1034901", "https://example.com", venue);
        return event;
    }
}