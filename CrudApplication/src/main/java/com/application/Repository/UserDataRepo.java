package com.application.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.Pojo.UserData;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, Long>{
	
	Optional<UserData> findById(Long id);
	
}
