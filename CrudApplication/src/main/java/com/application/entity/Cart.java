package com.application.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ak_cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "User ID cannot be null")
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be non-negative")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @NotNull(message = "Cart date cannot be null")
    @Column(name = "cart_date", nullable = false)
    private LocalDateTime cartDate;
    
    @NotBlank(message = "Payment method cannot be blank")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference  // Parent side of reference
    private Set<CartItem> cartItems = new HashSet<>();
    

    // Method to recalculate total
    public void updateTotal() {
        this.totalAmount = cartItems.stream()
            .map(CartItem::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Constructors
    public Cart() {
        this.cartDate = LocalDateTime.now();
        this.paymentMethod = "PENDING";
        this.totalAmount = BigDecimal.ZERO;
    }
    
    public Cart(Long userId) {
        this();
        this.userId = userId;
    }
    
    // Business methods
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }
    
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }
    
    public void updateTotalAmount() {
        this.totalAmount = cartItems.stream()
            .map(CartItem::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public boolean isEmpty() {
        return cartItems == null || cartItems.isEmpty();
    }
    
    // Getters and Setters with proper naming
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public LocalDateTime getCartDate() { return cartDate; }
    public void setCartDate(LocalDateTime cartDate) { this.cartDate = cartDate; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public Set<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(Set<CartItem> cartItems) { this.cartItems = cartItems; }
}