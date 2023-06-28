package learn.letsgo.Data;

import learn.letsgo.Models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void shouldAddContactToGroup() {
        // int initialSize = groupRepository.findById(groupId).getContacts.size();
        // boolean actual = groupRepository.addContactToGroup(int contactId, int groupId);
        // assertTrue(actual)
        // int finalSize = groupRepository.findById(groupId).getContacts.size();
        // assertEquals(1, finalSize - initialSize);
    }

    @Test
    void shouldFindGroupById() {
        Group actual = groupRepository.findById(1);
        assertNotNull(actual);
        assertEquals("The Adventurers", actual.getName());
        assertEquals(1, actual.getContacts().size());
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