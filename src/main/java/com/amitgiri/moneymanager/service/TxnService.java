package com.amitgiri.moneymanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amitgiri.moneymanager.dto.MonthlyAnalyticsDto;
import com.amitgiri.moneymanager.dto.TxnFilterDto;
import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

public interface TxnService {

	TxnResponseDto createTxn(TxnRequestDto dto);

	TxnResponseDto updateTxn(Long id, UpdateTxnDto dto);

	void deleteTxn(Long id);

	Page<TxnResponseDto> searchTransactions(Long userId, TxnFilterDto filter, Pageable pageable);

	MonthlyAnalyticsDto getMonthlyAnalytics(Long userId, YearMonth month);


	

}
