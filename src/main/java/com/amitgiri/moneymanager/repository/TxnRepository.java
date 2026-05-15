package com.amitgiri.moneymanager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.amitgiri.moneymanager.entity.Txn;

public interface TxnRepository extends JpaRepository<Txn, Long>, JpaSpecificationExecutor<Txn> {

	Page<Txn> findAll(Specification<Txn> spec, Pageable pageable);

	// analytics
	@Query("""
		       SELECT t.txnType.name, COALESCE(SUM(t.effectiveAmount), 0)
		       FROM Txn t
		       WHERE t.user.id = :userId
		       AND t.time BETWEEN :startDate AND :endDate
		       GROUP BY t.txnType.name
		       """)
		List<Object[]> getTotalsGroupedByType(
		        Long userId,
		        LocalDateTime startDate,
		        LocalDateTime endDate
		);
	// txn count
	@Query("""
			SELECT COUNT(t)
			FROM Txn t
			WHERE t.user.id = :userId
			AND t.time BETWEEN :startDate AND :endDate
			""")
	Long getTransactionCount(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
