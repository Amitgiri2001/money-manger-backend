package com.amitgiri.moneymanager.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyAnalyticsDto {

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal totalInvestment;
    
    private BigDecimal totalLoan;
    
    private BigDecimal effectiveBalance;

    private BigDecimal currentBalance;

    private Long transactionCount;
}
