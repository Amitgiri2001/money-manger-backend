package com.amitgiri.moneymanager.dto;

import java.time.LocalDateTime;

import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.enums.Level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnClassificationResDto {
	private Long id;
	private Level level;
	private String name;
	private String description;
	private Long createdBy;
	private Long parentId;
	private LocalDateTime createAt;
	private LocalDateTime updatedAt;

}
