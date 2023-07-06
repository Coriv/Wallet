package com.microservices.wallet;

import com.microservices.wallet.dto.TransactionDto;
import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.exception.NotEnoughFoundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import com.microservices.wallet.repository.WalletDao;
import com.microservices.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTestSuite {
    @InjectMocks
    private WalletService service;
    @Mock
    private WalletDao walletDao;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setUserId(1L);
        wallet.setCurrency("BTC");
        wallet.setQuantity(BigDecimal.TEN);
    }

    @Test
    public void testGetWalletByUserId() throws WalletNotFoundException {
        // given
        when(walletDao.findByUserId(anyLong())).thenReturn(Optional.of(wallet));
        // when
        Wallet result = service.getWalletByUserId(1L);
        // then
        assertEquals(wallet, result);
    }

    @Test
    public void testWithdrawMoneySufficientFunds() throws WalletNotFoundException, NotEnoughFoundsException {
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal withdrawAmount = BigDecimal.valueOf(50);
        wallet.setQuantity(currentBalance);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setUserId(1L);
        transactionDto.setQuantity(withdrawAmount);

        when(walletDao.findByUserId(anyLong())).thenReturn(Optional.of(wallet));
        when(walletDao.save(wallet)).thenReturn(wallet);
        // when
        Wallet result = service.withdrawMoney(1L, transactionDto);
        System.out.println(result.getQuantity());
        // then
        assertEquals(wallet, result);
        assertEquals(currentBalance.subtract(withdrawAmount), result.getQuantity());
        verify(walletDao, times(1)).save(wallet);
    }

    @Test
    public void testWithdrawMoneyNotEnoughFounds() {
        // given
        Long userId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal withdrawAmount = BigDecimal.valueOf(150);
        wallet.setQuantity(currentBalance);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setUserId(userId);
        transactionDto.setQuantity(withdrawAmount);

        when(walletDao.findByUserId(userId)).thenReturn(Optional.of(wallet));
        // when & then
        assertThrows(NotEnoughFoundsException.class, () -> service.withdrawMoney(userId, transactionDto));
    }


    @Test
    public void testDepositMoney() throws WalletNotFoundException {
        // given
        Long userId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal depositAmount = BigDecimal.valueOf(50);
        wallet.setQuantity(currentBalance);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setUserId(userId);
        transactionDto.setQuantity(depositAmount);

        when(walletDao.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(walletDao.save(wallet)).thenReturn(wallet);
        // when
        Wallet result = service.depositMoney(transactionDto);
        // then
        assertEquals(wallet, result);
        assertEquals(currentBalance.add(depositAmount), result.getQuantity());
        verify(walletDao, times(1)).save(wallet);
    }
}
