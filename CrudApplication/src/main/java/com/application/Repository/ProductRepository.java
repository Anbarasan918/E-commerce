package com.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Pojo.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
