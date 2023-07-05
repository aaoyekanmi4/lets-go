package learn.letsgo.Data;

import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactJdbcTemplateRepositoryTest {

    @Autowired
    ContactJdbcTemplateRepository contactRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllContactsByUserId() {
        List<Contact> contacts = contactRepository.findAllByUserId(1);
        assertEquals(2, contacts.size());
    }

    @Test
    void shouldFindAllBySavedEventId() {
        List<Contact> contacts= contactRepository.findAllBySavedEventId(1);
        assertNotNull(contacts);
        assertEquals(2, contacts.size());
    }

    @Test
    void shouldFindContactById() {
        Contact actual = contactRepository.findById(1);
        assertNotNull(actual);
        assertEquals("Rick", actual.getFirstName());
        assertEquals(1, actual.getGroups().size());
    }

    @Test
    void shouldCreateContact() {
        Contact actual = contactRepository.create(makeContact());
        assertNotNull(actual);
        assertEquals("Bob", actual.getLastName());
    }

    @Test
    void shouldUpdateContact() {
        Contact contactToUpdate = contactRepository.findById(2);
        contactToUpdate.setEmail("green@gmail.com");
        boolean actual = contactRepository.update(contactToUpdate);
        assertTrue(actual);
        Contact updatedContact = contactRepository.findById(2);
        assertEquals("green@gmail.com", updatedContact.getEmail());
    }

    @Test
    void shouldDeleteContactById() {
        assertTrue(contactRepository.deleteById(3));
        assertFalse(contactRepository.deleteById(3));
    }

    Contact makeContact() {
        Contact contact = new Contact(2, "spongebob@yahoo.com",
                "3333333", "Sponge", "Bob");
        return contact;
    }
}