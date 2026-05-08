package com.amitgiri.moneymanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.exception.EmailAlreadyExistsException;
import com.amitgiri.moneymanager.repository.UserRepository;
import com.amitgiri.moneymanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserRepository userRepo;
	public UserResponseDto createUser(UserRequestDto dto) {
		
		if(userRepo.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException("Email id : "+dto.getEmail()+" already exists, email id need to be unique");
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
