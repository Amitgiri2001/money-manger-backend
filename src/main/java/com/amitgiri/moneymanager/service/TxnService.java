package com.amitgiri.moneymanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

public interface TxnService {

	TxnResponseDto createTxn(TxnRequestDto dto);

	TxnResponseDto updateTxn(Long id, UpdateTxnDto dto);

	void deleteTxn(Long id);


	Page<TxnResponseDto> getAllTransaction(Long userId, Pageable pageable);

	Page<TxnResponseDto> getTransactionByDateRange(Long userId, LocalDate sDate, LocalDate eDate, Pageable pageable);

	Page<TxnResponseDto> getTransactionByCategory(Long userId, TransactionCategory cat, Pageable pageable);

	Page<TxnResponseDto> getTransactionByType(Long userId, TransactionType tType, Pageable pageable);

	Page<TxnResponseDto> getTransactionByAmountRange(Long userId, BigDecimal min, BigDecimal max, Pageable pageable);

	Page<TxnResponseDto> getTransactionByNote(Long id, String keyword, Pageable pageable);

}
