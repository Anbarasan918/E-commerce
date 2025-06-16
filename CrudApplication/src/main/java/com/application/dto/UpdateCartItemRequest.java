package com.application.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class UpdateCartItemRequest {
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;
    
    // Default constructor
    public UpdateCartItemRequest() {}
    
    // Constructor
    public UpdateCartItemRequest(Long quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    @Override
    public String toString() {
        return "UpdateCartItemRequest{" +
                "quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}