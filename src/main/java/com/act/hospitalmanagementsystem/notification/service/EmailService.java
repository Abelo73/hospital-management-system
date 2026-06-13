package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipientEmail());
            message.setSubject(notification.getTitle());
            message.setText(notification.getMessage());
            
            mailSender.send(message);
            
            log.info("Email sent successfully to {}", notification.getRecipientEmail());
        } catch (Exception e) {
            log.error("Error sending email to {}", notification.getRecipientEmail(), e);
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
