package com.amitgiri.moneymanager.voice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmTxnDto {

    private String confirmationId;

    private boolean confirmed;

    private BigDecimal amount;

    private String note;

    private LocalDateTime time;

    private Long userId;
    
    private Long txnTypeId;

	private Long txnCategoryId;
}
