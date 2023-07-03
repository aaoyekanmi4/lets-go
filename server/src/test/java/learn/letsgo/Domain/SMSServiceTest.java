package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SMSServiceTest {

    @Autowired
    SMSService smsService;

    @MockBean
    AppUserRepository appUserRepository;

    @Test
    void shouldCreateText() {
        SMSMessage msg = makeSMSMessage();
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(msg);
        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
    }

    @Test
    void shouldNotCreateTextWhenMessageNull() {
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(null);
        assertFalse(actual.isSuccess());
        assertEquals("Message cannot be null", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTextWhenUserIdNotPresent() {
        SMSMessage msg = makeSMSMessage();
        SavedEvent withoutUserId = msg.getSavedEvent();
        withoutUserId.setAppUserId(0);
        msg.setSavedEvent(withoutUserId);
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Message must contain a valid user id", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotCreateTextWhenSavedEventNameMissing() {
        SMSMessage msg = makeSMSMessage();
        SavedEvent withoutEventName = msg.getSavedEvent();
        withoutEventName.getEvent().setEventName(null);
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Event name is required", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotCreateWhenURLMissing() {
        SMSMessage msg = makeSMSMessage();
        msg.setEventDetailUrl("");
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Link to event in app is required", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotCreateWhenUserNotFound() {
        SMSMessage msg = makeSMSMessage();
        when(appUserRepository.findById(12)).thenReturn(null);
        Result<String> actual = smsService.createTextBody(msg);
        assertFalse(actual.isSuccess());
        assertEquals("User not found.", actual.getMessages().get(0));
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldNotCreateWithNullOrBlankRecipient() {
        SMSMessage msg = makeSMSMessage();
        msg.setRecipient("");
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual = smsService.createTextBody(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Recipient is required", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());

        SMSMessage msg2 = makeSMSMessage();
        msg2.setRecipient(null);
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<String> actual2 = smsService.createTextBody(msg2);
        assertFalse(actual2.isSuccess());
        assertEquals("Recipient is required", actual2.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual2.getStatus());
    }

    Event makeEvent() {
        Venue venue = new Venue("The Wine Cellar", "2222 Fifth St.",
                "New York", "NY", "United States", 29292);
        venue.setVenueId(1);
        Event event = new Event("Janet Jackson Live!", "concert", "https://chairnerd.global.ssl.fastly.net/images/performers/8741/555bce1815140ad65ab0b1066467ae7d/huge.jpg", "",
                LocalDateTime.parse("2012-03-09T19:00:00"),
                "TicketMaster", "721401", "https://example.com", venue);
        event.setEventId(4);
        event.setEventPosts(new ArrayList<>());
        return event;

    }

    SMSMessage makeSMSMessage() {
        SavedEvent savedEvent = new SavedEvent(12, makeEvent());
        savedEvent.setContacts(new ArrayList<>());
        savedEvent.setGroups(new ArrayList<>());
        SMSMessage message = new SMSMessage();
        message.setSavedEvent(savedEvent);
        message.setRecipient("18325477840");
        message.setEventDetailUrl("www.google.com");
        return message;
    }

    AppUser makeAppUser() {
        AppUser appUser = new AppUser(10, "bill@dev10.com",
                "password", "bill@dev10.com", "4444444",
                "Bill", "Bellamy", true, List.of("USER", "ADMIN"));
        return appUser;
    }
}