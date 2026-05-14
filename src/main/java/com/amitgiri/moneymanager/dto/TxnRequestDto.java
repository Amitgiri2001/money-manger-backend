package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnRequestDto {

    @NotNull(message = "Txn type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal amount;
    
    @DecimalMin(value = "0.00")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal effectiveAmount;

    @NotNull(message = "Category is required")
    private TransactionCategory category;

    @Size(max = 100)
    private String note;

    @NotNull(message = "Txn time is required")
    private LocalDateTime time;

    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Txn type ID is required")
    private Long txnTypeId;
    
    @NotNull(message = "Txn category ID is required")
    private Long txnCategoryId;
    
}