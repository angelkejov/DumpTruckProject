package com.shteotkacha.controller;

import com.shteotkacha.dto.ServiceOrderDto;
import com.shteotkacha.entity.ServiceOrder;
import com.shteotkacha.entity.User;
import com.shteotkacha.service.EmailService;
import com.shteotkacha.service.ServiceOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ServiceOrderController {
    
    @Autowired
    private ServiceOrderService serviceOrderService;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping("/order")
    public String orderPage(Model model) {
        model.addAttribute("serviceOrderDto", new ServiceOrderDto());
        return "service/order";
    }
    
    @PostMapping("/order")
    public String submitOrder(@Valid @ModelAttribute("serviceOrderDto") ServiceOrderDto orderDto,
                             BindingResult result, @AuthenticationPrincipal User user,
                             Model model, RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "service/order";
        }
        
        try {
            ServiceOrder order = serviceOrderService.createOrder(
                user, orderDto.getPickupLocation(), orderDto.getDropoffLocation(),
                orderDto.getPreferredDateTime(), orderDto.getMaterialType(), orderDto.getNotes()
            );
            
            // Send confirmation email
            String orderDetails = String.format(
                "Order ID: %d\n" +
                "Pickup: %s\n" +
                "Drop-off: %s\n" +
                "Date/Time: %s\n" +
                "Material: %s\n" +
                "Notes: %s",
                order.getId(),
                order.getPickupLocation(),
                order.getDropoffLocation(),
                order.getPreferredDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                order.getMaterialType(),
                order.getNotes() != null ? order.getNotes() : "None"
            );
            
            emailService.sendOrderConfirmationEmail(user, orderDetails);
            
            // Send admin notification
            emailService.sendAdminOrderNotification(user, order);
            
            redirectAttributes.addFlashAttribute("success", 
                "Service order submitted successfully! You will receive a confirmation email shortly.");
            return "redirect:/profile";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to submit order. Please try again.");
            return "service/order";
        }
    }
    
    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model) {
        List<ServiceOrder> userOrders = serviceOrderService.getUserOrders(user);
        List<ServiceOrder> pendingOrders = serviceOrderService.getUserOrdersByStatus(user, ServiceOrder.OrderStatus.PENDING);
        List<ServiceOrder> completedOrders = serviceOrderService.getUserOrdersByStatus(user, ServiceOrder.OrderStatus.COMPLETED);
        
        model.addAttribute("user", user);
        model.addAttribute("allOrders", userOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("completedOrders", completedOrders);
        
        return "user/profile";
    }
} 