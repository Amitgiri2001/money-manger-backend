package com.amitgiri.moneymanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.enums.Level;

public interface TxnClassificationRepository extends JpaRepository<TxnClassification, Long> {
	@Query("""
			SELECT t
			FROM TxnClassification t
			WHERE t.level = :level
			AND (
				t.createdBy IS NULL
				OR t.createdBy.id = :userId
			)
		""")
		List<TxnClassification> fetchDefaultsAndUserCreated(
			Long userId,
			Level level
		);

	TxnClassification findByName(String upperCase);
	@Query("""
			SELECT t
			FROM TxnClassification t
			WHERE t.parent.id = :typeId
			AND (
				t.createdBy IS NULL
				OR t.createdBy.id = :userId
			)
		""")
		List<TxnClassification> findCategoriesByType(Long userId,Long typeId);
}
