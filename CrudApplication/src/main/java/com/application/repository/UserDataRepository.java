package com.application.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.application.Utils.Utils;
import com.application.entity.UserData;

@Repository
public class UserDataRepository {

	@Autowired
	private static JdbcTemplate jdbcTemplate;

	public HashMap<String, Object> save(UserData request) {

		HashMap<String, Object> saveRegisterUserResponse = new HashMap<String, Object>();
		try {
			String query = "INSERT INTO ak_user_registration (user_id,name, emailId,password, mobile_number, gender, age) VALUES (?, ?, ?, ?, ?, ?,?)";

			System.out.println("Executing SQL: " + query);
			System.out.println("Values: " + request.getName() + ", " + request.getEmailId() + ", "
					+ request.getMobileNumber());

			jdbcTemplate.update(query, String.valueOf(request.getUserId()), String.valueOf(request.getName()), String.valueOf(request.getEmailId()),
					String.valueOf(request.getConfirmPassword()),String.valueOf(request.getMobileNumber()), request.getGender(), request.getAge());

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
			String query = "select password from ak_user_registration where EmailId=?";

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
