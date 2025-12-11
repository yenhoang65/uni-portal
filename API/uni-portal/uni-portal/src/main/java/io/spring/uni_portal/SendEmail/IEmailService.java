package io.spring.uni_portal.SendEmail;

import java.util.List;

public interface IEmailService {
    void sendEmail(List<String> to, String subject, String content);

}
