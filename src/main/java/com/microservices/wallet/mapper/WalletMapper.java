package com.microservices.wallet.mapper;

import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.dto.WalletDto;
import com.microservices.wallet.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper {

    private final WalletDao walletDao;
    public WalletDto mapToWalletDto(Wallet wallet) {
        return WalletDto.builder()
                .id(wallet.getId())
                .currency(wallet.getCurrency())
                .quantity(wallet.getQuantity())
                .userId(wallet.getUserId())
                .build();
    }
}
