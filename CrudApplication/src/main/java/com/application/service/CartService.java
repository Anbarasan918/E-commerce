package com.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.application.entity.Cart;
import com.application.entity.CartItem;
import com.application.repository.CartRepository;
import com.application.exception.CartNotFoundException;

@Service
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    
    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    
    public Cart createCart(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        Cart cart = new Cart(userId);
        return cartRepository.save(cart);
    }
    
    @Transactional(readOnly = true)
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Cart getCartByUserIdWithItems(Long userId) {
        return cartRepository.findByUserIdWithItems(userId)
            .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));
    }
    
    public Cart getOrCreateCart(Long userId) {
        return getCartByUserId(userId)
            .orElseGet(() -> createCart(userId));
    }
    
    public Cart updateCart(Cart cart) {
        cart.setCartDate(LocalDateTime.now());
        cart.updateTotalAmount();
        return cartRepository.save(cart);
    }
    
    public void deleteCartByUserId(Long userId) {
        if (!cartRepository.existsByUserId(userId)) {
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }
        cartRepository.deleteByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public boolean hasCart(Long userId) {
        return cartRepository.existsByUserId(userId);
    }
}