package com.amitgiri.moneymanager.service;

import java.util.List;

import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;

public interface UserService {
	UserResponseDto createUser(UserRequestDto dto);
	UserResponseDto getUserById(Long id);
	UserResponseDto[] getAllUsers();
}
