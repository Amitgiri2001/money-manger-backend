package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnResponseDto {

    private Long id;

    private TransactionType type;

    private BigDecimal amount;

    private BigDecimal effectiveAmount;
    
    private TransactionCategory category;

    private String note;

    private Long userId;
    
    private TxnClassification txnType;

	private TxnClassification txnCategory;

    private LocalDateTime time;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}