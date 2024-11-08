package com.application.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.application.Pojo.Category;
import com.application.Pojo.Product;

@Repository
public class EcommerceRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public HashMap<String, Object> productSave(Product request) {
		
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		try {
			String query = "INSERT INTO ak_product (product_name,brand, inventory,category,category_Description) VALUES (?, ?, ?, ?,?)";

			System.out.println("Executing SQL: " + query);
			System.out.println("Values: " + request.getProduct_name() + ", " + request.getBrand() + ", "
					+ request.getInventory()+ ", " + request.getCategory() + ", " + request.getCategory_Description());

			jdbcTemplate.update(query, String.valueOf(request.getProduct_name()), String.valueOf(request.getBrand()), request.getInventory(),
					request.getCategory(), request.getCategory_Description());

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
	
	
	public HashMap<String, Object> categorySave(Category request) {
		
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		try {
			String query = "INSERT INTO ak_category (id, name, description) VALUES (?, ?, ?)";

			System.out.println("Executing SQL: " + query);
			System.out.println("Values: " + request.getId() + ", " + request.getCategory_name() + ", "
					+ request.getDescription());

			jdbcTemplate.update(query, String.valueOf(request.getId()), String.valueOf(request.getCategory_name()), request.getDescription());

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


	public List<Map<String, Object>> retrieveProducts() {
		
		List<Map<String, Object>> details = new ArrayList<>();
		try {
			String query ="select * from ecommerce.ak_product";
			
			details = jdbcTemplate.queryForList(query); //  execute(query);
		} catch (Exception e) {
			System.out.println("Error at retrieveProducts  --> " + e.getMessage());
		}
		return details;
	}


	public List<Map<String, Object>> retrieveCatagories() {
		List<Map<String, Object>> categoriesList = new ArrayList<>();

		try {
			String query ="select id,name from ak_category";
			
			categoriesList= jdbcTemplate.queryForList(query);
			
			
		} catch (Exception e) {
			System.out.println("error at retrievecatagories" + e.getMessage());
		}
		return categoriesList;
	}
}
