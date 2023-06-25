package com.microservices.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class WithdrawDto {
    private String accountNumber;
    private BigDecimal quantity;
}
