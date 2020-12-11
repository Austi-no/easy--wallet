package com.easywallet.repository;

import com.easywallet.model.ZlogWalletAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZlogWalletAccountRepository extends JpaRepository <ZlogWalletAccount, Long> {
}
