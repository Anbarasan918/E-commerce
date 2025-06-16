package com.application.Utils;

import java.math.BigDecimal;
import java.util.Optional;

import com.application.entity.CartItem;
import com.application.entity.Product;

public class Utils {
	
	public static Boolean isNotEmpty(String data) {
		Boolean isVaild = false;
		if (data != null && data != "null" && !data.isEmpty()) {
			isVaild = true;
		}
		return isVaild;
	}

	public static void performPriceandQuantityCalc(CartItem request, Optional<Product> productResponse) {
		try {
			String quantity = String.valueOf(request.getQuantity());
			String price = String.valueOf(productResponse.get().getPrice());
			double total = Double.valueOf(price)*Double.valueOf(quantity);
			BigDecimal totalAmount = BigDecimal.valueOf(total);
			request.setTotal(totalAmount);
		} catch (Exception e) {
			System.out.println("error at performPriceandQuantityCalc => " +e.getMessage());
		}
		
	}

	
}
