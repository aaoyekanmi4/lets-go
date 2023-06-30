package learn.letsgo.Domain;

import learn.letsgo.Data.EventPostRepository;
import learn.letsgo.Models.EventPost;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventPostService {

    private final EventPostRepository postRepository;

    public EventPostService(EventPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<EventPost> findAllByEventId(int eventId) {
        return postRepository.findAllByEventId(eventId);
    }

    public EventPost findEventPostById(int eventPostId) {
        return postRepository.findById(eventPostId);
    }

    public Result<EventPost> create(EventPost eventPost) {
        Result<EventPost> result = validate(eventPost);
        if (!result.isSuccess()) {
            return result;
        }
        EventPost newEventPost = postRepository.create(eventPost);
        result.setPayload(newEventPost);
        return result;
    }

    public Result<EventPost> update(EventPost eventPost) {
        Result<EventPost> result = validate(eventPost);
        if (!result.isSuccess()) {
            return result;
        }

        if (eventPost.getPostId() <= 0) {
            result.addMessage( ResultType.INVALID, "eventPostId must be set for `update` operation");
            return result;
        }

        boolean eventPostDidUpdate = postRepository.update(eventPost);

        if (!eventPostDidUpdate) {
            String msg = String.format("eventPostId: %s, not found", eventPost.getPostId());
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    public boolean deleteById(int eventPostId) {
        return postRepository.deleteById(eventPostId);
    }

    private Result<EventPost> validate(EventPost eventPost) {
        Result<EventPost> result = new Result<>();
        if (eventPost == null) {
            result.addMessage(ResultType.INVALID, "Event post cannot be null");
            return result;
        }

        if (Validations.isNullOrBlank(eventPost.getPostBody())) {
            result.addMessage(ResultType.INVALID, "Post Body is required");
        }

        if (Validations.isNullOrBlank(eventPost.getAuthor())) {
            result.addMessage(ResultType.INVALID, "Author is required");
        }

        return result;
    }
}
