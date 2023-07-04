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
        Result<Void> actual = savedEventService.addContactToEvent(1,3);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddContactToEventWhenContactMissing() {
        when(savedEventRepository.findById(3)).thenReturn(new SavedEvent());
        when(contactRepository.findById(1)).thenReturn(null);
        when(savedEventRepository.addContactToEvent(1,3)).thenReturn(true);
        Result<Void> actual = savedEventService.addContactToEvent(1,3);
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
        Result<Void> actual = savedEventService.addContactToEvent(10,3);
        assertFalse(actual.isSuccess());
        assertEquals("Contact id 10 already in this saved event",
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
        Result<Void> actual = savedEventService.removeContactFromEvent(10,3);
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
        Result<Void> actual = savedEventService.removeContactFromEvent(9,3);
        assertEquals("Contact with id 9 not in this saved event for removal", actual.getMessages().get(0));
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldAddGroupToEvent() {
        when(savedEventRepository.findById(3)).thenReturn(new SavedEvent());
        when(groupRepository.findById(1)).thenReturn(new Group());
        when(savedEventRepository.addGroupToEvent(1,3)).thenReturn(true);
        Result<Void> actual = savedEventService.addGroupToEvent(1,3);
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
        Result<Void> actual = savedEventService.removeGroupFromEvent(10,3);
        System.out.println(actual.getMessages());
        assertTrue(actual.isSuccess());
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