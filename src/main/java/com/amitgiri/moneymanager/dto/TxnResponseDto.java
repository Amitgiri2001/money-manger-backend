package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnResponseDto {

    private Long id;

    private BigDecimal amount;

    private BigDecimal effectiveAmount;
    
    private String note;

    private Long userId;
    
    private TxnClassificationResDto txnType;

	private TxnClassificationResDto txnCategory;

    private LocalDateTime time;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}