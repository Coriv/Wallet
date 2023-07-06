package com.microservices.wallet;

import com.microservices.wallet.dto.WalletDto;
import com.microservices.wallet.entity.Wallet;
import com.microservices.wallet.mapper.WalletMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WalletMapperTestSuite {
    @Autowired
    private WalletMapper walletMapper;

    @Test
    public void testMapToWalletDto() {
        // given
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setCurrency("USD");
        wallet.setQuantity(BigDecimal.valueOf(100));
        wallet.setUserId(123L);
        // when
        WalletDto result = walletMapper.mapToWalletDto(wallet);
        // then
        assertEquals(wallet.getId(), result.getId());
        assertEquals(wallet.getCurrency(), result.getCurrency());
        assertTrue(wallet.getQuantity().compareTo(result.getQuantity()) == 0);
        assertEquals(wallet.getUserId(), result.getUserId());
    }

}
