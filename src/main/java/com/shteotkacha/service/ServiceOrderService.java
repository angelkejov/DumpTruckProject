package com.shteotkacha.service;

import com.shteotkacha.entity.ServiceOrder;
import com.shteotkacha.entity.User;
import com.shteotkacha.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOrderService {
    
    @Autowired
    private ServiceOrderRepository serviceOrderRepository;
    
    public ServiceOrder createOrder(User user, String pickupLocation, String dropoffLocation,
                                   LocalDateTime preferredDateTime, String materialType, String notes) {
        ServiceOrder order = new ServiceOrder(user, pickupLocation, dropoffLocation, 
                                            preferredDateTime, materialType, notes);
        return serviceOrderRepository.save(order);
    }
    
    public List<ServiceOrder> getUserOrders(User user) {
        return serviceOrderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<ServiceOrder> getUserOrdersByStatus(User user, ServiceOrder.OrderStatus status) {
        return serviceOrderRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status);
    }
    
    public ServiceOrder getOrderById(Long orderId) {
        return serviceOrderRepository.findById(orderId).orElse(null);
    }
    
    public ServiceOrder updateOrderStatus(Long orderId, ServiceOrder.OrderStatus status) {
        ServiceOrder order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            return serviceOrderRepository.save(order);
        }
        return null;
    }
    
    public boolean deleteOrder(Long orderId, User user) {
        ServiceOrder order = getOrderById(orderId);
        if (order != null && order.getUser().getId().equals(user.getId())) {
            serviceOrderRepository.delete(order);
            return true;
        }
        return false;
    }
} 