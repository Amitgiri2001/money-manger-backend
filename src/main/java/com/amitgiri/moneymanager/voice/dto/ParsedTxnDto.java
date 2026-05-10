
package com.amitgiri.moneymanager.voice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedTxnDto {

    private TransactionType type;

    private BigDecimal amount;

    private TransactionCategory category;

    private String note;

    private LocalDateTime time;

    private String originalCommand;

    private boolean valid;

    private String confirmationId;

    private Long userId;
}