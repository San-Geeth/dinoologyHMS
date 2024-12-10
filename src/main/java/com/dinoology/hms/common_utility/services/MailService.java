package com.dinoology.hms.common_utility.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
* Author: sangeethnawa
* Created: 12/10/2024 7:30 PM
*/
@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates HTML content

        mailSender.send(message);
    }

    /*
    TODO: Enhance below email template
        Need to be mobile responsive and must have proper content
    */
    public String buildPasswordResetEmail(String buttonUrl) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                ".container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; }" +
                ".heading { font-size: 24px; font-weight: bold; color: #333333; margin-bottom: 20px; }" +
                ".description { font-size: 16px; color: #666666; margin-bottom: 20px; }" +
                ".button { display: inline-block; padding: 10px 20px; background-color: #007bff; color: #ffffff; text-decoration: none; border-radius: 5px; }" +
                ".button:hover { background-color: #0056b3; }" +
                "@media only screen and (max-width: 600px) {" +
                "  .container { padding: 10px; }" +
                "  .heading { font-size: 20px; }" +
                "  .description { font-size: 14px; }" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<div class=\"heading\">" + "Welcome to HMS!" + "</div>" +
                "<div class=\"description\">" + "Please click on below button to reset your password" + "</div>" +
                "<a href=\"" + buttonUrl + "\" class=\"button\">" + "Reset Password" + "</a>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

}
