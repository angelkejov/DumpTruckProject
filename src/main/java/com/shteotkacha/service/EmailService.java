package com.shteotkacha.service;

import com.shteotkacha.entity.ServiceOrder;
import com.shteotkacha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Value("${app.email.verification-url}")
    private String verificationUrl;
    
    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Email Verification Code - Dump Truck Services");
        message.setText(String.format(
            "Hello %s,\n\n" +
            "Thank you for registering with Dump Truck Services!\n\n" +
            "Your verification code is: %s\n\n" +
            "Please enter this code on the verification page to complete your registration.\n\n" +
            "This code will expire in 24 hours.\n\n" +
            "If you did not register for this service, please ignore this email.\n\n" +
            "Best regards,\n" +
            "Dump Truck Services Team",
            user.getName(),
            user.getVerificationCode()
        ));
        
        mailSender.send(message);
    }
    
    public void sendOrderConfirmationEmail(User user, String orderDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Service Order Confirmation - Dump Truck Services");
        message.setText(String.format(
            "Hello %s,\n\n" +
            "Your service order has been received and is being processed.\n\n" +
            "Order Details:\n%s\n\n" +
            "We will contact you soon to confirm the details and schedule.\n\n" +
            "Thank you for choosing Dump Truck Services!\n\n" +
            "Best regards,\n" +
            "Dump Truck Services Team",
            user.getName(),
            orderDetails
        ));
        
        mailSender.send(message);
    }
    
    public void sendAdminOrderNotification(User user, ServiceOrder order, String contactPhoneNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(fromEmail); // Send to admin email (same as from email)
        message.setSubject("New Service Order Received - Dump Truck Services");
        message.setText(String.format(
            "A new service order has been submitted!\n\n" +
            "Client Information:\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n\n" +
            "Order Details:\n" +
            "Order ID: %d\n" +
            "Pickup Location: %s\n" +
            "Drop-off Location: %s\n" +
            "Preferred Date/Time: %s\n" +
            "Material Type: %s\n" +
            "Notes: %s\n" +
            "Status: %s\n\n" +
            "Please review and contact the client to confirm the order details.\n\n" +
            "Best regards,\n" +
            "Dump Truck Services System",
            user.getName(),
            user.getEmail(),
            contactPhoneNumber != null ? contactPhoneNumber : "Not provided",
            order.getId(),
            order.getPickupLocation(),
            order.getDropoffLocation(),
            order.getPreferredDateTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            order.getMaterialType(),
            order.getNotes() != null ? order.getNotes() : "None",
            order.getStatus().getDisplayName()
        ));
        
        mailSender.send(message);
    }
} 