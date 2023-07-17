package com.microservices.wallet.controller;

import com.microservices.wallet.dto.TransactionDto;
import com.microservices.wallet.dto.WalletDto;
import com.microservices.wallet.exception.NotEmptyWalletException;
import com.microservices.wallet.exception.NotEnoughFundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import com.microservices.wallet.mapper.WalletMapper;
import com.microservices.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/wallet")
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<WalletDto> fetchCashWalletByUserId(@PathVariable Long userId) throws WalletNotFoundException {
        var wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createWalletForNewUser(@RequestParam("userId") Long userId) {
        walletService.createWallet(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{userId}")
    public ResponseEntity<WalletDto> withdrawMoney(
            @PathVariable Long userId,
            @RequestBody TransactionDto transactionDto) throws WalletNotFoundException, NotEnoughFundsException {
        var wallet = walletService.withdrawMoney(userId, transactionDto);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

    @PutMapping("/deposit")
    public ResponseEntity<WalletDto> depositMoney(
            @RequestBody TransactionDto transactionDto) throws WalletNotFoundException {
        var wallet = walletService.depositMoney(transactionDto);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long userId) throws WalletNotFoundException, NotEmptyWalletException {
        walletService.deleteWallet(userId);
        return ResponseEntity.ok().build();
    }
}
