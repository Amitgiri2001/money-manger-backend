package com.amitgiri.moneymanager.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	public EmailAlreadyExistsException(String msg){
		super(msg);
	}
}
