package iam.domain.port.out;

public interface EmailSender {

    void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody);
}
