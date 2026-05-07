package com.amitgiri.moneymanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.repository.UserRepository;
import com.amitgiri.moneymanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserRepository userRepo;
	public UserResponseDto createUser(UserRequestDto dto) {
		
		if(userRepo.existsByEmail(dto.getEmail())) {
			//need to do exp handling for proper response send
			throw new RuntimeException("Email id : "+dto.getEmail()+" already exists, email must be unique");
		}
		User newUser=new User();
		newUser.setName(dto.getName());
		newUser.setEmail(dto.getEmail());
		newUser.setPassword(dto.getPassword());
		
		//call repo for save the user
		User createdUser=userRepo.save(newUser);
		
		UserResponseDto userRes=new UserResponseDto();
		userRes.setId(createdUser.getId());
		userRes.setName(createdUser.getName());
		userRes.setEmail(createdUser.getEmail());
		userRes.setCreatedAt(createdUser.getCreatedAt());
		userRes.setUpdatedAt(createdUser.getUpdatedAt());
		
		return userRes;
	}
}
