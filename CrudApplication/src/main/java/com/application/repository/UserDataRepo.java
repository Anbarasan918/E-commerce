package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.UserData;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, Long>{
	
	Optional<UserData> findById(Long id);
	
	Optional<UserData> findByEmailId(String email);
	
}
