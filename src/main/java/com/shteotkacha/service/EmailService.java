package com.shteotkacha.service;

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
} 