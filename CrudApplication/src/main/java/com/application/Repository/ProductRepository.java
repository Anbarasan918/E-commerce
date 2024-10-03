package com.application.Repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.application.Pojo.Product;

@Repository
public class ProductRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public HashMap<String, Object> save(Product request) {
		
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		try {
			String query = "INSERT INTO ak_product (product_name,brand, inventory,category) VALUES (?, ?, ?, ?)";

			System.out.println("Executing SQL: " + query);
			System.out.println("Values: " + request.getProduct_name() + ", " + request.getBrand() + ", "
					+ request.getInventory()+ ", " + request.getCategory());

			jdbcTemplate.update(query, String.valueOf(request.getProduct_name()), String.valueOf(request.getBrand()), request.getInventory(),
					request.getCategory());

		} catch (InvalidResultSetAccessException e) {
			System.out.println("error at save method - " + e.getMessage());
			productDetails.put("Status", "Error");
			productDetails.put("Message", e.getMessage());
			return productDetails;
		} catch (DataAccessException e) {
			System.out.println("error at save method - " + e.getMessage());
			productDetails.put("Status", "Error");
			productDetails.put("Message", e.getMessage());
			return productDetails;
		}
		productDetails.put("Status", "SUCCESS");
		productDetails.put("Message", "Data received and saved successfully");
		return productDetails;
	}
}
