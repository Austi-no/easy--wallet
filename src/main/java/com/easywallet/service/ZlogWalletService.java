package com.easywallet.service;


import com.easywallet.repository.UserDetailRepository;
import com.easywallet.repository.ZlogWalletAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ZlogWalletService {
    @Autowired
    private ZlogWalletAccountRepository zlogWalletAccountRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;


    public ZlogWalletAccountRepository getZlogWalletAccountRepository() {
        return zlogWalletAccountRepository;
    }

    public void setZlogWalletAccountRepository(ZlogWalletAccountRepository zlogWalletAccountRepository) {
        this.zlogWalletAccountRepository = zlogWalletAccountRepository;
    }

    public UserDetailRepository getUserDetailRepository() {
        return userDetailRepository;
    }

    public void setUserDetailRepository(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }
}
