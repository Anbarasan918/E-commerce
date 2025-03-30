package com.application.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.Pojo.CartItems;
import com.application.Repository.CartItemRepository;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	public HashMap<String, Object> cartItemSave(CartItems request) {
		HashMap<String, Object> cartDetails = new HashMap<String, Object>();
		try {
			cartItemRepository.save(request);
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
}
