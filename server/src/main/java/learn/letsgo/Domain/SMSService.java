package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Models.SMSMessage;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    private final AppUserRepository userRepository;

    public SMSService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<String> createTextBody(SMSMessage message) {
        Result<String> result = validate(message);
        if (!result.isSuccess()) {
            return result;
        }
        int appUserId = message.getSavedEvent().getAppUserId();
        String userFullName = Helpers.getUserFullName(userRepository, appUserId);
        if (Helpers.isNullOrBlank(userFullName)) {
            result.addMessage(ResultType.NOT_FOUND, "User not found.");
            return result;
        }
        String eventName = message.getSavedEvent().getEvent().getEventName();
        String eventDetailUrl = message.getEventDetailUrl();
        String body = String.format("Let's Go! \uD83C\uDF9F️: %s wants to go to \'%s\' with you.\n"
                + " Check out the link below for more details.\n"
                + " %s", userFullName, eventName, eventDetailUrl);
        result.setPayload(body);
        return result;
    }

    private Result<String> validate(SMSMessage message) {
        Result<String> result = new Result<>();
        if (message == null) {
            result.addMessage(ResultType.INVALID, "Message cannot be null");
            return result;
        }
        if (message.getSavedEvent().getAppUserId() == 0) {
            result.addMessage(ResultType.INVALID, "Message must contain a valid user id");
        }
        if (Helpers.isNullOrBlank(message.getSavedEvent().getEvent().getEventName())) {
            result.addMessage(ResultType.INVALID, "Event name is required");
        }

        if (Helpers.isNullOrBlank(message.getRecipient())) {
            result.addMessage(ResultType.INVALID, "Recipient is required");
        }

        if (Helpers.isNullOrBlank(message.getEventDetailUrl())) {
            result.addMessage(ResultType.INVALID, "Link to event in app is required");
        }
        return result;
    }
}
