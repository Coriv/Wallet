package com.microservices.wallet.controller;

import com.microservices.wallet.dto.WalletDto;
import com.microservices.wallet.dto.WithdrawDto;
import com.microservices.wallet.exception.NotEnoughFoundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import com.microservices.wallet.mapper.WalletMapper;
import com.microservices.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<WalletDto> fetchCashWalletByUserId(@PathVariable Long userId) throws WalletNotFoundException {
        var wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

    @PutMapping("/withdraw/{userId}")
    public ResponseEntity<WalletDto> withdrawMoney(
            @PathVariable Long userId,
            @RequestBody WithdrawDto withDrawDto) throws WalletNotFoundException, NotEnoughFoundsException {
        var wallet = walletService.withdrawMoney(userId, withDrawDto);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

    @PutMapping("/deposit/{userId}")
    public ResponseEntity<WalletDto> depositMoney(
            @PathVariable Long userId,
            @RequestParam BigDecimal quantity) throws WalletNotFoundException {
        var wallet = walletService.depositMoney(userId, quantity);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }
}
