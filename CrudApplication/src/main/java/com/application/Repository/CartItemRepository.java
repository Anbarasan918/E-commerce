package com.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.Pojo.CartItems;


@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {


}
