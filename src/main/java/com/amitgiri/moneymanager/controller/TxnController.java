package com.amitgiri.moneymanager.controller;

import java.time.YearMonth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amitgiri.moneymanager.dto.ApiResponse;
import com.amitgiri.moneymanager.dto.MonthlyAnalyticsDto;
import com.amitgiri.moneymanager.dto.TxnFilterDto;
import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.service.TxnService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/txn")
public class TxnController {

	private final TxnService txnService;

	public TxnController(TxnService txnService) {
		this.txnService = txnService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<TxnResponseDto>> createTxn(@Valid @RequestBody TxnRequestDto dto) {
		TxnResponseDto resDto = txnService.createTxn(dto);
		ApiResponse<TxnResponseDto> data = new ApiResponse<>(true, "Transaction created successfully", resDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<
	        ApiResponse<Page<TxnResponseDto>>
	>
	searchTransactions(
	        @PathVariable Long id,
	        TxnFilterDto filter,
	        Pageable pageable
	) {

	    Page<TxnResponseDto> res =
	            txnService.searchTransactions(
	                    id,
	                    filter,
	                    pageable
	            );

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Transactions fetched successfully",
	                    res
	            )
	    );
	}
	
	
//	analytics
	@GetMapping("/user/{id}/analytics/monthly")
	public ResponseEntity<
	        ApiResponse<MonthlyAnalyticsDto>
	>
	getMonthlyAnalytics(
	        @PathVariable Long id,
	        @RequestParam YearMonth month
	) {

	    MonthlyAnalyticsDto data =
	            txnService.getMonthlyAnalytics(
	                    id,
	                    month
	            );

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Monthly analytics fetched successfully",
	                    data
	            )
	    );
	}
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<TxnResponseDto>> updateTxn(@PathVariable Long id,
			@Valid @RequestBody UpdateTxnDto dto) {

		TxnResponseDto resDto = txnService.updateTxn(id, dto);

		ApiResponse<TxnResponseDto> data = new ApiResponse<>(true, "Transaction updated successfully", resDto);

		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Object>> deleteTxn(@PathVariable Long id) {

		txnService.deleteTxn(id);

		ApiResponse<Object> data = new ApiResponse<>(true, "Transaction deleted successfully", null);

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
}
