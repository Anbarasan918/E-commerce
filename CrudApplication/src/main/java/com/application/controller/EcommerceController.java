package com.application.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.application.service.CartItemService;
import com.application.service.CartService;
import com.application.service.CategoryService;
import com.application.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

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

import com.application.Utils.Utils;
import com.application.entity.Cart;
import com.application.entity.CartItem;
import com.application.entity.Category;
import com.application.entity.Product;
import com.application.security.JWTUtil;

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
	public ResponseEntity<List<Product>> productCreation(@RequestBody Product request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			response = productService.productSave(request);
			if ("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
				return new ResponseEntity<List<Product>>(HttpStatus.OK);
			} else {
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
			if ("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
				return new ResponseEntity<List<Category>>(HttpStatus.OK);
			} else {
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

//	@PostMapping("/addToCart")
//	public ResponseEntity<List<Cart>> addToCartDetails(@RequestBody CartItems request, HttpServletRequest httpRequest) {
//		HashMap<String, Object> response = new HashMap<>();
//		List<CartItems> cartItemResponse = new ArrayList<>();
//		try {
//
//			Long userId = JWTUtil.extractIdFromToken(httpRequest);
//			List<Cart> cartDetailsList = cartService.retrieveCartDetails(userId);
//
//			Cart currentCart;
//			if (cartDetailsList.isEmpty()) {
//				currentCart = new Cart();
//				currentCart.setUser_id(userId);
//				currentCart.setCart_date(LocalDateTime.now());
//				currentCart.setPayment_method("PENDING");
//				currentCart.setTotal_amount(BigDecimal.valueOf(Double.valueOf(String.valueOf("0"))));
//				cartService.cartSave(currentCart);
//
//				// Retrieve the saved cart to get the generated ID
//				cartDetailsList = cartService.retrieveCartDetails(userId);
//				currentCart = cartDetailsList.get(0);
//			} else {
//				currentCart = cartDetailsList.get(0);
//			}
//
//			// Set the cart object instead of cart_id
//			request.setCart(currentCart);
//			setProductId(request);
//			request.setQuantity(Long.valueOf(String.valueOf("1")));
//			request.setTotal(BigDecimal.valueOf(Double.valueOf(String.valueOf(request.getPrice()))));
//			Long productId = Long.valueOf(String.valueOf(request.getId()));
//			Optional<Product> productResponse = productService.getProductDetailsById(productId);
//			Utils.performPriceandQuantityCalc(request, productResponse);
//			response = cartItemService.cartItemSave(request);
//			cartItemResponse = cartItemService.retrieveCartItemDetails(currentCart.getId());
//			double totalAmount = 0;
//			for (CartItems cartres : cartItemResponse) {
//				totalAmount = totalAmount + Double.valueOf(String.valueOf(cartres.getPrice()));
//			}
//			currentCart.setCart_date(LocalDateTime.now());
//			currentCart.setTotal_amount(BigDecimal.valueOf(totalAmount));
//			cartService.cartSave(currentCart);
//
//			if ("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
//				return new ResponseEntity<List<Cart>>(HttpStatus.OK);
//			} else {
//				return new ResponseEntity<List<Cart>>(HttpStatus.NOT_ACCEPTABLE);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error at product Creation method - " + e.getMessage());
//		}
//		return new ResponseEntity<List<Cart>>(HttpStatus.GONE);
//	}
//
//	private void setProductId(CartItems request) {
//		int productId = Integer.valueOf(String.valueOf(request.getId()));
//		request.setProduct_id(productId);
//	}
//
//	@GetMapping("/cartDetails")
//	public ResponseEntity<List<CartItems>> retriveCartDetails(HttpServletRequest httpRequest) {
//		List<Cart> cartResponse = null;
//		List<CartItems> cartItemResponse = null;
//		List<Cart> response = null;
//		try {
//			Long userId = JWTUtil.extractIdFromToken(httpRequest);
//			cartResponse = cartService.retrieveCartDetails(userId);
//			if (!cartResponse.isEmpty()) {
//	            Long cartId = cartResponse.get(0).getId();
//	            cartItemResponse = cartItemService.retrieveCartItemDetails(cartId);
//	        } else {
//	            cartItemResponse = new ArrayList<>(); // Return empty list if no cart exists
//	        }
//
//			return new ResponseEntity<List<CartItems>>(cartItemResponse, HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println("Error at product Creation method - " + e.getMessage());
//		}
//		return new ResponseEntity<List<CartItems>>(HttpStatus.GONE);
//	}
//
//	@DeleteMapping("/deleteCartItem/{id}")
//	public ResponseEntity<Optional<Category>> deleteCartItem(@PathVariable Long id, HttpServletRequest httpRequest) {
//		HashMap<String, Object> response = new HashMap<>();
//		List<CartItems> CartItemsList = new ArrayList<>();
//		try {
//			response = cartItemService.deleteCartItem(id);
////			CartItemsList = cartItemService.retrieveAllCartItem();
//
////			if (CartItemsList.isEmpty()) {
////				Long userId = JWTUtil.extractIdFromToken(httpRequest);
////				cartService.deleteCart(userId);
////			}
//			return new ResponseEntity(response, HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println("Error at edit category method - " + e.getMessage());
//		}
//		return new ResponseEntity<>(HttpStatus.GONE);
//	}
//
//	@PostMapping("/updateCartItem")
//	public ResponseEntity<List<CartItems>> updateCartItem(@RequestBody CartItems request,
//			HttpServletRequest httpRequest) {
//
//		HashMap<String, Object> response = new HashMap<>();
//		try {
//
//			Long userId = JWTUtil.extractIdFromToken(httpRequest);
//			List<Cart> cartDetailsList = cartService.retrieveCartDetails(userId);
//
//			if (!cartDetailsList.isEmpty()) {
//				Cart currentCart = cartDetailsList.get(0);
//				request.setCart(currentCart); // Set the cart object
//
//				Long productId = Long.valueOf(String.valueOf(request.getId()));
//				Optional<Product> productResponse = productService.getProductDetailsById(productId);
//				Utils.performPriceandQuantityCalc(request, productResponse);
//
//				response = cartItemService.cartItemSave(request);
//
//				if ("SUCCESS".equalsIgnoreCase(String.valueOf(response.get("Status")))) {
//					return new ResponseEntity<List<CartItems>>(HttpStatus.OK);
//				} else {
//					return new ResponseEntity<List<CartItems>>(HttpStatus.NOT_ACCEPTABLE);
//				}
//			} else {
//				return new ResponseEntity<List<CartItems>>(HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			System.out.println("Error at product Creation method - " + e.getMessage());
//		}
//		return new ResponseEntity<List<CartItems>>(HttpStatus.GONE);
//	}
}
