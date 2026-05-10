package com.amitgiri.moneymanager.voice.service;

import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.voice.dto.ParsedTxnDto;
import com.amitgiri.moneymanager.voice.utils.DataExtractFromMsg;

@Service
public class VoiceCommandParser {

	private final DataExtractFromMsg dataExtract;

	VoiceCommandParser(DataExtractFromMsg dataExtract) {
		this.dataExtract = dataExtract;
	}

	public ParsedTxnDto parse(String command,Long userId) {

		ParsedTxnDto dto = new ParsedTxnDto();

		dto.setOriginalCommand(command);
		
		dto.setUserId(userId);
		String lower = command.toLowerCase();

		// extract type
		dataExtract.extractType(lower, dto);

		// extract amount
		dataExtract.extractAmount(lower, dto);

		// extract note
		dataExtract.extractNote(lower, dto);

		// extract time
		dataExtract.extractTime(lower, dto);

		// extract category
		dataExtract.extractCategory(command, dto);

		// validation
		dto.setValid(
				dto.getType() != null && dto.getAmount() != null && dto.getCategory() != null && dto.getTime() != null);

		return dto;
	}
}