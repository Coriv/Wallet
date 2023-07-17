package com.microservices.wallet.service;

import com.microservices.wallet.dto.TransactionDto;
import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.exception.NotEmptyWalletException;
import com.microservices.wallet.exception.NotEnoughFundsException;
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

    public Wallet withdrawMoney(Long userId, TransactionDto transactionDto) throws WalletNotFoundException, NotEnoughFundsException {
        var wallet = walletDao.findByUserId(userId).orElseThrow(WalletNotFoundException::new);
        if (wallet.getQuantity().compareTo(transactionDto.getQuantity()) < 0) {
            log.error("Not enough funds to process withdraw for user ID: " + userId +
                    ". Actual account balance: " + wallet.getQuantity());
            throw new NotEnoughFundsException();
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

    public void deleteWallet(Long userId) throws WalletNotFoundException, NotEmptyWalletException {
        var wallet = walletDao.findByUserId(userId).orElseThrow(WalletNotFoundException::new);
        checkIfWalletIsEmpty(wallet);
        walletDao.deleteByUserId(userId);

    }

    private void checkIfWalletIsEmpty(Wallet wallet) throws NotEmptyWalletException {
        boolean isWalletEmpty = wallet.getQuantity().equals(BigDecimal.ZERO);
        if (!isWalletEmpty) {
            throw new NotEmptyWalletException();
        }
    }
}
