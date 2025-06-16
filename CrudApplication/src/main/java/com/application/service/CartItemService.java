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
import com.application.entity.Product;
import com.application.repository.CartItemRepository;
import com.application.repository.CartRepository;
import com.application.exception.CartItemNotFoundException;

@Service
@Transactional
public class CartItemService {
    
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    
    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartService cartService, ProductService productService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productService =productService;
        this.cartRepository = cartRepository;
    }
    
    public CartItem addCartItem(Long userId, CartItem cartItem) {
        Cart cart = cartService.getOrCreateCart(userId);
        
        cartItem.setCart(cart);
        cartItem.calculateTotal();
        
        CartItem savedItem = cartItemRepository.save(cartItem);
        
        // Update cart total
        cartService.updateCart(cart);
        
        return savedItem;
    }
    
    public CartItem updateCartItem(CartItem cartItem) {
        CartItem existingItem = cartItemRepository.findById(cartItem.getId())
            .orElseThrow(() -> new CartItemNotFoundException("Cart item not found: " + cartItem.getId()));
        
        existingItem.setQuantity(cartItem.getQuantity());
        existingItem.setPrice(cartItem.getPrice());
        existingItem.calculateTotal();
        
        CartItem updatedItem = cartItemRepository.save(existingItem);
        
        // Update cart total
        cartService.updateCart(existingItem.getCart());
        
        return updatedItem;
    }
    
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartItemNotFoundException("Cart item not found: " + cartItemId));
        
        Cart cart = cartItem.getCart();
        
        // Remove from collection (this will also delete from DB due to orphanRemoval)
        cart.removeCartItem(cartItem);
        cart.updateTotal();
        
     // Save the cart (deletion happens automatically)
        cartService.updateCart(cart);
    }
    
    @Transactional(readOnly = true)
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
    
    @Transactional(readOnly = true)
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Product> getCartItemByProductId(Long id) {
        return productService.getProductDetailsById(id);
    }

	public CartItem addOrUpdateCartItem(Long userId, Long productId, Long quantityToAdd, String productName, String brand, String category, BigDecimal price, 
            Long quantity) {
		
		Cart cart = findOrCreateCartByUserId(userId);
		Optional<CartItem> existingItemOpt = cartItemRepository
	            .findByCartIdAndProductId(cart.getId(), productId);

	    if (existingItemOpt.isPresent()) {
	        // Update existing item quantity
	        CartItem existingItem = existingItemOpt.get();
	        existingItem.setQuantity(existingItem.getQuantity() + quantityToAdd);
	        existingItem.setTotal(existingItem.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())));

	        cartItemRepository.save(existingItem);
	    } else {
            // Create new cart item
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProductId(productId);
            newCartItem.setProductName(productName);
            newCartItem.setBrand(brand);
            newCartItem.setCategory(category);
            newCartItem.setPrice(price);
            newCartItem.setQuantity(quantity);
            newCartItem.setTotal(price.multiply(BigDecimal.valueOf(quantity)));
            
            CartItem savedItem = cartItemRepository.save(newCartItem);
            updateCartTotal(cart);
            return savedItem;
        }
		return new CartItem();
	}
	
	 private Cart findOrCreateCartByUserId(Long userId) {
	        return cartRepository.findByUserId(userId)
	            .orElseGet(() -> {
	                Cart newCart = new Cart();
	                newCart.setUserId(userId);
	                newCart.setCartDate(LocalDateTime.now());
	                newCart.setTotalAmount(BigDecimal.ZERO);
	                return cartRepository.save(newCart);
	            });
	    }
	 
	  private void updateCartTotal(Cart cart) {
	        BigDecimal total = cartItemRepository.findByCartId(cart.getId())
	            .stream()
	            .map(CartItem::getTotal)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	        
	        cart.setTotalAmount(total);
	        cartRepository.save(cart);
	    }
}