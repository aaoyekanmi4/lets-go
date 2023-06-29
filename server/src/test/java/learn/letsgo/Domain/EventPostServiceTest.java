package learn.letsgo.Domain;

import learn.letsgo.Data.EventPostRepository;
import learn.letsgo.Models.EventPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventPostServiceTest {

    @Autowired
    EventPostService eventPostService;

    @MockBean
    EventPostRepository eventPostRepository;

    @Test
    void shouldAddEventPost () {
        EventPost post = new EventPost(1, 1, "When do you want to meet up?", 0);
        EventPost postOut = new EventPost(1, 1, "When do you want to meet up?", 0);
        postOut.setPostId(4);
        when(eventPostRepository.create(post)).thenReturn(postOut);
        Result<EventPost> actual = eventPostService.create(post);
        assertEquals(postOut, actual.getPayload());
    }

    @Test
    void shouldNotAddWithMissingBody() {
        EventPost eventPost = new EventPost(1, 1, "", 0);
        eventPost.setPostId(1);
        Result<EventPost> actual = eventPostService.create(eventPost);
        assertEquals(ResultType.INVALID, actual.getStatus());
        assertEquals("Post Body is required", actual.getMessages().get(0));
    }

//    @Test
//    void shouldNotAddWithMissingAuthor() {
//        EventPost eventPost1 = new EventPost(1, "blue@red.com",
//                "2222222", "", "James");
//        eventPost1.setEventId(1);
//        Result<EventPost> firstMissing = eventPostService.create(eventPost1);
//        assertEquals(ResultType.INVALID, firstMissing.getStatus());
//        assertEquals("First and last name are required", firstMissing.getMessages().get(0));
//
//        EventPost eventPost2 = new EventPost(1, "yellow@green.com",
//                "2222222", "Rick", "");
//        eventPost2.setPostId(1);
//        Result<EventPost> lastMissing = eventPostService.create(eventPost2);
//        assertEquals(ResultType.INVALID, lastMissing.getStatus());
//        assertEquals("First and last name are required", lastMissing.getMessages().get(0));
//    }
//

    @Test
    void shouldUpdate() {
        EventPost eventPost = new EventPost(1, 1, "foo bar", 0);
        eventPost.setPostId(3);
        when(eventPostRepository.update(eventPost)).thenReturn(true);
        Result<EventPost> actual = eventPostService.update(eventPost);
        System.out.println(actual.getMessages());
        assertEquals(ResultType.SUCCESS, actual.getStatus());
    }

    @Test
    void shouldNotUpdateMissingPost() {
        EventPost eventPost = new EventPost(1, 1, "foo bar", 0);
        eventPost.setPostId(3);
        when(eventPostRepository.update(eventPost)).thenReturn(false);
        Result<EventPost> actual = eventPostService.update(eventPost);
        assertEquals(ResultType.NOT_FOUND, actual.getStatus());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        EventPost eventPost =  new EventPost(1, 1, "", 0);

        Result<EventPost> actual = eventPostService.update(eventPost);
        assertEquals(ResultType.INVALID, actual.getStatus());

        eventPost.setPostBody("Here we go!");
        eventPost.setPostId(0);
        actual = eventPostService.update(eventPost);
        assertEquals(ResultType.INVALID, actual.getStatus());
    }
}