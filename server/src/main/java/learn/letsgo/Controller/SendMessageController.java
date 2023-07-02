package learn.letsgo.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import learn.letsgo.Domain.EmailService;
import learn.letsgo.Domain.Result;
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
    public ResponseEntity<?> sendSMS(@RequestBody SMSMessage message) {

        Twilio.init(twilioAccountSid, twilioAuthToken);
        Result<String> result = smsService.createTextBody(message);
        if (result.isSuccess()) {
            Message.creator(new PhoneNumber(message.getRecipient()),
                    new PhoneNumber(twilioPhoneFrom), result.getPayload()).create();

            return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailMessage emailMsg) {
        Result<Void> result = emailService.sendEmail(emailMsg);
        if (result.isSuccess()) {
            return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }
}
