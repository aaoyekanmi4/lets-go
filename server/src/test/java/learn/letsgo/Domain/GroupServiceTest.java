package learn.letsgo.Domain;

import learn.letsgo.Data.GroupRepository;
import learn.letsgo.Models.Contact;
import learn.letsgo.Models.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceTest {
    @Autowired
    GroupService groupService;

    @MockBean
    GroupRepository groupRepository;

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
}