package com.amitgiri.moneymanager.service;

import com.amitgiri.moneymanager.dto.UpdateUserDto;
import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;

public interface UserService {
	UserResponseDto createUser(UserRequestDto dto);
	UserResponseDto getUserById(Long id);
	UserResponseDto[] getAllUsers();
	UserResponseDto updateUserById(Long id,UpdateUserDto dto);
	UserResponseDto deleteUserById(Long id);

}
