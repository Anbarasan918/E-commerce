package com.application.security;

import org.springframework.stereotype.Component;

import com.application.entity.UserData;
import com.application.repository.UserDataRepo;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired private UserDataRepo userRepo;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserData> userRes = userRepo.findByEmailId(email);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with email = " + email);
        UserData user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
	
	 public Optional<UserData> getUserDetails(String email) {
	    	Optional<UserData> userRes = userRepo.findByEmailId(email);
	    	if(userRes.isEmpty())
	            throw new UsernameNotFoundException("Could not findUser with email = " + email);

	        return userRes;
	    }
}
