package com.amitgiri.moneymanager.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SQLRestriction("deleted = false")
@Getter
@Setter
@NoArgsConstructor

@Table(indexes= {
		@Index(name="idx_user_id",columnList="user_id"),
		@Index(name="idx_user_date",columnList="user_id, time")
})
public class Txn {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@DecimalMin(value="0.00")
	@Column(nullable=false,precision = 15, scale = 2)
	private BigDecimal amount;
	
	@DecimalMin(value="0.00")
	@Column(nullable=false,precision = 15, scale = 2)
	private BigDecimal effectiveAmount;
	
	@Column(length=100)
	private String note;
	
	@Column(nullable=false)
	private LocalDateTime time;
	
	@Column(nullable=false)
	private Boolean deleted=false;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "type_classifier_id")
	private TxnClassification txnType;

	@ManyToOne
	@JoinColumn(name = "cat_classifier_id")
	private TxnClassification txnCategory;
	
	@Column(nullable=true)
	private LocalDateTime deletedAt;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
