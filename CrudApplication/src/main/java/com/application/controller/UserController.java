package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.entity.UserData;
import com.application.repository.UserDataRepo;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired private UserDataRepo userRepo;

    @GetMapping("/info")
    public UserData getUserDetails(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmailId(email).get();
    }
}
