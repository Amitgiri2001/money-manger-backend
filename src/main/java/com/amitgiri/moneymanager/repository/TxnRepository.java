package com.amitgiri.moneymanager.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amitgiri.moneymanager.entity.Txn;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

public interface TxnRepository extends JpaRepository<Txn,Long> {


	Page<Txn> findAllByUserIdAndTimeBetween(Long userId, LocalDateTime sTime,LocalDateTime eTime,Pageable pageable);

	Page<Txn> findAllByUserId(Long userId, Pageable pageable);

	Page<Txn> findAllByUserIdAndCategory(Long userId, TransactionCategory cat, Pageable pageable);

	Page<Txn> findAllByUserIdAndType(Long userId, TransactionType tType, Pageable pageable);

	Page<Txn> findAllByUserIdAndAmountBetween(Long userId, BigDecimal min, BigDecimal max, Pageable pageable);

	Page<Txn> findAllByUserIdAndNoteContainingIgnoreCase(Long userId, String keyword, Pageable pageable);
	
}
