package com.application.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.application.service.CartItemService;
import com.application.service.CartService;
import com.application.service.CategoryService;
import com.application.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.Cart;
import com.application.Pojo.CartItems;
import com.application.Pojo.Category;
import com.application.Pojo.Product;

@RestController
@RequestMapping("/api/product")
public class EcommerceController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartService cartService;

	@PostMapping("/productCreation")
	public ResponseEntity<List<Product>>  productCreation(@RequestBody Product request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = productService.productSave(request);
			if("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
				return new ResponseEntity<List<Product>>(HttpStatus.OK);
			}else {
				return new ResponseEntity<List<Product>>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return new ResponseEntity<List<Product>>(HttpStatus.GONE);
	}

	@PostMapping("/catagoryCreation")
	public ResponseEntity<List<Category>> catagoryCreation(@RequestBody Category request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = categoryService.categorySave(request);
			if("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
				return new ResponseEntity<List<Category>>(HttpStatus.OK);
			}else {
				return new ResponseEntity<List<Category>>(HttpStatus.NOT_ACCEPTABLE);
			}
//			return new ResponseEntity<List<Category>>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return new ResponseEntity<List<Category>>(HttpStatus.GONE);
	}

	@GetMapping("/productListing")
	public ResponseEntity<List<Product>> retriveProductListingDetails() {
		List<Product> response = new ArrayList<>();
		try {
			response = productService.retrieveProducts();
			return new ResponseEntity<List<Product>>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return new ResponseEntity<List<Product>>(HttpStatus.GONE);
	}

	@GetMapping("/catagoriesList")
	public ResponseEntity<List<Category>> retriveCatagoriesAsList() {
		List<Category> response = new ArrayList<>();
		try {
			response = categoryService.retrieveCatagories();
			return new ResponseEntity<List<Category>>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return new ResponseEntity<List<Category>>(HttpStatus.GONE);
	}

	@GetMapping("/editCategoryDetail/{id}")
	public ResponseEntity<Optional<Category>> editCategoryDetail(@PathVariable Long id) {
		Optional<Category> response = Optional.empty();
		try {
			response = categoryService.getCatagoryDetailsById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.GONE);

	}

	@DeleteMapping("/deleteCategory/{id}")
	public ResponseEntity<Optional<Category>> delteCategoryDetail(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = categoryService.deleteCatagory(id);
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.GONE);
	}

	@GetMapping("/editProduct/{id}")
	public ResponseEntity<Optional<Product>> editProductDetail(@PathVariable Long id) {
		Optional<Product> response = Optional.empty();
		try {
			response = productService.getProductDetailsById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.GONE);

	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<Optional<Product>> delteProductDetail(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = productService.deleteProduct(id);
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error at edit category method - " + e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.GONE);
	}

	@PostMapping("/addToCart")
	public ResponseEntity<List<Cart>> addToCartDetails(@RequestBody CartItems request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			List<Cart> cartDetailsList = cartService.retrieveCartDetails();

			if (!cartDetailsList.isEmpty()) {
				request.setId(null);
				
			} else {
				cartService.cartSave(null);
			}
			response = cartItemService.cartItemSave(request);
			if ("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
				return new ResponseEntity<List<Cart>>(HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Cart>>(HttpStatus.NOT_ACCEPTABLE);
			}

		} catch (Exception e) {
			System.out.println("Error at product Creation method - " + e.getMessage());
		}
		return new ResponseEntity<List<Cart>>(HttpStatus.GONE);
	}
	
}
