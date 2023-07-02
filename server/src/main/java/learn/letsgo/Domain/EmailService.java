package learn.letsgo.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(List<String> recipients, String subject, String body) {
        String[] to = new String[recipients.size()];
        recipients.toArray(to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        //TODO helper function to make body with event params
        message.setText(body);

        mailSender.send(message);
    }
}
