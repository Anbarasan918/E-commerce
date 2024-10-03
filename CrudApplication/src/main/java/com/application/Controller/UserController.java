package com.application.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.UserData;
import com.application.Repository.UserDataRepository;
import com.application.Utils.Utils;


@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserDataRepository userDataRepository;

	@PostMapping("/registrationDetails")
	public Object registerUser(@RequestBody UserData request) {
		HashMap<String, Object> response = null;
		try {

			if (Utils.isNotEmpty(String.valueOf(request.getEmail_id()))
					&& Utils.isNotEmpty(String.valueOf(request.getMobile_number()))
					&& Utils.isNotEmpty(request.getNewPassword()) && Utils.isNotEmpty(request.getConfirmPassword())) {
				response = storeRegisterUserDetails(request);
			}

		} catch (Exception e) {
			System.out.println("Exception at RegisterUser Method" + e.getMessage());
		}

		return response;
	}

	@PostMapping("/loginConntroller")
	public Object loginUser(@RequestBody UserData request) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String emailId = String.valueOf(request.getEmail_id());
		String passWord = String.valueOf(request.getPassword());
		if (Utils.isNotEmpty(emailId) && Utils.isNotEmpty(passWord)) {
			response = validateUser(emailId, passWord);
		}else if(!Utils.isNotEmpty(emailId) && !Utils.isNotEmpty(passWord)) {
			response.put("Status", "ERROR");
			response.put("Message", "Please Provide Email and Password which is mandate to login");
		}

		return response;
	}

	private HashMap<String, Object> validateUser(String emailId, String passWord) {
		HashMap<String, Object> retriveRegisterUser = new HashMap<String, Object>();
		try {
			retriveRegisterUser = userDataRepository.retrive(emailId);

			String passWordFromDb = String.valueOf(retriveRegisterUser.get("password"));

			if (Utils.isNotEmpty(passWordFromDb) && Utils.isNotEmpty(passWord)
					&& validatePassword(passWord, passWordFromDb)) {
				retriveRegisterUser = new HashMap<>();
				retriveRegisterUser.put("Status", "SUCCESS");
				retriveRegisterUser.put("Message", "User Logged in Successfully");
			}else {
				retriveRegisterUser = new HashMap<>();
				retriveRegisterUser.put("Status", "ERROR");
				retriveRegisterUser.put("Message", "Please Provide Valid Email and Password");
			}
		} catch (Exception e) {
			System.out.println("Error at validateUser method - " + e.getMessage());
		}

		return retriveRegisterUser;
	}

	@SuppressWarnings("null")
	private HashMap<String, Object> storeRegisterUserDetails(UserData request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			System.out.println("Entered into storeRegisterUserDetails - ");
			Boolean passwordValidation = validatePassword(request.getNewPassword(), request.getConfirmPassword());

			if (passwordValidation) {
				response = userDataRepository.save(request);
			} else {
				response.put("Status", "ERROR");
				response.put("Message", "The provided password and Confirm password are differend");
//				return response;
			}

		} catch (Exception e) {
			System.out.println("error at storeRegisterUserDetails - " + e.getMessage());
		}
		System.out.println("Ended into storeRegisterUserDetails - ");

		return response;
	}

	private Boolean validatePassword(String newPassword, String confirmPassword) {
		 
		return newPassword.equals(confirmPassword);
	}
}
