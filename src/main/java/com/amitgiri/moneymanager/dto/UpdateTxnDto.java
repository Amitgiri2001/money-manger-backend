package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTxnDto {

    private TransactionType type;

    @DecimalMin(value = "0.01")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal amount;

    private TransactionCategory category;

    private String note;

    private LocalDateTime time;
}