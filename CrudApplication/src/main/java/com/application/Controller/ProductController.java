package com.application.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.Product;
import com.application.Repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/productCreation")
	public HashMap<String, Object> productCreation(@RequestBody Product request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= productRepository.save(request);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
}
