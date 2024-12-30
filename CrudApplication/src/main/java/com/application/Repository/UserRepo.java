package com.application.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Pojo.User;

public interface UserRepo extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
	
}
