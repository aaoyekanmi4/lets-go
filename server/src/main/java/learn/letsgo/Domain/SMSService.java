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

    public String createTextBody(SMSMessage message) {
        int appUserId = message.getSavedEvent().getAppUserId();
        String userFullName = Helpers.getUserFullName(userRepository, appUserId);
        String eventName = message.getSavedEvent().getEvent().getEventName();
        String eventDetailUrl = message.getEventDetailUrl();
        String body = String.format("Let's Go! \uD83C\uDF9F️: %s wants to go to \'%s\' with you.\n"
                + " Check out the link below for more details.\n"
                + " %s", userFullName, eventName, eventDetailUrl);
        return body;
    }
}
