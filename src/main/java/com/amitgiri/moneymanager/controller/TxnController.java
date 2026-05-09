package com.amitgiri.moneymanager.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;
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
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getAllTransaction(@PathVariable Long id,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getAllTransaction(id,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully", resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping("/user/{id}/date-range")
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getTxnByDate(@PathVariable Long id,@RequestParam LocalDate sDate,@RequestParam LocalDate eDate,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getTransactionByDateRange(id,sDate,eDate,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully from date : "+sDate+" to Date "+eDate, resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping("/user/{id}/amount-range")
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getTxnByAmount(@PathVariable Long id,@RequestParam BigDecimal min,@RequestParam BigDecimal max,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getTransactionByAmountRange(id,min,max,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully from amount : "+min+" to amount "+max, resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping("/user/{id}/category")
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getTxnByCategory(@PathVariable Long id,@RequestParam TransactionCategory cat,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getTransactionByCategory(id,cat,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully for category : "+cat, resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping("/user/{id}/type")
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getTxnByType(@PathVariable Long id,@RequestParam TransactionType type,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getTransactionByType(id,type,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully for type : "+type, resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping("/user/{id}/note")
	public ResponseEntity<ApiResponse<Page<TxnResponseDto>>> getTxnByNote(@PathVariable Long id,@RequestParam String keyword,Pageable pageable) {
		Page<TxnResponseDto> resDto = txnService.getTransactionByNote(id,keyword,pageable);
		ApiResponse<Page<TxnResponseDto>> data = new ApiResponse<>(true, "All transaction fetched successfully for keyword : "+keyword, resDto);
		return ResponseEntity.status(HttpStatus.OK).body(data);
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
