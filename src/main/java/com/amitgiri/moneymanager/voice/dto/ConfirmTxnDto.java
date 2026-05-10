package com.amitgiri.moneymanager.voice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmTxnDto {

    private String confirmationId;

    private boolean confirmed;
}