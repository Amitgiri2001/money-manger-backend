package com.amitgiri.moneymanager.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.dto.TxnClassificationReqDto;
import com.amitgiri.moneymanager.dto.TxnClassificationResDto;
import com.amitgiri.moneymanager.dto.UpdateTxnClassificationReqDto;
import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.entity.User;
import com.amitgiri.moneymanager.enums.Level;
import com.amitgiri.moneymanager.exception.ResourceNotFoundException;
import com.amitgiri.moneymanager.repository.TxnClassificationRepository;
import com.amitgiri.moneymanager.service.TxnClassificationService;
import com.amitgiri.moneymanager.service.UserService;
@Service
public class TxnClassificationServiceImpl implements TxnClassificationService{
	private final UserService userService;
	private final TxnClassificationRepository txnClassRepo;

	TxnClassificationServiceImpl(UserService userService,TxnClassificationRepository txnClassRepo){
		this.userService=userService;
		this.txnClassRepo=txnClassRepo;
	}
	@Override
	public TxnClassificationResDto create(TxnClassificationReqDto dto){
		User user = userService.getActiveUserOrThrow(dto.getCreatedBy());

		TxnClassification txn=mapReq(dto,user);
		TxnClassification createdTxn=txnClassRepo.save(txn);
		TxnClassificationResDto res=mapRes(createdTxn);
		return res;
	}
	
	@Override
	public List<TxnClassificationResDto> fetchDefaultsAndUserCreted(Long userId,Level level){
		List<TxnClassification> fetchedTxn=txnClassRepo.fetchDefaultsAndUserCreated(userId, level);
		List<TxnClassificationResDto> fetchedTxnRes =
				fetchedTxn.stream()
				.map(this::mapRes)
				.collect(Collectors.toList());
		return fetchedTxnRes;
	}
	@Override
	public List<TxnClassificationResDto> getCategoriesByType(Long userId,Long typeId) {

		List<TxnClassification> categories =
				txnClassRepo.findCategoriesByType(userId,typeId);

		return categories.stream()
				.map(this::mapRes)
				.toList();
	}
	@Override
	public TxnClassificationResDto update(Long userId,Long txnClassifierId,UpdateTxnClassificationReqDto dto){
		User user = userService.getActiveUserOrThrow(dto.getCreatedBy());
		//check if the owner of this txn is same as the curr txn
		TxnClassification txn=findTxnByIdOrThrow(txnClassifierId);
		if(dto.getName()!=null) {
			txn.setName(dto.getName());
		}
		if(dto.getDescription()!=null) {
			txn.setDescription(dto.getDescription());
		}
		if(dto.getLavel()!=null) {
			txn.setLevel(dto.getLavel());
		}
		TxnClassification savedTxn=txnClassRepo.save(txn);
		
		return mapRes(savedTxn);
	}
	
	//implement later
	@Override
	public TxnClassificationResDto delete(Long txnClassifierId){
		
		TxnClassification txn=findTxnByIdOrThrow(txnClassifierId);
		txnClassRepo.deleteById(txnClassifierId);
		return mapRes(txn);
	}
	
	public TxnClassification findTxnByIdOrThrow(Long txnId) {
		TxnClassification txn=txnClassRepo.findById(txnId).orElseThrow(()->new ResourceNotFoundException("Transaction Classifier not found with id : " + txnId));
		return txn;
	}
	public TxnClassification findTxnByNameOrThrow(String txnName) {
		TxnClassification txn=txnClassRepo.findByName(txnName.toUpperCase());
		if(txn==null) {
			throw new ResourceNotFoundException("Transaction Classifier not found with name : \" + txnName");
		}
		return txn;
	}
	private TxnClassification mapReq(TxnClassificationReqDto dto,User user) {
		TxnClassification txn=new TxnClassification();
		txn.setLevel(dto.getLevel());
		txn.setName(dto.getName().toUpperCase());
		txn.setDescription(dto.getDescription());
		txn.setCreatedBy(user);
		
		if (dto.getParentId() != null) {
			if (dto.getLevel() == Level.TYPE) {
				throw new IllegalArgumentException("A transaction TYPE cannot have a parent.");
			}
			TxnClassification parent = findTxnByIdOrThrow(dto.getParentId());
			if (parent.getLevel() != Level.TYPE) {
				throw new IllegalArgumentException("The parent of a CATEGORY must be a TYPE.");
			}
			txn.setParent(parent);
		}
		
		return txn;
	}
	private TxnClassificationResDto mapRes(TxnClassification txn) {
		TxnClassificationResDto dto=new TxnClassificationResDto();
		dto.setId(txn.getId());
		dto.setLevel(txn.getLevel());
		dto.setName(txn.getName());
		dto.setDescription(txn.getDescription());
		if (txn.getCreatedBy() != null) {
			dto.setCreatedBy(txn.getCreatedBy().getId());
		}
		if (txn.getParent() != null) {
			dto.setParentId(txn.getParent().getId());
		}
		dto.setCreateAt(txn.getCreatedAt());
		dto.setUpdatedAt(txn.getUpdatedAt());
		
		return dto;
	}
}
