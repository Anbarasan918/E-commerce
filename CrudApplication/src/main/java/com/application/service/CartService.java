package com.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.Pojo.Cart;
import com.application.Repository.CartRepository;
import com.application.security.JWTUtil;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	public HashMap<String, Object> cartSave(Cart request) {
		HashMap<String, Object> cartDetails = new HashMap<String, Object>();
		try {
			cartRepository.save(request);
			cartDetails.put("Status", "SUCCESS");
			cartDetails.put("Message", "Data received and saved successfully");
			return cartDetails;

		} catch (Exception e) {
			System.out.println("error at save method - " + e.getMessage());
			cartDetails.put("Status", "Error");
			cartDetails.put("Message", e.getMessage());
			return cartDetails;
		}

	}
	
	
	public List<Cart> retrieveCartDetails() {
		List<Cart> cartDetailsList = new ArrayList<>();
		try {
			JWTUtil.getJwtTokenFromCookies(null, "jwtToken");
			cartDetailsList = cartRepository.findAllById(null);
			return cartDetailsList;

		} catch (Exception e) {
			System.out.println("error at save method - " + e.getMessage());
		}
		return cartDetailsList;

	}

}
