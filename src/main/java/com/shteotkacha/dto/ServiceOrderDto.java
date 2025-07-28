package com.shteotkacha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ServiceOrderDto {
    
    @NotBlank(message = "Pickup location is required")
    @Size(max = 500, message = "Pickup location must not exceed 500 characters")
    private String pickupLocation;
    
    @NotBlank(message = "Drop-off location is required")
    @Size(max = 500, message = "Drop-off location must not exceed 500 characters")
    private String dropoffLocation;
    
    @NotNull(message = "Preferred date and time is required")
    private LocalDateTime preferredDateTime;
    
    @NotBlank(message = "Material type is required")
    @Size(max = 100, message = "Material type must not exceed 100 characters")
    private String materialType;
    
    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
    
    // Constructors
    public ServiceOrderDto() {}
    
    public ServiceOrderDto(String pickupLocation, String dropoffLocation, 
                          LocalDateTime preferredDateTime, String materialType, String notes) {
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.preferredDateTime = preferredDateTime;
        this.materialType = materialType;
        this.notes = notes;
    }
    
    // Getters and Setters
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
} 