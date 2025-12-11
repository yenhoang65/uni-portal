package io.spring.uni_portal.SendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(List<String> to, String subject, String content) {
        List<String> validEmails = to.stream()
                .filter(email -> email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                .toList();

        for (String email : validEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        }

    }
}

