package com.application.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.application.service.CategoryService;
import com.application.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.Category;
import com.application.Pojo.Product;

@RestController
@RequestMapping("/api")
public class EcommerceController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@PostMapping("/productCreation")
	public HashMap<String, Object> productCreation(@RequestBody Product request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= productService.productSave(request);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	@PostMapping("/catagoryCreation")
	public HashMap<String, Object> catagoryCreation(@RequestBody Category request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= categoryService.categorySave(request);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	
	@GetMapping("/productListing")
	public List<Product> retriveProductListingDetails() {
		List<Product> response = new ArrayList<>();
		try {
			response= productService.retrieveProducts();
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/catagoriesList")
	public List<Category> retriveCatagoriesAsList() {
		List<Category> response = new ArrayList<>();
		try {
			response= categoryService.retrieveCatagories();
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return response;
	}
	

	@GetMapping("/editCategoryDetail/{id}")
	public Optional<Category> editCategoryDetail(@PathVariable Long id) {
		Optional<Category> response = Optional.empty();
		try {
			response= categoryService.getCatagoryDetailsById(id);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return response;
		
	}
	
	@DeleteMapping("/deleteCategory/{id}")
	public HashMap<String, Object> delteCategoryDetail(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= categoryService.deleteCatagory(id);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/editProduct/{id}")
	public Optional<Product> editProductDetail(@PathVariable Long id) {
		Optional<Product> response = Optional.empty();
		try {
			response= productService.getProductDetailsById(id);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return response;
		
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public HashMap<String, Object> delteProductDetail(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response= productService.deleteProduct(id);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return response;
	}

}
