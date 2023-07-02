package learn.letsgo.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import learn.letsgo.Domain.EmailService;
import learn.letsgo.Domain.SMSService;
import learn.letsgo.Models.EmailMessage;
import learn.letsgo.Models.SMSMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class SendMessageController {

    private final EmailService emailService;
    private final SMSService  smsService;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.phone.number}")
    private String twilioPhoneFrom;

    public SendMessageController(EmailService emailService, SMSService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostMapping("/sendSMS")
    public ResponseEntity<String> sendSMS(@RequestBody SMSMessage message) {

        Twilio.init(twilioAccountSid, twilioAuthToken);
        String body = smsService.createTextBody(message);
        Message.creator(new PhoneNumber(message.getRecipient()),
                new PhoneNumber(twilioPhoneFrom), body).create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailMessage emailMsg) {
        emailService.sendEmail(emailMsg);
        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}
