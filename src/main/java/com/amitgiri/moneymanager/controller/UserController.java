package com.amitgiri.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgiri.moneymanager.dto.ApiResponse;
import com.amitgiri.moneymanager.dto.UpdateUserDto;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
		//call service layer for create the user
		UserResponseDto resDto=userService.getUserById(id);
		ApiResponse<UserResponseDto> apiRes=new ApiResponse<>(true,"User fetched successfully",resDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> updateUserById(@PathVariable Long id,@Valid @RequestBody UpdateUserDto dto) {
		UserResponseDto resDto=userService.updateUserById(id,dto);
		ApiResponse<UserResponseDto> apiRes=new ApiResponse<>(true,"User updated successfully",resDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> deleteUserById(@PathVariable Long id) {
		UserResponseDto resDto=userService.deleteUserById(id);
		ApiResponse<UserResponseDto> apiRes=new ApiResponse<>(true,"User deleted successfully",resDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiRes);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<UserResponseDto[]>> getAllUser() {
		//call service layer for create the user
		UserResponseDto[] resDto=userService.getAllUsers();
		ApiResponse<UserResponseDto[]> apiRes=new ApiResponse<>(true,"All user fetched successfully",resDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}
}
