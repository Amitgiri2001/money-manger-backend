package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnResponseDto {

    private Long id;

    private TransactionType type;

    private BigDecimal amount;

    private TransactionCategory category;

    private String note;

    private Long userId;

    private LocalDateTime time;
    
    private boolean deleted;
    
    private LocalDateTime deletedAt;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}