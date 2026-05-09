package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxnFilterDto {

    private TransactionType type;

    private TransactionCategory category;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private String keyword;
}
