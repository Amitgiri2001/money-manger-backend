
package com.amitgiri.moneymanager.voice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedTxnDto {
    private BigDecimal amount;

    private String note;

    private LocalDateTime time;

    private String originalCommand;

    private boolean valid;

    private String confirmationId;

    private Long userId;
    
    private Long txnTypeId;

	private Long txnCategoryId;
}