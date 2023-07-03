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
class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @MockBean
    AppUserRepository appUserRepository;

    @Test
    void shouldSendValidEmail() {
        EmailMessage msg = makeEmailMessage();
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<Void> actual = emailService.sendEmail(msg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotSendEmailWhenUserNotFound() {
        EmailMessage msg = makeEmailMessage();
        when(appUserRepository.findById(12)).thenReturn(null);
        Result<Void> actual = emailService.sendEmail(msg);
        assertFalse(actual.isSuccess());
        assertEquals("User not found.", actual.getMessages().get(0));
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldNotSendEmailWithoutSavedEvent() {
        EmailMessage msg = makeEmailMessage();
        msg.setSavedEvent(null);
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<Void> actual = emailService.sendEmail(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Event for message cannot be null", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotSendEmailWhenRecipientsListEmpty() {
        EmailMessage msg = makeEmailMessage();
        msg.setRecipients(new ArrayList<>());
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<Void> actual = emailService.sendEmail(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Recipients are required", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldNotSendEmailWithoutLink() {
        EmailMessage msg = makeEmailMessage();
        msg.setEventDetailURL("");
        when(appUserRepository.findById(12)).thenReturn(makeAppUser());
        Result<Void> actual = emailService.sendEmail(msg);
        assertFalse(actual.isSuccess());
        assertEquals("Link to app saved event cannot be null", actual.getMessages().get(0));
        assertEquals(ResultType.INVALID, actual.getStatus());
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

    EmailMessage makeEmailMessage() {
        SavedEvent savedEvent = new SavedEvent(12, makeEvent());
        savedEvent.setContacts(new ArrayList<>());
        savedEvent.setGroups(new ArrayList<>());
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSavedEvent(savedEvent);
        emailMessage.setRecipients(List.of("aritoyekan@gmail.com"));
        emailMessage.setEventDetailURL("https://www.google.com");
        return emailMessage;
    }

    AppUser makeAppUser() {
        AppUser appUser = new AppUser(10, "bill@dev10.com",
                "password", "bill@dev10.com", "4444444",
                "Bill", "Bellamy", true, List.of("USER", "ADMIN"));
        return appUser;
    }
}