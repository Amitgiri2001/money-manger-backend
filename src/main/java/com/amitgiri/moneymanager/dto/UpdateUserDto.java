package com.amitgiri.moneymanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
	@Size(min=3,max=30)
	private String name;
	
	@Email(message="Not a valid email")
	private String email;
}
