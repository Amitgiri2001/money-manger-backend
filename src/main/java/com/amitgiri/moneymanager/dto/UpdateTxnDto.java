package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTxnDto {

    @DecimalMin(value = "0.00")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal amount;
    
    @DecimalMin(value = "0.00")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal effectiveAmount;

    private String note;

    private LocalDateTime time;
    
    private Long txnTypeId;
    
    private Long txnCategoryId;
}