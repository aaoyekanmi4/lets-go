package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import learn.letsgo.Models.SavedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceTest {
    @Autowired
    GroupService groupService;

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    ContactRepository contactRepository;

    @Test
    void shouldAddGroup () {
        Group group = new Group(1, "The Tigers");

        Group mockOut = new Group(1, "The Tigers");
        mockOut.setGroupId(1);


        when(groupRepository.create(group)).thenReturn(mockOut);

        Result<Group> actual = groupService.create(group);
        assertEquals(ResultType.SUCCESS, actual.getStatus());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddGroupWithoutName() {
        Group group = new Group(1, "");

        group.setGroupId(1);

        Result<Group> actual = groupService.create(group);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("Name is required", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenGroupNull() {
        Result<Group> actual = groupService.create(null);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("Group cannot be null", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdateGroup() {
        Group group = new Group(1, "The Bluebirds");
        group.setGroupId(2);
        when(groupRepository.update(group)).thenReturn(true);
        Result<Group> actual = groupService.update(group);
        System.out.println(actual.getMessages());
        assertEquals(ResultType.SUCCESS, actual.getStatus());
    }

    @Test
    void shouldNotUpdateMissingGroup() {
        Group group = new Group(1, "The Bluebirds");
        group.setGroupId(33);
        when(groupRepository.update(group)).thenReturn(false);
        Result<Group> actual = groupService.update(group);
        System.out.println(actual.getMessages());
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldNotUpdateInvalidGroup() {
        Group group = new Group(1, "");
        group.setGroupId(33);
        when(groupRepository.update(group)).thenReturn(false);
        Result<Group> actual = groupService.update(group);
        System.out.println(actual.getMessages());
        assertEquals(ResultType.INVALID, actual.getStatus());
    }

    @Test
    void shouldAddContactToGroup() {
        when(groupRepository.findById(3)).thenReturn(new Group());
        when(contactRepository.findById(1)).thenReturn(new Contact());
        when(groupRepository.addContactToGroup(1,3)).thenReturn(true);
        Result<Void> actual = groupService.addContactToGroup(1,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddContactToGroupWhenContactMissing() {
        when(groupRepository.findById(3)).thenReturn(new Group());
        when(contactRepository.findById(1)).thenReturn(null);
        when(groupRepository.addContactToGroup(1,3)).thenReturn(true);
        Result<Void> actual = groupService.addContactToGroup(1,3);
        assertFalse(actual.isSuccess());
        assertEquals("Could not find contact with contactId: 1",
                actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddContactToGroupWhenGroupMissing() {
        when(groupRepository.findById(3)).thenReturn(null);
        when(contactRepository.findById(1)).thenReturn(new Contact());
        when(groupRepository.addContactToGroup(1,3)).thenReturn(true);
        Result<Void> actual = groupService.addContactToGroup(1,3);
        assertFalse(actual.isSuccess());
        assertEquals("Could not find group with groupId: 3",
                actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddContactToGroupWhenAlreadyPresent() {
        Group group = makeGroup();
        group.setContacts(makeContactsList());
        Contact contactToAdd = new Contact();
        contactToAdd.setContactId(10);
        when(groupRepository.findById(3)).thenReturn(group);
        when(contactRepository.findById(10)).thenReturn(makeContactsList().get(0));
        Result<Void> actual = groupService.addContactToGroup(10,3);
        assertFalse(actual.isSuccess());
        assertEquals("Contact with id 10 already in group",
                actual.getMessages().get(0));
        System.out.println(actual.getMessages());
    }

    @Test
    void shouldRemoveContactFromEvent() {
        Group group = makeGroup();
        group.setContacts(makeContactsList());
        Contact contactToRemove = new Contact();
        contactToRemove.setContactId(10);
        when(groupRepository.findById(3)).thenReturn(group);
        when(groupRepository.removeContactFromGroup(10,3)).thenReturn(true);
        when(contactRepository.findById(10)).thenReturn(contactToRemove);
        Result<Void> actual = groupService.removeContactFromGroup(10,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotRemoveContactFromEventNotInList() {
        Group group = makeGroup();
        group.setContacts(makeContactsList());
        Contact contactToRemove = new Contact();
        contactToRemove.setContactId(9);
        when(contactRepository.findById(9)).thenReturn(contactToRemove);
        when(groupRepository.findById(3)).thenReturn(group);
        Result<Void> actual = groupService.removeContactFromGroup(9,3);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldBatchAddContactsToGroup() {
        Group group = new Group();
        group.setGroupId(3);
        List<Integer> contactIds = List.of(10, 12);
        when(groupRepository.findById(3)).thenReturn(group);
        when(contactRepository.findById(10)).thenReturn(new Contact());
        when(contactRepository.findById(12)).thenReturn(new Contact());
        when(groupRepository.batchAddContactsToGroup(contactIds, 3)).thenReturn(true);
        Result<Void> actual = groupService.batchAddContactsToGroup(contactIds, 3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotBatchAddContactsToGroupWhenInGroup() {
        Group group = new Group();
        group.setGroupId(3);
        group.setContacts(makeContactsList());
        List<Integer> contactIds = makeContactsList().stream().map(contact -> contact.getContactId()).toList();
        when(groupRepository.findById(3)).thenReturn(group);
        when(contactRepository.findById(10)).thenReturn(new Contact());
        when(contactRepository.findById(12)).thenReturn(new Contact());
        when(groupRepository.batchAddContactsToGroup(contactIds, 3)).thenReturn(true);
        Result<Void> actual = groupService.batchAddContactsToGroup(contactIds, 3);
        assertFalse(actual.isSuccess());
        assertEquals("Contact with id 10 already in group", actual.getMessages().get(0));
    }

    @Test
    void shouldNotBatchAddContactsToGroupWhenAContactMissing() {
        Group group = new Group();
        group.setContacts(makeContactsList());
        when(groupRepository.findById(3)).thenReturn(group);
        when(contactRepository.findById(15)).thenReturn(new Contact());
        when(contactRepository.findById(17)).thenReturn(null);
        when(groupRepository.batchAddContactsToGroup(List.of(15,17), 3)).thenReturn(true);
        Result<Void> actual = groupService.batchAddContactsToGroup(List.of(15,17), 3);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    Group makeGroup() {
        return new Group(1, "The Bluebirds");
    }

    List<Contact> makeContactsList() {
        Contact contact = new Contact(2, "spongebob@yahoo.com",
                "3333333", "Sponge", "Bob");
        contact.setContactId(10);
        Contact contact2 = new Contact(3, "tommy@yahoo.com",
                "3333353", "Tommy", "Danger");
        contact2.setContactId(12);
        return List.of(contact, contact2);
    }


}