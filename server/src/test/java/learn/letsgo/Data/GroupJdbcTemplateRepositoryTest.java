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
class GroupJdbcTemplateRepositoryTest {

    @Autowired
    GroupJdbcTemplateRepository groupRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllGroupsByUserId() {
        List<Group> groups = groupRepository.findAllByUserId(2);
        assertNotNull(groups);
        assertEquals(2, groups.size());
    }

    @Test
    void shouldFindAllBySavedEventId() {
        List<Group> groups= groupRepository.findAllBySavedEventId(1);
        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

    @Test
    void shouldFindGroupById() {
        Group actual = groupRepository.findById(1);
        assertNotNull(actual);
        assertEquals("The Adventurers", actual.getName());
    }

    @Test
    void shouldAddContactToGroup() {
        int initialCount =  groupRepository.findById(1).getContacts().size();
        boolean actual = groupRepository.addContactToGroup(1,1);
        assertTrue(actual);
        int resultCount = groupRepository.findById(1).getContacts().size();
        assertEquals(1, resultCount - initialCount);
    }

    @Test
    void shouldBatchAddContactsToGroup() {
        System.out.println(groupRepository.findById(1).getContacts());
        boolean actual = groupRepository.batchAddContactsToGroup(List.of(2), 1);
        assertTrue(actual);
    }

    @Test
   void shouldRemoveContactFromGroup() {
        int initialCount =  groupRepository.findById(1).getContacts().size();
        boolean actual = groupRepository.removeContactFromGroup(1,1);
        assertTrue(actual);
        int resultCount = groupRepository.findById(1).getContacts().size();
        assertEquals(-1, resultCount - initialCount);
    }

    @Test
    void shouldCreateGroup() {
        Group actual = groupRepository.create(makeGroup());
        assertNotNull(actual);
        assertEquals(1, actual.getAppUserId());
    }

    @Test
    void shouldUpdateGroup() {
        Group groupToUpdate = groupRepository.findById(2);
        groupToUpdate.setName("Bluebirds");

        boolean actual = groupRepository.update(groupToUpdate);
        assertTrue(actual);

        Group updatedGroup = groupRepository.findById(2);
        assertEquals("Bluebirds", updatedGroup.getName());
    }

    @Test
    void shouldDeleteGroupById() {
        assertTrue(groupRepository.deleteById(3));
        assertFalse(groupRepository.deleteById(3));
    }

    Group makeGroup () {
        Group group = new Group(1, "TestGroup");
        return group;
    }
}