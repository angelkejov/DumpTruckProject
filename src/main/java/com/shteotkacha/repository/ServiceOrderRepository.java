package com.shteotkacha.repository;

import com.shteotkacha.entity.ServiceOrder;
import com.shteotkacha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    
    List<ServiceOrder> findByUserOrderByCreatedAtDesc(User user);
    
    List<ServiceOrder> findByUserAndStatusOrderByCreatedAtDesc(User user, ServiceOrder.OrderStatus status);
} 