package com.microservices.wallet.repository;

import com.microservices.wallet.domain.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface WalletDao extends CrudRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);
}
