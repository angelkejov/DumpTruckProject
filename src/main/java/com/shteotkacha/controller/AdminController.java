package com.shteotkacha.controller;

import com.shteotkacha.entity.ServiceOrder;
import com.shteotkacha.entity.User;
import com.shteotkacha.service.ServiceOrderService;
import com.shteotkacha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private ServiceOrderService serviceOrderService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public String adminDashboard(Model model) {
        // Get statistics
        List<ServiceOrder> allOrders = serviceOrderService.getAllOrders();
        List<User> allUsers = userService.getAllUsers();
        
        // Calculate statistics
        long totalOrders = allOrders.size();
        long pendingOrders = allOrders.stream()
                .filter(order -> order.getStatus() == ServiceOrder.OrderStatus.PENDING)
                .count();
        long completedOrders = allOrders.stream()
                .filter(order -> order.getStatus() == ServiceOrder.OrderStatus.COMPLETED)
                .count();
        long totalCustomers = allUsers.size();
        
        // Recent orders (last 10)
        List<ServiceOrder> recentOrders = allOrders.stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .limit(10)
                .collect(Collectors.toList());
        
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("recentOrders", recentOrders);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/orders")
    public String adminOrders(@RequestParam(required = false) String status,
                             @RequestParam(required = false) String search,
                             Model model) {
        List<ServiceOrder> orders;
        
        if (status != null && !status.isEmpty()) {
            ServiceOrder.OrderStatus orderStatus = ServiceOrder.OrderStatus.valueOf(status.toUpperCase());
            orders = serviceOrderService.getAllOrdersByStatus(orderStatus);
        } else {
            orders = serviceOrderService.getAllOrders();
        }
        
        // Apply search filter if provided
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.toLowerCase();
            orders = orders.stream()
                    .filter(order -> 
                        order.getUser().getName().toLowerCase().contains(searchLower) ||
                        order.getUser().getEmail().toLowerCase().contains(searchLower) ||
                        order.getPickupLocation().toLowerCase().contains(searchLower) ||
                        order.getDropoffLocation().toLowerCase().contains(searchLower) ||
                        order.getMaterialType().toLowerCase().contains(searchLower)
                    )
                    .collect(Collectors.toList());
        }
        
        // Sort by creation date (newest first)
        orders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        
        model.addAttribute("orders", orders);
        model.addAttribute("statusFilter", status);
        model.addAttribute("searchQuery", search);
        model.addAttribute("orderStatuses", ServiceOrder.OrderStatus.values());
        
        return "admin/orders";
    }
    
    @PostMapping("/orders/{orderId}/status")
    public String updateOrderStatus(@PathVariable Long orderId,
                                   @RequestParam ServiceOrder.OrderStatus status,
                                   RedirectAttributes redirectAttributes) {
        try {
            ServiceOrder order = serviceOrderService.getOrderById(orderId);
            if (order != null) {
                order.setStatus(status);
                serviceOrderService.updateOrder(order);
                redirectAttributes.addFlashAttribute("success", 
                    "Order #" + orderId + " status updated to " + status.getDisplayName());
            } else {
                redirectAttributes.addFlashAttribute("error", "Order not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update order status");
        }
        
        return "redirect:/admin/orders";
    }
    
    @GetMapping("/customers")
    public String adminCustomers(@RequestParam(required = false) String search,
                                Model model) {
        List<User> customers = userService.getAllUsers();
        
        // Apply search filter if provided
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.toLowerCase();
            customers = customers.stream()
                    .filter(user -> 
                        user.getName().toLowerCase().contains(searchLower) ||
                        user.getEmail().toLowerCase().contains(searchLower) ||
                        (user.getPhoneNumber() != null && user.getPhoneNumber().toLowerCase().contains(searchLower))
                    )
                    .collect(Collectors.toList());
        }
        
        // Sort by registration date (newest first)
        customers.sort((u1, u2) -> u2.getCreatedAt().compareTo(u1.getCreatedAt()));
        
        model.addAttribute("customers", customers);
        model.addAttribute("searchQuery", search);
        
        return "admin/customers";
    }
    
    @GetMapping("/customers/{customerId}")
    public String customerDetails(@PathVariable Long customerId, Model model) {
        User customer = userService.findById(customerId);
        if (customer == null) {
            return "redirect:/admin/customers";
        }
        
        List<ServiceOrder> customerOrders = serviceOrderService.getUserOrders(customer);
        
        model.addAttribute("customer", customer);
        model.addAttribute("customerOrders", customerOrders);
        
        return "admin/customer-details";
    }
    
    @GetMapping("/stats")
    public String adminStats(Model model) {
        List<ServiceOrder> allOrders = serviceOrderService.getAllOrders();
        List<User> allUsers = userService.getAllUsers();
        
        // Order statistics by status
        Map<ServiceOrder.OrderStatus, Long> ordersByStatus = allOrders.stream()
                .collect(Collectors.groupingBy(ServiceOrder::getStatus, Collectors.counting()));
        
        // Material type statistics
        Map<String, Long> ordersByMaterial = allOrders.stream()
                .collect(Collectors.groupingBy(ServiceOrder::getMaterialType, Collectors.counting()));
        
        // Monthly order statistics (last 6 months)
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        List<ServiceOrder> recentOrders = allOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(sixMonthsAgo))
                .collect(Collectors.toList());
        
        model.addAttribute("ordersByStatus", ordersByStatus);
        model.addAttribute("ordersByMaterial", ordersByMaterial);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("totalOrders", allOrders.size());
        model.addAttribute("totalCustomers", allUsers.size());
        
        return "admin/stats";
    }
} 