package com.amitgiri.moneymanager.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.dto.UpdateTxnDto;
import com.amitgiri.moneymanager.entity.Txn;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;
import com.amitgiri.moneymanager.exception.ResourceNotFoundException;
import com.amitgiri.moneymanager.repository.TxnRepository;
import com.amitgiri.moneymanager.service.TxnService;
import com.amitgiri.moneymanager.service.UserService;

@Service
public class TxnServiceImpl implements TxnService {
	private final TxnRepository txnRepo;
	private final UserService userService;
	
	public TxnServiceImpl(TxnRepository txnRepo,UserService userService) {
		this.txnRepo=txnRepo;
		this.userService=userService;
	}
	@Override
	public TxnResponseDto createTxn(TxnRequestDto dto) {
		//check if user is valid User or not
		User user=userService.getActiveUserOrThrow(dto.getUserId());
		Txn txn = mapToTxnEntity(dto,user);
		Txn saveTxn=txnRepo.save(txn);
		return mapToTxnResponseDto(saveTxn);
	}
	@Override
	public Page<TxnResponseDto> getAllTransaction(Long userId,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
		if(pageable.getPageSize() > 50) {
			throw new RuntimeException("Page size can't be greater than 50");
		}
		Page<Txn> resTxn=txnRepo.findAllByUserId(userId,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	@Override
	public Page<TxnResponseDto> getTransactionByDateRange(Long userId,LocalDate sDate,LocalDate eDate,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
		LocalDateTime start =
	            sDate.atStartOfDay();

	    LocalDateTime end =
	            eDate.atTime(23, 59, 59);
	    
		Page<Txn> resTxn=txnRepo.findAllByUserIdAndTimeBetween(userId,start,end,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	@Override
	public Page<TxnResponseDto> getTransactionByAmountRange(Long userId,BigDecimal min,BigDecimal max,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
		
		Page<Txn> resTxn=txnRepo.findAllByUserIdAndAmountBetween(userId,min,max,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	@Override
	public Page<TxnResponseDto> getTransactionByCategory(Long userId,TransactionCategory cat,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
	    
		Page<Txn> resTxn=txnRepo.findAllByUserIdAndCategory(userId,cat,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	@Override
	public Page<TxnResponseDto> getTransactionByType(Long userId,TransactionType tType,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
	    
		Page<Txn> resTxn=txnRepo.findAllByUserIdAndType(userId,tType,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	
	@Override
	public Page<TxnResponseDto> getTransactionByNote(Long userId,String keyword,Pageable pageable){
		User user=userService.getActiveUserOrThrow(userId);
	    
		Page<Txn> resTxn=txnRepo.findAllByUserIdAndNoteContainingIgnoreCase(userId,keyword,pageable);
		Page<TxnResponseDto> data=resTxn.map(this::mapToTxnResponseDto);
		return data;
	}
	
	@Override
	public TxnResponseDto updateTxn(
	        Long id,
	        UpdateTxnDto dto
	) {

	    Txn txn = getActiveTxnOrThrow(id);

	    if(dto.getType() != null) {
	        txn.setType(dto.getType());
	    }

	    if(dto.getAmount() != null) {
	        txn.setAmount(dto.getAmount());
	    }

	    if(dto.getCategory() != null) {
	        txn.setCategory(dto.getCategory());
	    }

	    if(dto.getNote() != null) {
	        txn.setNote(dto.getNote());
	    }

	    if(dto.getTime() != null) {
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
	
	
	private Txn mapToTxnEntity(TxnRequestDto dto,User user) {

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
		
		dto.setDeleted(txn.getDeleted());
		
		dto.setDeletedAt(txn.getDeletedAt());

		dto.setCreatedAt(txn.getCreatedAt());

		dto.setUpdatedAt(txn.getUpdatedAt());

		return dto;
	}
	private Txn getActiveTxnOrThrow(Long id) {

	    Txn txn= txnRepo.findById(id).orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Transaction not found with id : " + id
	                    )
	            );
	    if(txn.getDeleted()) {
	    	throw new ResourceNotFoundException(
                    "Transaction already deleted with id : " + id
            );
	    }
	    return txn;
	}
}
