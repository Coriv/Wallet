package com.microservices.wallet.service;

import com.microservices.wallet.dto.TransactionDto;
import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.exception.NotEnoughFoundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import com.microservices.wallet.repository.WalletDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletDao walletDao;

    public Wallet getWalletByUserId(Long userId) throws WalletNotFoundException {
        return walletDao.findByUserId(userId).orElseThrow(WalletNotFoundException::new);
    }

    public Wallet withdrawMoney(Long userId, TransactionDto transactionDto) throws WalletNotFoundException, NotEnoughFoundsException {
        var wallet = walletDao.findByUserId(userId).orElseThrow(WalletNotFoundException::new);
        if (wallet.getQuantity().compareTo(transactionDto.getQuantity()) < 0) {
            log.error("Not enough funds to process withdraw for user ID: " + userId +
                    ". Actual account balance: " + wallet.getQuantity());
            throw new NotEnoughFoundsException();
        }
        wallet.setQuantity(wallet.getQuantity().subtract(transactionDto.getQuantity()));
        //todo saveWithdrawToHistory(user, withDrawDto.getAccountNumber(), withDrawDto.getQuantity());
        return walletDao.save(wallet);
    }

    public Wallet depositMoney(TransactionDto transactionDto) throws WalletNotFoundException {
        long userId = transactionDto.getUserId();
        var wallet = walletDao.findByUserId(userId).orElseThrow(WalletNotFoundException::new);
         //todo add external service quantityUSD = nbpService.exchangePlnToUsd(quantityPLN);
        wallet.setQuantity(wallet.getQuantity().add(transactionDto.getQuantity()));
        //todo add history saveDepositToHistory(user, quantityUSD, quantityPLN);
        return walletDao.save(wallet);
    }

    public Wallet createWallet(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        return walletDao.save(wallet);
    }
}
