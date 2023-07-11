package learn.letsgo.Domain;

import learn.letsgo.Data.ContactRepository;
import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Data.SavedEventRepository;
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
class SavedEventServiceTest {

    @Autowired
    SavedEventService savedEventService;

    @MockBean
    SavedEventRepository savedEventRepository;

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    ContactRepository contactRepository;

    @Test
    void shouldAddContactToEvent() {
        when(savedEventRepository.findById(3)).thenReturn(new SavedEvent());
        when(contactRepository.findById(1)).thenReturn(new Contact());
        when(savedEventRepository.addContactToEvent(1,3)).thenReturn(true);
        Result<Contact> actual = savedEventService.addContactToEvent(1,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddContactToEventWhenContactMissing() {
        when(savedEventRepository.findById(3)).thenReturn(new SavedEvent());
        when(contactRepository.findById(1)).thenReturn(null);
        when(savedEventRepository.addContactToEvent(1,3)).thenReturn(true);
        Result<Contact> actual = savedEventService.addContactToEvent(1,3);
        assertFalse(actual.isSuccess());
        assertEquals("Could not find contact with contactId: 1",
                actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddContactToEventWhenAlreadyPresent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setContacts(makeContactsList());
        Contact contactToAdd = new Contact();
        contactToAdd.setContactId(10);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(contactRepository.findById(10)).thenReturn(makeContactsList().get(0));
        Result<Contact> actual = savedEventService.addContactToEvent(10,3);
        assertFalse(actual.isSuccess());
        assertEquals("Contact with id 10 already in saved event",
                actual.getMessages().get(0));
        System.out.println(actual.getMessages());
    }

    @Test
    void shouldRemoveContactFromEvent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setContacts(makeContactsList());
        Contact contactToRemove = new Contact();
        contactToRemove.setContactId(10);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(savedEventRepository.removeContactFromEvent(10,3)).thenReturn(true);
        when(contactRepository.findById(10)).thenReturn(contactToRemove);
        Result<Contact> actual = savedEventService.removeContactFromEvent(10,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotRemoveContactFromEventNotInList() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setContacts(makeContactsList());
        Contact contactToRemove = new Contact();
        contactToRemove.setContactId(9);
        when(contactRepository.findById(9)).thenReturn(contactToRemove);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        Result<Contact> actual = savedEventService.removeContactFromEvent(9,3);
        assertEquals("Contact with id 9 not in saved event so cannot be removed", actual.getMessages().get(0));
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldAddGroupToEvent() {
        when(savedEventRepository.findById(3)).thenReturn(new SavedEvent());
        when(groupRepository.findById(1)).thenReturn(new Group());
        when(savedEventRepository.addGroupToEvent(1,3)).thenReturn(true);
        Result<Group> actual = savedEventService.addGroupToEvent(1,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldRemoveGroupFromEvent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setGroups(makeGroupList());
        System.out.println(savedEvent.getGroups());
        Group groupToRemove = new Group();
        groupToRemove.setGroupId(10);
        when(savedEventRepository.getSavedEventId(1,2)).thenReturn(3);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(savedEventRepository.removeGroupFromEvent(10,3)).thenReturn(true);
        when(groupRepository.findById(10)).thenReturn(groupToRemove);
        Result<Group> actual = savedEventService.removeGroupFromEvent(10,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldBatchAddContactsToSavedEvent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        List<Integer> contactIds = List.of(10, 12);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(contactRepository.findById(10)).thenReturn(new Contact());
        when(contactRepository.findById(12)).thenReturn(new Contact());
        when(savedEventRepository.batchAddContactsToEvent(contactIds, 3)).thenReturn(true);
        Result<Contact> actual = savedEventService.batchAddContactsToSavedEvent(contactIds, 3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotBatchAddContactsToSavedEventWhenAlreadyPresent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        savedEvent.setContacts(makeContactsList());
        List<Integer> contactIds = makeContactsList().stream().map(Contact::getContactId).toList();
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(contactRepository.findById(10)).thenReturn(new Contact());
        when(contactRepository.findById(12)).thenReturn(new Contact());
        when(savedEventRepository.batchAddContactsToEvent(contactIds, 3)).thenReturn(true);
        Result<Contact> actual = savedEventService.batchAddContactsToSavedEvent(contactIds, 3);
        assertFalse(actual.isSuccess());
        assertEquals("Contact with id 10 already in saved event", actual.getMessages().get(0));
    }

    @Test
    void shouldNotBatchAddContactsToSavedEventWhenAContactMissing() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        savedEvent.setContacts(makeContactsList());
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(contactRepository.findById(15)).thenReturn(new Contact());
        when(contactRepository.findById(17)).thenReturn(null);
        when(savedEventRepository.batchAddContactsToEvent(List.of(15,17), 3)).thenReturn(true);
        Result<Contact> actual = savedEventService.batchAddContactsToSavedEvent(List.of(15,17), 3);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldBatchUpdateContactsInSavedEvent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(contactRepository.findById(10)).thenReturn(new Contact());
        when(contactRepository.findById(14)).thenReturn(new Contact());
        when(savedEventRepository.batchUpdateContactsInEvent(List.of(10,14), 3)).thenReturn(true);
        Result<Contact> actual = savedEventService.batchUpdateContactsInSavedEvent(List.of(10,14), 3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotBatchUpdateContactsWhenSavedEventMissing() {
        when(savedEventRepository.findById(3)).thenReturn(null);
        when(contactRepository.findById(15)).thenReturn(new Contact());
        when(contactRepository.findById(17)).thenReturn(new Contact());
        when(savedEventRepository.batchUpdateContactsInEvent(List.of(15,17), 3)).thenReturn(true);
        Result<Contact> actual = savedEventService.batchUpdateContactsInSavedEvent(List.of(15,17), 3);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldBatchAddGroupsToSavedEvent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        List<Integer> groupIds = List.of(10, 12);
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(groupRepository.findById(10)).thenReturn(new Group());
        when(groupRepository.findById(12)).thenReturn(new Group());
        when(savedEventRepository.batchAddGroupsToEvent(groupIds, 3)).thenReturn(true);
        Result<Group> actual = savedEventService.batchAddGroupsToSavedEvent(groupIds, 3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotBatchAddGroupsToSavedEventWhenAlreadyPresent() {
        SavedEvent savedEvent = new SavedEvent();
        savedEvent.setSavedEventId(3);
        savedEvent.setGroups(makeGroupList());
        List<Integer> groupIds = makeGroupList().stream().map(Group::getGroupId).toList();
        when(savedEventRepository.findById(3)).thenReturn(savedEvent);
        when(groupRepository.findById(10)).thenReturn(new Group());
        when(groupRepository.findById(12)).thenReturn(new Group());
        when(savedEventRepository.batchAddGroupsToEvent(groupIds, 3)).thenReturn(true);
        Result<Group> actual = savedEventService.batchAddGroupsToSavedEvent(groupIds, 3);
        assertFalse(actual.isSuccess());
        assertEquals("Group with id 10 already in saved event", actual.getMessages().get(0));
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

    List<Group> makeGroupList() {
        Group group = new Group(2, "The Party People");
        group.setGroupId(10);
        Group group2 = new Group(3, "The Yoga Friends");
        group2.setGroupId(12);
        return List.of(group, group2);
    }
}