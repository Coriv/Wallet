package com.microservices.wallet.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletDto {

    private Long id;
    @Builder.Default
    private String currency = "$ USD";
    @PositiveOrZero
    @Builder.Default
    private BigDecimal quantity = BigDecimal.valueOf(0);
}
