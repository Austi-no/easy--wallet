package com.easywallet.service;

import com.easywallet.repository.UserDetailRepository;
import com.easywallet.repository.WalletAccountRespository;
import com.easywallet.repository.ZlogWalletAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class WalletService {
@Autowired
private WalletAccountRespository walletAccountRespository;

@Autowired
private UserDetailRepository userDetailRepository;


@Autowired
private ZlogWalletAccountRepository zlogWalletAccountRepository;

    public ZlogWalletAccountRepository getZlogWalletAccountRepository() {
        return zlogWalletAccountRepository;
    }

    public void setZlogWalletAccountRepository(ZlogWalletAccountRepository zlogWalletAccountRepository) {
        this.zlogWalletAccountRepository = zlogWalletAccountRepository;
    }

    public WalletAccountRespository getWalletAccountRespository() {
        return walletAccountRespository;
    }

    public void setWalletAccountRespository(WalletAccountRespository walletAccountRespository) {
        this.walletAccountRespository = walletAccountRespository;
    }

    public UserDetailRepository getUserDetailRepository() {
        return userDetailRepository;
    }

    public void setUserDetailRepository(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }
}
