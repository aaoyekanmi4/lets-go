package learn.letsgo.Domain;

import learn.letsgo.Data.AppUserRepository;
import learn.letsgo.Models.AppUser;
import learn.letsgo.Models.EmailMessage;
import learn.letsgo.Models.Event;
import learn.letsgo.Models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final AppUserRepository userRepository;

    private static final String DATE_TIME_FORMATTER = "EEE • MMM dd • hh:mm a";

    public EmailService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<Void> sendEmail(EmailMessage emailMessage) {
        Result<Void> result = validate(emailMessage);
        if (!result.isSuccess()) {
            return result;
        }
        String templateStr = readFile("./email_templates/inviteTemplate.html");

        Event event = emailMessage.getSavedEvent().getEvent();

        int appUserId = emailMessage.getSavedEvent().getAppUserId();

        String imageUrl = event.getImageUrl();

        String eventTitle = event.getEventName();

        String formattedDateTime = formatDateTime(event.getDateTime());

        String venueName = event.getVenue().getVenueName();

        String formattedFullAddress = formatFullAddress(event.getVenue());

        String userFullName = Helpers.getUserFullName(userRepository, appUserId);
        if (Helpers.isNullOrBlank(userFullName)) {
            result.addMessage(ResultType.NOT_FOUND, "User not found.");
            return result;
        }

        String eventDetailUrl = emailMessage.getEventDetailUrl();
        System.out.println(eventDetailUrl);

        String body = String.format(templateStr, imageUrl, eventTitle, formattedDateTime,
                venueName, formattedFullAddress, userFullName, eventDetailUrl);

        String[] to = convertRecipientsToStrArr(emailMessage.getRecipients());

        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setSubject(String.format("A friend is inviting you to: %s", eventTitle));
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String readFile(String pathName) {
        String emailTemplate = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                emailTemplate += line;
            }
        } catch (IOException ex) {
            // will skip and empty String will be returned instead
        }
        return emailTemplate;
    }

    private String formatDateTime(LocalDateTime eventDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
        String formattedDateTime = eventDate.format(formatter);
        return formattedDateTime;
    }

    private String formatFullAddress(Venue venue) {
        return String.format("%s, %s, %s %s", venue.getAddress(), venue.getCity(),
                venue.getState(), venue.getZipCode());
    }

    private String[] convertRecipientsToStrArr(List<String> recipients) {
        String[] to = new String[recipients.size()];
        recipients.toArray(to);
        return to;
    }

    private Result<Void> validate(EmailMessage emailMessage) {
        Result<Void> result = new Result<>();
        if (emailMessage == null) {
            result.addMessage(ResultType.INVALID, "Message cannot be null");
            return result;
        }
        if (emailMessage.getSavedEvent() == null) {
            result.addMessage(ResultType.INVALID, "Event for message cannot be null");
        }
        if (Helpers.isNullOrBlank(emailMessage.getEventDetailUrl())) {
            result.addMessage(ResultType.INVALID, "Link to app saved event cannot be null");
        }
        if (emailMessage.getRecipients() == null || emailMessage.getRecipients().size() == 0) {
            result.addMessage(ResultType.INVALID, "Recipients are required");
        }
        return result;
    }
}
