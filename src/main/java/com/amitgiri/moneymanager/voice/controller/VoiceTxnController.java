package com.amitgiri.moneymanager.voice.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgiri.moneymanager.dto.ApiResponse;
import com.amitgiri.moneymanager.dto.TxnRequestDto;
import com.amitgiri.moneymanager.dto.TxnResponseDto;
import com.amitgiri.moneymanager.service.TxnService;
import com.amitgiri.moneymanager.voice.dto.ConfirmTxnDto;
import com.amitgiri.moneymanager.voice.dto.ParsedTxnDto;
import com.amitgiri.moneymanager.voice.service.PendingTxnStore;
import com.amitgiri.moneymanager.voice.service.VoiceCommandParser;

@RestController
@RequestMapping("/api/voice")
public class VoiceTxnController {

	private final VoiceCommandParser parser;
	private final PendingTxnStore pendingTxnStore;

	private final TxnService txnService;

	public VoiceTxnController(VoiceCommandParser parser, PendingTxnStore pendingTxnStore, TxnService txnService) {

		this.parser = parser;
		this.pendingTxnStore = pendingTxnStore;
		this.txnService = txnService;
	}

	@PostMapping("/parse/{id}")
	public ResponseEntity<ApiResponse<ParsedTxnDto>> parseCommand(@PathVariable Long id,
			@RequestBody String command) {

		ParsedTxnDto dto = parser.parse(command, id);

		String confirmationId = UUID.randomUUID().toString();

		dto.setConfirmationId(confirmationId);

		pendingTxnStore.save(confirmationId, dto);

		return ResponseEntity.ok(new ApiResponse<>(true, "Please confirm transaction", dto));
	}

	@PostMapping("/confirm")
	public ResponseEntity<?> confirmTransaction(@RequestBody ConfirmTxnDto dto) {

		if (!dto.isConfirmed()) {

			return ResponseEntity.ok(new ApiResponse<>(true, "Transaction cancelled", null));
		}

		ParsedTxnDto parsedTxn = pendingTxnStore.get(dto.getConfirmationId());

		if (parsedTxn == null) {

			return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid confirmation id", null));
		}

		TxnRequestDto requestDto = convert(parsedTxn);

		TxnResponseDto savedTxn = txnService.createTxn(requestDto);

		pendingTxnStore.remove(dto.getConfirmationId());

		return ResponseEntity.ok(new ApiResponse<>(true, "Transaction created successfully", savedTxn));
	}

	private TxnRequestDto convert(ParsedTxnDto parsedTxn) {

		TxnRequestDto dto = new TxnRequestDto();

		dto.setType(parsedTxn.getType());

		dto.setAmount(parsedTxn.getAmount());

		dto.setCategory(parsedTxn.getCategory());

		dto.setNote(parsedTxn.getNote());

		dto.setTime(parsedTxn.getTime());

		dto.setUserId(parsedTxn.getUserId());

		return dto;
	}
}