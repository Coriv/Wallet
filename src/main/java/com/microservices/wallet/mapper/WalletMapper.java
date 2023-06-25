package com.microservices.wallet.mapper;

import com.microservices.wallet.domain.Wallet;
import com.microservices.wallet.dto.WalletDto;
import com.microservices.wallet.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper {

    private final WalletDao walletDao;

    public Wallet mapToWallet(WalletDto walletDto) {
        Wallet wallet;
        wallet = walletDao.findById(walletDto.getId()).orElse(new Wallet());
        wallet.setCurrency(wallet.getCurrency());
        wallet.setQuantity(walletDto.getQuantity());
        return wallet;
    }

    public WalletDto walletDto(Wallet wallet) {
        return WalletDto.builder()
                .id(wallet.getId())
                .currency(wallet.getCurrency())
                .quantity(wallet.getQuantity())
                .build();
    }
}
