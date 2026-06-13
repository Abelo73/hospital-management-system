package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMSService {

    public void sendSMS(Notification notification) {
        try {
            // TODO: Integrate with SMS gateway (e.g., Twilio, Africa's Talking)
            log.info("SMS sent to {}: {}", notification.getRecipientPhone(), notification.getMessage());
            
            // Placeholder for SMS gateway integration
            // Example with Twilio:
            // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            // Message.creator(
            //     new PhoneNumber(notification.getRecipientPhone()),
            //     new PhoneNumber(TWILIO_PHONE_NUMBER),
            //     notification.getMessage()
            // ).create();
            
        } catch (Exception e) {
            log.error("Error sending SMS to {}", notification.getRecipientPhone(), e);
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }
}
