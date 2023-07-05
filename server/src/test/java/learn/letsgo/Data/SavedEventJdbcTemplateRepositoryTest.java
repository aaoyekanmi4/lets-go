package learn.letsgo.Data;

import learn.letsgo.Models.Event;
import learn.letsgo.Models.Group;
import learn.letsgo.Models.SavedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavedEventJdbcTemplateRepositoryTest {

    @Autowired
    SavedEventJdbcTemplateRepository savedEventRepository;

    @Autowired
    AppUserJdbcTemplateRepository appUserRepository;
    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllForUser() {
        List<SavedEvent> actual = savedEventRepository.findAll(2);
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void shouldFindAllById() {
        SavedEvent actual = savedEventRepository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldAddEventToUser() {
        int initialCount =  appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        boolean actual = savedEventRepository.addEventToUser(1,1);
        assertTrue(actual);
        int resultCount = appUserRepository.findByUsername("eric@dev10.com").getEvents().size();
        assertEquals(1, resultCount - initialCount);
    }

    @Test
    void shouldAddContactToEvent() {
        savedEventRepository.removeContactFromEvent(1,1);
        boolean actual = savedEventRepository.addContactToEvent(1,1);
        assertTrue(actual);
    }

    @Test
    void shouldRemoveContactFromEvent() {
        savedEventRepository.addContactToEvent(2,1);
        boolean actual = savedEventRepository.removeContactFromEvent(2,1);
        assertTrue(actual);
        boolean repeat = savedEventRepository.removeContactFromEvent(2,1);
        assertFalse(repeat);
    }

    @Test
    void shouldAddGroupToEvent() {
        boolean actual = savedEventRepository.addGroupToEvent(1,1);
        assertTrue(actual);
    }

    @Test
    void removeGroupFromEvent() {
        boolean actual = savedEventRepository.removeGroupFromEvent(1,1);
        assertTrue(actual);
        boolean repeat = savedEventRepository.removeGroupFromEvent(1,1);
        assertFalse(repeat);
    }

    @Test
    void shouldRemoveEventFromUser() {
        int initialCount =  appUserRepository.findByUsername("arit@dev10.com").getEvents().size();
        boolean actual = savedEventRepository.removeEventFromUser(2,3);
        assertTrue(actual);
        int resultCount = appUserRepository.findByUsername("arit@dev10.com").getEvents().size();
        assertEquals(-1, resultCount - initialCount);
    }

    @Test
    void shouldFindSavedEventForUser() {
        SavedEvent actual = savedEventRepository.findSavedEventForUser(1, 2);
        assertNotNull(actual);
        assertEquals(2, actual.getAppUserId());
    }

    @Test
    void shouldBatchAddContactsToEvent() {
        savedEventRepository.removeContactFromEvent(1,1);
        boolean actual = savedEventRepository.batchAddContactsToEvent(List.of(1), 1);
        assertTrue(actual);
        SavedEvent savedEvent = savedEventRepository.findById(1);
        assertEquals(2, savedEvent.getContacts().size());
    }

    @Test
    void shouldBatchUpdateContactsInEvent() {
        boolean actual = savedEventRepository.batchUpdateContactsInEvent(List.of(3,1), 1);
        assertTrue(actual);
        SavedEvent savedEvent = savedEventRepository.findById(1);
        assertEquals(2, savedEvent.getContacts().size());
    }

    @Test
    void shouldBatchAddGroupsToEvent() {
        savedEventRepository.removeGroupFromEvent(1,1);
        boolean actual = savedEventRepository.batchAddGroupsToEvent(List.of(1), 1);
        assertTrue(actual);
        SavedEvent savedEvent = savedEventRepository.findById(1);
        assertEquals(2, savedEvent.getGroups().size());
    }

    @Test
    void shouldBatchUpdateGroupsInEvent() {
        boolean actual = savedEventRepository.batchUpdateGroupsInEvent(List.of(2), 1);
        assertTrue(actual);
        SavedEvent savedEvent = savedEventRepository.findById(1);
        assertEquals(1, savedEvent.getGroups().size());
    }
}