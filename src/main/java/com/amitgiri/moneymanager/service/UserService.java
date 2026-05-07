package com.amitgiri.moneymanager.service;

import com.amitgiri.moneymanager.dto.UserRequestDto;
import com.amitgiri.moneymanager.dto.UserResponseDto;

public interface UserService {
	UserResponseDto createUser(UserRequestDto dto);
}
