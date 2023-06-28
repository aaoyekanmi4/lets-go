package learn.letsgo.Data;

import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.EventPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventPostJdbcTemplateRepositoryTest {

    @Autowired
    EventPostJdbcTemplateRepository eventPostRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllEventPostsByEventId() {
        List<EventPost> posts = eventPostRepository.findAllByEventId(1);
        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    @Test
    void shouldFindEventPostById() {
        EventPost actual = eventPostRepository.findById(1);
        assertNotNull(actual);
        assertEquals("Who is going to this concert?", actual.getPostBody());
    }

    @Test
    void shouldCreateEventPost() {
        EventPost actual = eventPostRepository.create(makeEventPost());
        assertNotNull(actual);
        assertEquals("Where is everyone?", actual.getPostBody());
    }

    @Test
    void shouldUpdateEventPost() {
        EventPost postToUpdate = eventPostRepository.findById(2);
        postToUpdate.setLikes(22);

        boolean actual = eventPostRepository.update(postToUpdate);
        assertTrue(actual);

        EventPost updatedPost = eventPostRepository.findById(2);
        assertEquals(22, updatedPost.getLikes());
    }

    @Test
    void shouldDeleteEventPostById() {
        assertTrue(eventPostRepository.deleteById(3));
        assertFalse(eventPostRepository.deleteById(3));
    }

    EventPost makeEventPost() {
        Event event = new Event();
        event.setEventId(10);
        AppUser appUser = new AppUser(10, "JohnnyBravo", "password", "jb@gmail.com",
                "4444444","Johnny", "Rivers", true, List.of("USER", "ADMIN"));
        EventPost post  = new EventPost();
        post.setEvent(event);
        post.setAppUser(appUser);
        post.setPostBody("Where is everyone?");
        post.setLikes(5);
        return post;
    }
}