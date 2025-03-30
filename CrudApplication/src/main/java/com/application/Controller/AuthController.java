package com.application.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.Pojo.User;
import com.application.Pojo.UserData;
import com.application.Repository.UserDataRepo;
import com.application.Repository.UserDataRepository;
import com.application.Repository.UserRepo;
import com.application.Utils.Utils;
import com.application.security.JWTUtil;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserDataRepository userDataRepository;

	@Autowired private UserDataRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    
	@PostMapping("/registrationDetails")
	 public Map<String, Object> registerHandler(@RequestBody UserData user){
        String encodedPass = passwordEncoder.encode(user.getConfirmPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail_id());
        return Collections.singletonMap("jwt-token", token);
    }

	@PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody User body){
        try {
        	HashMap<String, Object> map = new HashMap<>();
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());
            map.put("jwtToken", token);
            map.put("Status", "SUCCESS");
            return map;
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
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
