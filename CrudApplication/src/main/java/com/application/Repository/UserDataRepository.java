package com.application.Repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.application.Pojo.UserData;
import com.application.Utils.Utils;

@Repository
public class UserDataRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public HashMap<String, Object> save(UserData request) {

		HashMap<String, Object> saveRegisterUserResponse = new HashMap<String, Object>();
		try {
			String query = "INSERT INTO user (user_id,name, email_id,password, mobile_number, gender, age) VALUES (?, ?, ?, ?, ?, ?,?)";

			System.out.println("Executing SQL: " + query);
			System.out.println("Values: " + request.getName() + ", " + request.getEmail_id() + ", "
					+ request.getMobile_number());

			jdbcTemplate.update(query, String.valueOf(request.getUser_id()), String.valueOf(request.getName()), String.valueOf(request.getEmail_id()),
					String.valueOf(request.getConfirmPassword()),String.valueOf(request.getMobile_number()), request.getGender(), request.getAge());

		} catch (InvalidResultSetAccessException e) {
			System.out.println("error at save method - " + e.getMessage());
			saveRegisterUserResponse.put("Status", "Error");
			saveRegisterUserResponse.put("Message", e.getMessage());
			return saveRegisterUserResponse;
		} catch (DataAccessException e) {
			System.out.println("error at save method - " + e.getMessage());
			saveRegisterUserResponse.put("Status", "Error");
			saveRegisterUserResponse.put("Message", e.getMessage());
			return saveRegisterUserResponse;
		}
		saveRegisterUserResponse.put("Status", "SUCCESS");
		saveRegisterUserResponse.put("Message", "Data received and saved successfully");
		return saveRegisterUserResponse;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public HashMap<String, Object> retrive(String emailId) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			String query = "select password from user where Email_id=?";

			System.out.println("Executing SQL: " + query);

			String password = jdbcTemplate.queryForObject(query, new Object[] { emailId }, String.class);
			System.out.println("Retrieved Password: " + password);

			if (Utils.isNotEmpty(password)) {
				response.put("password", password);
			}
		} catch (Exception e) {
			System.out.println("Error at retrive method - " + e.getMessage());
		}
		return response;
	}

}
