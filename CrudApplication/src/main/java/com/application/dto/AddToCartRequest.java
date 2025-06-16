package com.application.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class AddToCartRequest {
    
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    
    @NotBlank(message = "Product name cannot be blank")
    private String productName;
    
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    
    @NotBlank(message = "Category cannot be blank")
    private String category;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
    
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;
    
    // Default constructor
    public AddToCartRequest() {}
    
    // Constructor with all fields
    public AddToCartRequest(Long productId, String productName, String brand, 
                           String category, Long quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity != null ? quantity : 1L; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    @Override
    public String toString() {
        return "AddToCartRequest{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}