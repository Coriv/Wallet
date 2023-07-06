package com.microservices.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto {
    private Long userId;
    private String symbol;
    private BigDecimal quantity;

}
