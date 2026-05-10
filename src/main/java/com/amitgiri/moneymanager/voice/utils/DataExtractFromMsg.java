package com.amitgiri.moneymanager.voice.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.amitgiri.moneymanager.enums.TransactionCategory;
import com.amitgiri.moneymanager.enums.TransactionType;
import com.amitgiri.moneymanager.voice.dto.ParsedTxnDto;


@Service
public class DataExtractFromMsg {
	public void extractType(
	        String command,
	        ParsedTxnDto dto
	) {

	    if(command.contains("expense")) {

	        dto.setType(
	                TransactionType.EXPENSE
	        );

	    } else if(command.contains("income")) {

	        dto.setType(
	                TransactionType.INCOME
	        );
	    }
	    else if(command.contains("invest")||command.contains("investment")) {

	        dto.setType(
	                TransactionType.INVESTMENT
	        );
	    }
	    else if(command.contains("loan")) {

	        dto.setType(
	                TransactionType.LOAN
	        );
	    }else {
	    	dto.setType(
	                TransactionType.EXPENSE
	        );
	    }
	}
	
	public void extractAmount(
	        String command,
	        ParsedTxnDto dto
	) {

	    Pattern pattern =
	            Pattern.compile(
	                "(?i)(rs|rupees|rupee|amount|inr)\\s*(\\d+(\\.\\d+)?)"
	            );

	    Matcher matcher =
	            pattern.matcher(command);

	    if(matcher.find()) {

	        String amount =
	                matcher.group(2);

	        dto.setAmount(
	                new BigDecimal(amount)
	        );
	    }
	}
	
	public void extractNote(
	        String command,
	        ParsedTxnDto dto
	) {

	    if(command.contains("note")) {

	        String[] parts =
	                command.split("note");

	        if(parts.length > 1) {

	            dto.setNote(
	                    parts[1].trim()
	            );
	        }
	    }
	}
	
	public void extractTime(
	        String command,
	        ParsedTxnDto dto
	) {

	    LocalDate date = LocalDate.now();

	    String lower =
	            command.toLowerCase();

	    // detect date keywords
	    if(lower.contains("yesterday")) {

	        date = date.minusDays(1);

	    } else if(lower.contains("tomorrow")) {

	        date = date.plusDays(1);
	    }

	    // extract time
	    Pattern pattern =
	            Pattern.compile("(\\d{1,2}):(\\d{2})");

	    Matcher matcher =
	            pattern.matcher(lower);

	    LocalTime time =
	            LocalTime.now();

	    if(matcher.find()) {

	        int hour =
	                Integer.parseInt(
	                        matcher.group(1)
	                );

	        int minute =
	                Integer.parseInt(
	                        matcher.group(2)
	                );

	        time = LocalTime.of(
	                hour,
	                minute
	        );
	    }

	    dto.setTime(
	            LocalDateTime.of(date, time)
	    );
	}
	public void extractCategory(
	        String command,
	        ParsedTxnDto dto
	) {

	    Pattern pattern =
	            Pattern.compile(
	                    "category\\s+(\\w+)",
	                    Pattern.CASE_INSENSITIVE
	            );

	    Matcher matcher =
	            pattern.matcher(command);

	    if(matcher.find()) {

	        String categoryText =
	                matcher.group(1)
	                        .toUpperCase();

	        try {

	            dto.setCategory(
	                    TransactionCategory
	                            .valueOf(categoryText)
	            );

	        } catch(Exception ex) {

	            dto.setCategory(null);
	        }
	    }
	}
}
