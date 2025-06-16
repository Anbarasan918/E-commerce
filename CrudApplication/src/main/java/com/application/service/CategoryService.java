package com.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.entity.Category;
import com.application.repository.CategoryRepository;

@Service
public class CategoryService {
		
	@Autowired
	private CategoryRepository categoryRepository;
	
	public HashMap<String, Object> categorySave(Category request) {
		HashMap<String, Object> productDetails = new HashMap<String, Object>();
		try {
			categoryRepository.save(request);
			productDetails.put("Status", "SUCCESS");
			productDetails.put("Message", "Data received and saved successfully");
			return productDetails;

		} catch (Exception e) {
			System.out.println("error at save method - " + e.getMessage());
			productDetails.put("Status", "Error");
			productDetails.put("Message", e.getMessage());
			return productDetails;
		}
		
	}
	
	public List<Category> retrieveCatagories() {
		List<Category> categoriesList = new ArrayList<>();

		try {
			categoriesList = categoryRepository.findAll();
		} catch (Exception e) {
			System.out.println("error at retrievecatagories" + e.getMessage());
		}
		return categoriesList;
	}

	public HashMap<String, Object> deleteCatagory(Long id) {
		
		HashMap<String, Object> deleteCategoriesList = new HashMap<String, Object>();
		try {
			categoryRepository.deleteById(id);
			deleteCategoriesList.put("Status", "SUCCESS");
			deleteCategoriesList.put("Message", "Data deleted successfully");
		} catch(Exception e) {
			System.out.println("Error at deleteCategories method " + e.getMessage());
		}
		return deleteCategoriesList;
	}
	
	
	public Optional<Category> getCatagoryDetailsById(Long id) {
		Optional<Category> response = Optional.empty();
		try {
			response = categoryRepository.findById(id);
		} catch (Exception e) {
			System.out.println("error at getCatagoryDetailsById " + e.getMessage());
		}
		return response;
	}


	
}
