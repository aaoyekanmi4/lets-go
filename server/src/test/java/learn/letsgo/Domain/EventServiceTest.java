package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Data.EventRepository;

import learn.letsgo.Data.SavedEventRepository;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventServiceTest {

    @Autowired
    EventService eventService;

    @MockBean
    EventRepository eventRepository;

    @MockBean
    SavedEventRepository savedEventRepository;

    @MockBean
    AppUserRepository appUserRepository;

    @Test
    void shouldCreateAndAddEvent() {
        Event event = makeEvent();
        Event eventOut = event;
        eventOut.setEventId(22);
        AppUser appUser = makeAppUser();
        when(appUserRepository.findById(3)).thenReturn(appUser);
        when(eventRepository.findAll()).thenReturn(makeExistingEvents());
        when(eventRepository.create(event)).thenReturn(eventOut);
        when(eventRepository.findById(22)).thenReturn(event);
        when(savedEventRepository.addEventToUser(event.getEventId(), 3)).thenReturn(true);
        Result<Event> actual = eventService.saveEventToUser(event, 3);

        assertTrue(actual.isSuccess());
        assertEquals(22, actual.getPayload().getEventId());
    }

    @Test
    void shouldReturnExistingEvent() {
        Event event = makeEvent();
        event.setSourceId("721901");
        event.setEventId(1);
        when(eventRepository.findAll()).thenReturn(makeExistingEvents());
        when(appUserRepository.findById(3)).thenReturn(makeAppUser());
        when(eventRepository.findById(4)).thenReturn(makeExistingEvents().get(1));
        when(savedEventRepository.addEventToUser(4, 3)).thenReturn(true);
        Result<Event> actual = eventService.saveEventToUser(makeExistingEvents().get(1), 3);
        assertEquals(4, actual.getPayload().getEventId());
    }

    @Test
    void shouldNotAddWithInvalidName() {
        Event event = makeEvent();
        event.setEventName(null);

        Result<Event> actual = eventService.saveEventToUser(event, 3);

        assertFalse(actual.isSuccess());

        assertEquals("Event name is required",actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithInvalidDate() {
        Event noDateEvent = makeEvent();
        noDateEvent.setDateTime(null);

        Result<Event> actual2 = eventService.saveEventToUser(noDateEvent, 3);

        assertFalse(actual2.isSuccess());

        assertEquals("Datetime is required",actual2.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullEvent() {
        Result<Event> actual = eventService.saveEventToUser(null, 3);
        assertFalse(actual.isSuccess());

        assertEquals("Event cannot be null",actual.getMessages().get(0));
    }

    @Test
    void shouldRemoveEventFromUser() {
        List<Event> events = makeExistingEvents();
        AppUser appUser = makeAppUser();
        appUser.setEvents(events);
        appUser.setAppUserId(1);
        when(appUserRepository.findById(1)).thenReturn(appUser);
        when(savedEventRepository.removeEventFromUser(events.get(0).getEventId(), 1)).thenReturn(true);

        when(eventRepository.findById(events.get(0).getEventId())).thenReturn(events.get(0));
        Result<Event> actual = eventService.removeEventFromUser(events.get(0).getEventId(), 1);

        assertTrue(actual.isSuccess());

    }

    @Test
    void shouldNotRemoveEventFromUserWhenNotThere() {
        List<Event> events = makeExistingEvents();
        AppUser appUser = makeAppUser();
        appUser.setEvents(makeExistingEvents());
        appUser.setAppUserId(1);
        Event newEvent = new Event();
        newEvent.setEventId(47);
        when(appUserRepository.findById(1)).thenReturn(appUser);
        when(savedEventRepository.removeEventFromUser(newEvent.getEventId(),1)).thenReturn(false);
        when(eventRepository.findById(events.get(0).getEventId())).thenReturn(events.get(0));
        Result<Event> actual = eventService.removeEventFromUser(events.get(0).getEventId(), 1);
        assertFalse(actual.isSuccess());

    }

    @Test
    void shouldNotRemoveEventFromUserWhenUserIdWrong() {
        List<Event> events = makeExistingEvents();
        AppUser appUser = makeAppUser();
        appUser.setEvents(events);
        appUser.setAppUserId(1);
        when(appUserRepository.findById(1)).thenReturn(null);
        when(savedEventRepository.removeEventFromUser(events.get(0).getEventId(), 1)).thenReturn(true);

        when(eventRepository.findById(events.get(0).getEventId())).thenReturn(events.get(0));
        Result<Event> actual = eventService.removeEventFromUser(events.get(0).getEventId(), 1);

        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotRemoveEventFromUserWhenEventIdWrong() {
        List<Event> events = makeExistingEvents();
        AppUser appUser = makeAppUser();
        appUser.setEvents(events);
        appUser.setAppUserId(1);
        when(appUserRepository.findById(1)).thenReturn(null);
        when(savedEventRepository.removeEventFromUser(events.get(0).getEventId(), 1)).thenReturn(true);

        when(eventRepository.findById(events.get(0).getEventId())).thenReturn(null);
        Result<Event> actual = eventService.removeEventFromUser(events.get(0).getEventId(), 1);

        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdateEvent() {
        Event event = makeEvent();
        event.setEventId(10);
        event.setEventName("Hamilton");
        when(eventRepository.update(event)).thenReturn(true);
        Result<Event> actual = eventService.update(event);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidEvent() {
        Event event = makeEvent();
        event.setEventId(0);

        Result<Event> actual = eventService.update(event);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateWhenMissing() {
        Event event = makeEvent();
        event.setEventId(15);
        when(eventRepository.update(event)).thenReturn(false);
        Result<Event> actual = eventService.update(event);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldDeleteById() {
        when(eventRepository.deleteById(2)).thenReturn(true);
        assertTrue(eventService.deleteById(2));
    }

    @Test
    void shouldNotDeleteByIdWhenMissing() {
        when(eventRepository.deleteById(2)).thenReturn(false);
        assertFalse(eventService.deleteById(2));
    }


    Event makeEvent() {
        Venue venue = new Venue( "The Wine Cellar", "2222 Fifth St.",
                "New York", "NY", "United States", 29292);
        return new Event("Janet Jackson Live!", "concert", "https://chairnerd.global.ssl.fastly.net/images/performers/8741/555bce1815140ad65ab0b1066467ae7d/huge.jpg", "",
                LocalDateTime.parse("2012-03-09T19:00:00"),
                "TicketMaster", "721401", "https://example.com" ,venue);

    }

    List<Event> makeExistingEvents() {
        Venue venue = new Venue( "The Wine Cellar", "2222 Fifth St.",
                "New York", "NY", "United States", 29292);
        Event event1 =  new Event( "concert", "Usher Live!", "https://chairnerd.global.ssl.fastly.net/images/performers/8741/555bce1815140ad65ab0b1066467ae7d/huge.jpg", "",
                LocalDateTime.parse("2012-03-09T19:00:00"),
                "LiveWire", "721901", "https://example.com" ,venue);
        event1.setEventId(3);
        Event event2 = new  Event("Janet Jackson Live!", "concert", "https://chairnerd.global.ssl.fastly.net/images/performers/8741/555bce1815140ad65ab0b1066467ae7d/huge.jpg", "",
                LocalDateTime.parse("2012-03-09T19:00:00"),
                "TicketMaster", "721901", "https://example.com" ,venue);
        event2.setEventId(4);
        return List.of(event1, event2);
    }

    AppUser makeAppUser () {
        AppUser appUser = new AppUser(10, "bill@dev10.com",
                "password", "bill@dev10.com", "4444444",
                "Bill", "Bellamy", true, List.of("USER", "ADMIN"));
        return appUser;
    }
}