package com.amitgiri.moneymanager.voice.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.entity.TxnClassification;
import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;
import com.amitgiri.moneymanager.service.TxnClassificationService;
import com.amitgiri.moneymanager.voice.dto.ParsedTxnDto;

@Service
public class DataExtractFromMsg {
	private final TxnClassificationService txnClassificationService;
	DataExtractFromMsg(TxnClassificationService txnClassificationService){
		this.txnClassificationService=txnClassificationService;
	}
	public void extractType(String command, ParsedTxnDto dto) {

		if (command.contains("expense")) {
			TxnClassification txnType = getTxnClassifier("expense");
			dto.setTxnTypeId(txnType.getId());
		} else if (command.contains("income")) {
			TxnClassification txnType = getTxnClassifier("income");
			dto.setTxnTypeId(txnType.getId());
		} else if (command.contains("invest") || command.contains("investment")) {
			TxnClassification txnType = getTxnClassifier("invest");
			dto.setTxnTypeId(txnType.getId());
		} else if (command.contains("loan")) {
			TxnClassification txnType = getTxnClassifier("loan");
			dto.setTxnTypeId(txnType.getId());
		} else {
			TxnClassification txnType = getTxnClassifier("expense");
			dto.setTxnTypeId(txnType.getId());
		}
	}

	public void extractAmount(String command, ParsedTxnDto dto) {

		Pattern pattern = Pattern.compile("(?i)(rs|rupees|rupee|amount|inr)\\s*(\\d+(\\.\\d+)?)");

		Matcher matcher = pattern.matcher(command);

		if (matcher.find()) {

			String amount = matcher.group(2);

			dto.setAmount(new BigDecimal(amount));
		}
	}

	public void extractNote(String command, ParsedTxnDto dto) {

		if (command.contains("note")) {

			String[] parts = command.split("note");

			if (parts.length > 1) {

				dto.setNote(parts[1].trim());
			}
		}
	}

	public void extractTime(String command, ParsedTxnDto dto) {

		LocalDate date = LocalDate.now();

		String lower = command.toLowerCase();

		// detect date keywords
		if (lower.contains("yesterday")) {

			date = date.minusDays(1);

		} else if (lower.contains("tomorrow")) {

			date = date.plusDays(1);
		}

		// extract time
		Pattern pattern = Pattern.compile("(\\d{1,2}):(\\d{2})");

		Matcher matcher = pattern.matcher(lower);

		LocalTime time = LocalTime.now();

		if (matcher.find()) {

			int hour = Integer.parseInt(matcher.group(1));

			int minute = Integer.parseInt(matcher.group(2));

			time = LocalTime.of(hour, minute);
		}

		dto.setTime(LocalDateTime.of(date, time));
	}

	public void extractCategory(String command, ParsedTxnDto dto) {

		Pattern pattern = Pattern.compile("category\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(command);

		if (matcher.find()) {

			String categoryText = matcher.group(1).toUpperCase();

			try {

				TxnClassification txnCategory = getTxnClassifier(categoryText);
				dto.setTxnCategoryId(txnCategory.getId());

			} catch (Exception ex) {

				dto.setTxnCategoryId(null);
			}
		}
	}
	
	private TxnClassification getTxnClassifier(String name) {
		return txnClassificationService.findTxnByNameOrThrow(name.toUpperCase());
	}
}
