package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.application.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCartId(Long cartId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.userId = :userId")
    List<CartItem> findByUserId(@Param("userId") Long userId);
    
    void deleteByCartId(Long cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId and ci.productId = :productId")
	Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long userId, @Param("productId") Long productId);
}