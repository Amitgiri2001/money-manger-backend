package com.amitgiri.moneymanager.service;

import java.util.List;

import com.amitgiri.moneymanager.dto.TxnClassificationReqDto;
import com.amitgiri.moneymanager.dto.TxnClassificationResDto;
import com.amitgiri.moneymanager.dto.UpdateTxnClassificationReqDto;
import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.enums.Level;

public interface TxnClassificationService {
	TxnClassificationResDto create(TxnClassificationReqDto dto);

	TxnClassificationResDto delete(Long txnClassificationId);

	List<TxnClassificationResDto> fetchDefaultsAndUserCreted(Long userId, Level level);

	TxnClassificationResDto update(Long userId, Long txnClassifierId, UpdateTxnClassificationReqDto dto);

	TxnClassification findTxnByIdOrThrow(Long txnTypeId);

}
