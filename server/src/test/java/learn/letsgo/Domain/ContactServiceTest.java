package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Models.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    ContactService contactService;

    @MockBean
    ContactRepository contactRepository;

    @Test
    void shouldAdd() {
        Contact contact = new Contact(1, "blue@gmail.com",
                "2222222", "Rick'", "James");
        contact.setContactId(0);
        Contact mockOut = new Contact(1, "blue@gmail.com",
                "2222222", "Rick'", "James");
        mockOut.setContactId(10);

        when(contactRepository.create(contact)).thenReturn(mockOut);

        Result<Contact> actual = contactService.create(contact);
        assertEquals(ResultType.SUCCESS, actual.getStatus());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddWithMissingEmail() {
        Contact contact = new Contact(1, null,
                "2222222", "Rick'", "James");
        contact.setContactId(1);
        Result<Contact> actual = contactService.create(contact);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("Email is required", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAdWithInvalidEmail() {
        Contact contact = new Contact(1, "HowAboutNow?",
                "+18324493289", "Rick'", "James");
        contact.setContactId(1);
        Result<Contact> actual = contactService.create(contact);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("User must enter a valid email", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithMissingName() {
        Contact contact1 = new Contact(1, "blue@red.com",
                "2222222", "", "James");
        contact1.setContactId(1);
        Result<Contact> firstMissing = contactService.create(contact1);
        assertEquals(ResultType.INVALID, firstMissing.getStatus());
        assertEquals("First and last name are required", firstMissing.getMessages().get(0));

        Contact contact2 = new Contact(1, "yellow@green.com",
                "2222222", "Rick", "");
        contact2.setContactId(1);
        Result<Contact> lastMissing = contactService.create(contact2);
        assertEquals(ResultType.INVALID, lastMissing.getStatus());
        assertEquals("First and last name are required", lastMissing.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithMissingPhone() {
        Contact contact = new Contact(1, "blue@gmail.com",
                "", "Rick'", "James");
        contact.setContactId(1);
        Result<Contact> actual = contactService.create(contact);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("Phone number is required", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Contact contact = new Contact(1, "blue@gmail.com",
                "2222222", "Rick'", "James");
        contact.setContactId(3);
        when(contactRepository.update(contact)).thenReturn(true);
        Result<Contact> actual = contactService.update(contact);
        System.out.println(actual.getMessages());
        assertEquals(ResultType.SUCCESS, actual.getStatus());
    }

    @Test
    void shouldNotUpdateMissing() {
        Contact contact = new Contact(1, "blue@gmail.com",
                "2222222", "Rick'", "James");
        contact.setContactId(3);
        when(contactRepository.update(contact)).thenReturn(false);
        Result<Contact> actual = contactService.update(contact);
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Contact contact =  new Contact(1, "blue@gmail.com",
                "", "Rick'", "James");

        Result<Contact> actual = contactService.update(contact);
        assertEquals(ResultType.INVALID, actual.getStatus());

        contact.setPhone("3145678");
        contact.setContactId(0);
        actual = contactService.update(contact);
        assertEquals(ResultType.INVALID, actual.getStatus());
    }
}