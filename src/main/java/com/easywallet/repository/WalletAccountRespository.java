package com.easywallet.repository;

import com.easywallet.model.WalletAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletAccountRespository extends JpaRepository<WalletAccount, Long> {
}
