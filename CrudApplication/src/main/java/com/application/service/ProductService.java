package com.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Pojo.Category;
import com.application.Pojo.Product;
import com.application.Repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public HashMap<String, Object> productSave(Product request) {

		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		try {
			productRepository.save(request);
			productDetails.put("Status", "SUCCESS");
			productDetails.put("Message", "Data received and saved successfully");

		} catch (Exception e) {
			System.out.println("error at save method - " + e.getMessage());
			productDetails.put("Status", "Error");
			productDetails.put("Message", e.getMessage());
			return productDetails;
		}

		return productDetails;
	}

	public List<Product> retrieveProducts() {

		List<Product> details = new ArrayList<>();
		try {
			details = productRepository.findAll();
		} catch (Exception e) {
			System.out.println("Error at retrieveProducts  --> " + e.getMessage());
		}
		return details;
	}

	public Optional<Product> getProductDetailsById(Long id) {
		Optional<Product> response = Optional.empty();
		try {
			response = productRepository.findById(id);
		} catch (Exception e) {
			System.out.println("error at getCatagoryDetailsById " + e.getMessage());
		}
		return response;
	}
	
public HashMap<String, Object> deleteProduct(Long id) {
		
		HashMap<String, Object> deleteCategoriesList = new HashMap<String, Object>();
		try {
			productRepository.deleteById(id);
			deleteCategoriesList.put("Status", "SUCCESS");
			deleteCategoriesList.put("Message", "Data deleted successfully");
		} catch(Exception e) {
			System.out.println("Error at deleteCategories method " + e.getMessage());
		}
		return deleteCategoriesList;
	}
}
