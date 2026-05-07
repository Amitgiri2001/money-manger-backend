package com.amitgiri.moneymanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
	@NotBlank(message="Name is required")
	@Size(min=3,max=30)
	private String name;
	
	@Email
	@NotBlank(message="Email is required")
	private String email;
	
	@Size(min=6,message="Password must be at least 6 characters")
	private String password;
}
