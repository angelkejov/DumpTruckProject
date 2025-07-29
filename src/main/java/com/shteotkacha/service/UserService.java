package com.shteotkacha.service;

import com.shteotkacha.entity.User;
import com.shteotkacha.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }
    
    public User registerUser(String name, String email, String password) {
        logger.info("Starting user registration for email: {}", email);
        
        if (userRepository.existsByEmail(email)) {
            logger.warn("Email already registered: {}", email);
            throw new RuntimeException("Email already registered");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(false);
        
        // Generate verification code (6-digit number)
        String verificationCode = String.format("%06d", new Random().nextInt(1000000));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiry(LocalDateTime.now().plusHours(24));
        
        logger.info("Attempting to save user to database: {}", email);
        User savedUser = userRepository.save(user);
        logger.info("User saved successfully with ID: {}", savedUser.getId());
        
        // Send verification email
        try {
            emailService.sendVerificationEmail(savedUser);
            logger.info("Verification email sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send verification email: {}", e.getMessage());
        }
        
        return savedUser;
    }
    
    public boolean verifyEmail(String code) {
        User user = userRepository.findByVerificationCode(code)
                .orElse(null);
        
        if (user == null) {
            return false;
        }
        
        if (user.getVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
            return false;
        }
        
        user.setVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);
        userRepository.save(user);
        
        return true;
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
} 