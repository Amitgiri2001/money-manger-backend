package com.amitgiri.moneymanager.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.UpdateUserDto;
import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.exception.EmailAlreadyExistsException;
import com.amitgiri.moneymanager.exception.ResourceNotFoundException;
import com.amitgiri.moneymanager.repository.UserRepository;
import com.amitgiri.moneymanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;

	public UserResponseDto createUser(UserRequestDto dto) {

		if (userRepo.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException(
					"Email id : " + dto.getEmail() + " already exists, email id need to be unique");
		}
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setEmail(dto.getEmail());
		newUser.setPassword(dto.getPassword());

		// call repo for save the user
		User createdUser = userRepo.save(newUser);

		UserResponseDto userRes = mapToUserResDto(createdUser);

		return userRes;
	}

	public UserResponseDto getUserById(Long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> (new ResourceNotFoundException("User not found with id : " + id)));

		return mapToUserResDto(user);
	}

	public UserResponseDto updateUserById(Long id,UpdateUserDto dto) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> (new ResourceNotFoundException("User not found with id : " + id)));
		
		if(dto.getName()!=null)
			user.setName(dto.getName());
		if(dto.getEmail()!=null) {
			if (userRepo.existsByEmail(dto.getEmail())) {
				throw new EmailAlreadyExistsException(
						"Email id : " + dto.getEmail() + " already exists, email id need to be unique");
			}
			user.setEmail(dto.getEmail());
		}
		User updatedUser=userRepo.save(user);
		return mapToUserResDto(updatedUser);
	}
	
	public UserResponseDto deleteUserById(Long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> (new ResourceNotFoundException("User not found with id : " + id)));
		
		//userRepo.delete(user);
		user.setIsDeleted(true);
		user.setDeletedAt(LocalDateTime.now());
		User updatedUser=userRepo.save(user);
		return mapToUserResDto(updatedUser);
	}
	
	public UserResponseDto[] getAllUsers() {

		return userRepo.findAll().stream().map(this::mapToUserResDto).toArray(UserResponseDto[]::new);
	}

	
	private UserResponseDto mapToUserResDto(User user) {

		UserResponseDto dto = new UserResponseDto();

		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setIsDeleted(user.getIsDeleted());
		dto.setCreatedAt(user.getCreatedAt());
		dto.setUpdatedAt(user.getUpdatedAt());
		dto.setDeletedAt(user.getDeletedAt());
		return dto;
	}
	
}
