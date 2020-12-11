package com.easywallet.controller;


import com.easywallet.model.WalletAccount;
import com.easywallet.model.ZlogWalletAccount;
import com.easywallet.service.ZlogWalletService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wallet/log")
public class ZlogWalletAccountController {

    @Autowired
    private ZlogWalletService zlogWalletService;

    @ApiOperation("To return all customer wallet Details")
    @GetMapping("/getAllDepositLog")
    public List<ZlogWalletAccount> getWallets(){
        return zlogWalletService.getZlogWalletAccountRepository().findAll();
    }



}
