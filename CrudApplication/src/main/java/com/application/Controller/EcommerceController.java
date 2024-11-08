package com.application.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.Category;
import com.application.Pojo.Product;
import com.application.Repository.EcommerceRepository;

@RestController
@RequestMapping("/api")
public class EcommerceController {
	
	@Autowired
	private EcommerceRepository ecommerceRepository;

	@PostMapping("/productCreation")
	public HashMap<String, Object> productCreation(@RequestBody Product request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= ecommerceRepository.productSave(request);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	@PostMapping("/catagoryCreation")
	public HashMap<String, Object> catagoryCreation(@RequestBody Category request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= ecommerceRepository.categorySave(request);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	
	@GetMapping("/productListing")
	public List<Map<String, Object>> retriveProductListingDetails() {
		List<Map<String, Object>> response = new ArrayList<>();
		try {
			response= ecommerceRepository.retrieveProducts();
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/catagoriesList")
	public List<Map<String, Object>> retriveCatagoriesAsList() {
		List<Map<String, Object>> response = new ArrayList<>();
		try {
			response= ecommerceRepository.retrieveCatagories();
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}

}
