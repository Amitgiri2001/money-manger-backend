package com.amitgiri.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgiri.moneymanager.dto.ApiResponse;
import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;
import com.amitgiri.moneymanager.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto dto) {
		//call service layer for create the user
		UserResponseDto resDto=userService.createUser(dto);
		ApiResponse<UserResponseDto> apiRes=new ApiResponse<>(true,"User created successfully",resDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiRes);
	}
}
