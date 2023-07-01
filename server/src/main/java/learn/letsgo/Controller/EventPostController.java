package learn.letsgo.Controller;

import learn.letsgo.Domain.EventPostService;
import learn.letsgo.Domain.Result;
import learn.letsgo.Models.EventPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/event-post")
public class EventPostController {

    private final EventPostService postService;

    public EventPostController(EventPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{eventId}")
    public List<EventPost> findAllByEventId(@PathVariable int eventId) {
        return postService.findAllByEventId(eventId);
    }

    @GetMapping("/post/{eventPostId}")
    public EventPost findByPostId(@PathVariable int eventPostId) {
        return postService.findEventPostById(eventPostId);
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody EventPost post) {
        Result<EventPost> result = postService.create(post);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{eventPostId}")
    public ResponseEntity<Object> updatePost(@RequestBody EventPost post, @PathVariable int eventPostId) {
        if (eventPostId != post.getPostId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<EventPost> result = postService.update(post);
        if (result.isSuccess()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{eventPostId}")
    public ResponseEntity<?> deletePostById(@PathVariable int eventPostId) {

        if (postService.deleteById(eventPostId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
