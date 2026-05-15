package com.amitgiri.moneymanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amitgiri.moneymanager.dto.ApiResponse;
import com.amitgiri.moneymanager.dto.TxnClassificationReqDto;
import com.amitgiri.moneymanager.dto.TxnClassificationResDto;
import com.amitgiri.moneymanager.dto.UpdateTxnClassificationReqDto;
import com.amitgiri.moneymanager.enums.Level;
import com.amitgiri.moneymanager.service.TxnClassificationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/txn-classifier")
public class TxnClassificationController {
	private final TxnClassificationService txnClassificationService;
	TxnClassificationController(TxnClassificationService txnClassificationService){
		this.txnClassificationService=txnClassificationService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<TxnClassificationResDto>> create(@Valid @RequestBody TxnClassificationReqDto dto){
		TxnClassificationResDto res=txnClassificationService.create(dto);
		ApiResponse<TxnClassificationResDto> data= new ApiResponse<>(true,"New Txn Classifier created",res);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<List<TxnClassificationResDto>>> update(@PathVariable Long userId,@RequestParam Level level){
		List<TxnClassificationResDto> res=txnClassificationService.fetchDefaultsAndUserCreted(userId,level);
		ApiResponse<List<TxnClassificationResDto>> data= new ApiResponse<>(true,"All Classifier fetched",res);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	
	@GetMapping("/{userId}/types/{typeId}/categories")
	public ResponseEntity<ApiResponse<List<TxnClassificationResDto>>> getCategoriesByType(@PathVariable Long userId,@PathVariable Long typeId){
		List<TxnClassificationResDto> res=txnClassificationService.getCategoriesByType(userId, typeId);
		ApiResponse<List<TxnClassificationResDto>> data= new ApiResponse<>(true,"All Category fetched",res);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	
	@PatchMapping("/{userId}/{txnClassifierId}")
	public ResponseEntity<ApiResponse<TxnClassificationResDto>> update(@PathVariable Long userId,@PathVariable Long txnClassifierId,
			@Valid @RequestBody UpdateTxnClassificationReqDto dto ){
		TxnClassificationResDto res=txnClassificationService.update(userId,txnClassifierId,dto);
		ApiResponse<TxnClassificationResDto> data= new ApiResponse<>(true,"Txn Classifier updated",res);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}
	@DeleteMapping("/{userId}/{txnClassifierId}")
	public ResponseEntity<ApiResponse<TxnClassificationResDto>> delete(@PathVariable Long userId,@PathVariable Long txnClassifierId
			 ){
		TxnClassificationResDto res=txnClassificationService.delete(txnClassifierId);
		ApiResponse<TxnClassificationResDto> data= new ApiResponse<>(true,"Txn Classifier deleted",res);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
}
