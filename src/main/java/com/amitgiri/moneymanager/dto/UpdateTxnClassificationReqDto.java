package com.amitgiri.moneymanager.dto;

import org.hibernate.validator.constraints.Length;

import com.amitgiri.moneymanager.enums.Level;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTxnClassificationReqDto {
	private Level lavel;
	
	private String name;
	
	@Length(max=200)
	private String description;
	
	private Long createdBy;
}
