package com.microservices.wallet;

import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.repository.WalletDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WalletDaoTestSuite {
    @Autowired
    private WalletDao walletDao;

    @Test
    void saveNewWalletAndFindByIdTest() {
        //given
        Wallet wallet = new Wallet();
        wallet.setUserId(1L);
        wallet.setCurrency("BTC");
        wallet.setQuantity(BigDecimal.TEN);
        //when
        walletDao.save(wallet);
        Wallet resultWallet = walletDao.findById(wallet.getId())
                .orElseThrow();
        //then
        assertEquals(wallet.getId(), resultWallet.getId());
        assertEquals(wallet.getCurrency(), resultWallet.getCurrency());
        assertTrue(wallet.getQuantity().compareTo(resultWallet.getQuantity()) == 0);
        assertEquals(wallet.getUserId(), resultWallet.getUserId());
        //cleanUp
        walletDao.deleteById(resultWallet.getId());
    }
}
