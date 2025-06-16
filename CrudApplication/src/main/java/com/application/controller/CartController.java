package com.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.application.entity.Cart;
import com.application.entity.CartItem;
import com.application.entity.Product;
import com.application.service.CartService;
import com.application.service.CartItemService;
import com.application.dto.ApiResponse;
import com.application.dto.AddToCartRequest;
import com.application.dto.UpdateCartItemRequest;
import com.application.security.JWTUtil;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartService cartService;
    private final CartItemService cartItemService;
    
    @Autowired
    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartItem>> addToCart(
            @Valid @RequestBody AddToCartRequest request, 
            HttpServletRequest httpRequest) {
        
        try {
            Long userId = JWTUtil.extractIdFromToken(httpRequest);
            Long productId = Long.valueOf(String.valueOf(request.getProductId()));
            Long quantityToAdd = Long.valueOf(String.valueOf(request.getQuantity()));
//            Long product = Long.valueOf(String.valueOf(request.get));
           
            CartItem savedItem = cartItemService.addOrUpdateCartItem(userId, 
            		productId, 
            		quantityToAdd,
            		request.getProductName(),
                    request.getBrand(),
                    request.getCategory(),
                    request.getPrice(),
                   Long.valueOf(String.valueOf(request.getQuantity() != null ? request.getQuantity() : 1)));

            
            return ResponseEntity.ok(
                ApiResponse.success("Item added to cart successfully", savedItem)
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to add item to cart: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCartItems(HttpServletRequest httpRequest) {
        try {
            Long userId = JWTUtil.extractIdFromToken(httpRequest);
            List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);
            
            return ResponseEntity.ok(
                ApiResponse.success("Cart items retrieved successfully", cartItems)
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to retrieve cart items: " + e.getMessage())
            );
        }
    }
    
    @PutMapping("/items/{id}")
    public ResponseEntity<ApiResponse<CartItem>> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequest request) {
        
        try {
            CartItem cartItem = new CartItem();
            cartItem.setId(id);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(request.getPrice());
            
            CartItem updatedItem = cartItemService.updateCartItem(cartItem);
            
            return ResponseEntity.ok(
                ApiResponse.success("Cart item updated successfully", updatedItem)
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to update cart item: " + e.getMessage())
            );
        }
    }
    
    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long id) {
        try { 
            cartItemService.deleteCartItem(id);
            
            return ResponseEntity.ok(
                ApiResponse.success("Cart item deleted successfully", null)
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to delete cart item: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Cart>> getCartSummary(HttpServletRequest httpRequest) {
        try {
            Long userId = JWTUtil.extractIdFromToken(httpRequest);
            Cart cart = cartService.getCartByUserIdWithItems(userId);
            
            return ResponseEntity.ok(
                ApiResponse.success("Cart summary retrieved successfully", cart)
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.error("Failed to retrieve cart summary: " + e.getMessage())
            );
        }
    }
}