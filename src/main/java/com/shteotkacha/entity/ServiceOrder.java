package com.shteotkacha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_orders")
public class ServiceOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank(message = "Pickup location is required")
    @Size(max = 500, message = "Pickup location must not exceed 500 characters")
    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;
    
    @NotBlank(message = "Drop-off location is required")
    @Size(max = 500, message = "Drop-off location must not exceed 500 characters")
    @Column(name = "dropoff_location", nullable = false)
    private String dropoffLocation;
    
    @NotNull(message = "Preferred date and time is required")
    @Column(name = "preferred_datetime", nullable = false)
    private LocalDateTime preferredDateTime;
    
    @NotBlank(message = "Material type is required")
    @Size(max = 100, message = "Material type must not exceed 100 characters")
    @Column(name = "material_type", nullable = false)
    private String materialType;
    
    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private OrderStatus status = OrderStatus.PENDING;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public ServiceOrder() {}
    
    public ServiceOrder(User user, String pickupLocation, String dropoffLocation, 
                       LocalDateTime preferredDateTime, String materialType, String notes) {
        this.user = user;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.preferredDateTime = preferredDateTime;
        this.materialType = materialType;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getPickupLocation() {
        return pickupLocation;
    }
    
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    
    public String getDropoffLocation() {
        return dropoffLocation;
    }
    
    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }
    
    public LocalDateTime getPreferredDateTime() {
        return preferredDateTime;
    }
    
    public void setPreferredDateTime(LocalDateTime preferredDateTime) {
        this.preferredDateTime = preferredDateTime;
    }
    
    public String getMaterialType() {
        return materialType;
    }
    
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public enum OrderStatus {
        PENDING("Pending"),
        CONFIRMED("Confirmed"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        OrderStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
} 