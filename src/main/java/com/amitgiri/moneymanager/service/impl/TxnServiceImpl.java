package com.amitgiri.moneymanager.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.MonthlyAnalyticsDto;
import com.amitgiri.moneymanager.dto.TxnFilterDto;
import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.entity.Txn;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.enums.TransactionType;
import com.amitgiri.moneymanager.exception.ResourceNotFoundException;
import com.amitgiri.moneymanager.repository.TxnRepository;
import com.amitgiri.moneymanager.service.TxnService;
import com.amitgiri.moneymanager.service.UserService;
import com.amitgiri.moneymanager.specification.TxnSpecification;

@Service
public class TxnServiceImpl implements TxnService {
	private final TxnRepository txnRepo;
	private final UserService userService;

	public TxnServiceImpl(TxnRepository txnRepo, UserService userService) {
		this.txnRepo = txnRepo;
		this.userService = userService;
	}

	@Override
	public TxnResponseDto createTxn(TxnRequestDto dto) {
		// check if user is valid User or not
		User user = userService.getActiveUserOrThrow(dto.getUserId());
		Txn txn = mapToTxnEntity(dto, user);
		Txn saveTxn = txnRepo.save(txn);
		return mapToTxnResponseDto(saveTxn);
	}

	@Override
	public Page<TxnResponseDto> searchTransactions(Long userId, TxnFilterDto filter, Pageable pageable) {

		userService.getActiveUserOrThrow(userId);

		Specification<Txn> spec = TxnSpecification.withFilters(userId, filter);

		Page<Txn> txnPage = txnRepo.findAll(spec, pageable);

		return txnPage.map(this::mapToTxnResponseDto);
	}

	// analytics
	@Override
	public MonthlyAnalyticsDto getMonthlyAnalytics(Long userId, YearMonth month) {

		userService.getActiveUserOrThrow(userId);

		LocalDateTime startDate = month.atDay(1).atStartOfDay();

		LocalDateTime endDate = month.atEndOfMonth().atTime(23, 59, 59);

		List<Object[]> res= txnRepo.getTotalsGroupedByType(userId, startDate, endDate);
		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal totalExpense = BigDecimal.ZERO;
		BigDecimal totalInvestment = BigDecimal.ZERO;
		BigDecimal totalLoan = BigDecimal.ZERO;

		for(Object[] row : res) {

		    TransactionType type =
		            (TransactionType) row[0];

		    BigDecimal total =
		            (BigDecimal) row[1];

		    switch(type) {

		        case INCOME ->
		                totalIncome = total;

		        case EXPENSE ->
		                totalExpense = total;

		        case INVESTMENT ->
		                totalInvestment = total;

		        case LOAN ->
		                totalLoan = total;
		    }
		}
		
		Long transactionCount = txnRepo.getTransactionCount(userId, startDate, endDate);

		BigDecimal effectiveBalance = totalIncome.subtract(totalExpense);

		BigDecimal currentBalance = effectiveBalance.subtract(totalInvestment).subtract(totalLoan);
		return new MonthlyAnalyticsDto(totalIncome, totalExpense, totalInvestment, totalLoan, effectiveBalance,
				currentBalance, transactionCount);
	}

	@Override
	public TxnResponseDto updateTxn(Long id, UpdateTxnDto dto) {

		Txn txn = getActiveTxnOrThrow(id);

		if (dto.getType() != null) {
			txn.setType(dto.getType());
		}

		if (dto.getAmount() != null) {
			txn.setAmount(dto.getAmount());
		}

		if (dto.getCategory() != null) {
			txn.setCategory(dto.getCategory());
		}

		if (dto.getNote() != null) {
			txn.setNote(dto.getNote());
		}

		if (dto.getTime() != null) {
			txn.setTime(dto.getTime());
		}

		Txn updatedTxn = txnRepo.save(txn);

		return mapToTxnResponseDto(updatedTxn);
	}

	@Override
	public void deleteTxn(Long id) {

		Txn txn = getActiveTxnOrThrow(id);

		txn.setDeleted(true);

		txn.setDeletedAt(LocalDateTime.now());

		txnRepo.save(txn);
	}

	private Txn mapToTxnEntity(TxnRequestDto dto, User user) {

		Txn txn = new Txn();

		txn.setType(dto.getType());
		txn.setAmount(dto.getAmount());
		txn.setCategory(dto.getCategory());
		txn.setNote(dto.getNote());
		txn.setTime(dto.getTime());
		txn.setUser(user);

		return txn;
	}

	private TxnResponseDto mapToTxnResponseDto(Txn txn) {

		TxnResponseDto dto = new TxnResponseDto();

		dto.setId(txn.getId());

		dto.setType(txn.getType());

		dto.setAmount(txn.getAmount());

		dto.setCategory(txn.getCategory());

		dto.setNote(txn.getNote());

		dto.setUserId(txn.getUser().getId());

		dto.setTime(txn.getTime());

		dto.setCreatedAt(txn.getCreatedAt());

		dto.setUpdatedAt(txn.getUpdatedAt());

		return dto;
	}

	private Txn getActiveTxnOrThrow(Long id) {

		Txn txn = txnRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id : " + id));
		if (txn.getDeleted()) {
			throw new ResourceNotFoundException("Transaction already deleted with id : " + id);
		}
		return txn;
	}
}
