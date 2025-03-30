package com.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Pojo.Cart;
import com.application.Pojo.CartItems;


public interface CartRepository extends JpaRepository<Cart, Long>{

	void save(CartItems request);

}
