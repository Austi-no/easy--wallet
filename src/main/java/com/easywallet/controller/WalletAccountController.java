package com.easywallet.controller;


import com.easywallet.constants.ApiResponse;
import com.easywallet.constants.CustomMessages;
import com.easywallet.exceptions.RecordNotFoundException;
import com.easywallet.model.User;
import com.easywallet.model.WalletAccount;
import com.easywallet.model.ZlogWalletAccount;
import com.easywallet.service.Mail.Mail;
import com.easywallet.service.Mail.MailService;
import com.easywallet.service.WalletService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.round;

@RestController
@RequestMapping("/wallet")
public class WalletAccountController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private MailService mailService;

    @ApiOperation("To return all customer wallet Details")
    @GetMapping("/wallet")
    public List<WalletAccount> getWallets() {
        return walletService.getWalletAccountRespository().findAll();
    }

    @ApiOperation("To return a user wallet Details")
    @GetMapping("/wallets/{id}")
    public Optional<WalletAccount> getAccount(@PathVariable("id") Long id) {
        return walletService.getWalletAccountRespository().findById(id);
    }

    @ApiOperation("To return a user wallet Details")
    @GetMapping("/{accountNo}")
    public Optional<User> getAccountNo(@PathVariable("accountNo") String accountNo) {
        return walletService.getUserDetailRepository().findByWalletAccountId_AccountNumber(accountNo);
    }

    @ApiOperation("To return a user wallet Details")
    @GetMapping("/getBalance")
    public ResponseEntity getBalance(@RequestParam(value = "id", required = false) Long id) {
        Optional<User> user = walletService.getUserDetailRepository().findUsersById(id);
        if (user.isPresent()) {

            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, user.get().getWalletAccountId()));
        } else {
            throw new RecordNotFoundException("No user Found!");
        }
    }

    @ApiOperation("To Deposit to a user wallet")
    @PostMapping("/Deposit")
    public ResponseEntity makeDeposit(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "depositAmount", required = false) double depositAmount,
            @RequestParam(value = "transactionPin", required = false) String transactionPin,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod
    ) {
        double serviceCharge = 0.0;
        Optional<User> user = walletService.getUserDetailRepository().findUsersById(userId);
        if (user.get().getTransactionPin().equalsIgnoreCase(transactionPin) && user.isPresent()) {
            double userWalletBalance = user.get().getWalletAccountId().getAccountBalance();
            double depositAmountAfterCharge = depositAmount - serviceCharge;
            double newBalance = userWalletBalance + depositAmountAfterCharge;
            user.get().getWalletAccountId().setAccountBalance(newBalance);
            user.get().getWalletAccountId().setLastDeposit(depositAmount);
            user.get().getWalletAccountId().setLastPaymentMethod(paymentMethod);
            user.get().getWalletAccountId().setLastServiceCharge(serviceCharge);

            User newUser = walletService.getUserDetailRepository().save(user.get());

//      LOG FOR GETTING DEPOSIT
            ZlogWalletAccount log = new ZlogWalletAccount();
            log.setService("Deposit");
            log.setDescription("A deposit was made to the account");
            log.setServiceCharge(serviceCharge);
            log.setAmount(depositAmount);
            Double currentUserBaance = user.get().getWalletAccountId().getAccountBalance();
            log.setCurrentAccountBalance(currentUserBaance);
            log.setDateCreated(new Date());
            log.setStatus("Completed!");
            log.setUserId(user.get());
            walletService.getZlogWalletAccountRepository().save(log);

            if (user.get().getEmail() != null) {
                Mail mail = new Mail();
                mail.setMailFrom("noreply@easywallet.com");
                mail.setMailTo(user.get().getEmail());
                mail.setMailSubject("Easy Wallet | Deposit [Credit: NGN" + depositAmount+ "]");
                mail.setMailContent("Hi" + user.get().getLastName() + " " + user.get().getFirstName() + "  " + "\n\n"
                        + "Your Account has been credited with "+depositAmount+"\n\nThanks for your patronage\n You can visit us @ www.easywallet.com");
                mailService.sendEmail(mail);
            }

            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newUser));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.incorrectPin));
        }
    }

    @ApiOperation("To withdraw from wallet Account")
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "amountToWithdraw", required = false) double amountToWithdraw,
            @RequestParam(value = "transactionPin", required = false) String transactionPin,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod
    ) {
        //    ------------------------GET THE SERVICE CHARGE OF WITHDRAWAL SERVICE----------------------------------------------
        double amount = amountToWithdraw;
        double percentageAmount = 0.1;
        double calculatedPercentage = amount * percentageAmount;
        double serviceCharge = calculatedPercentage / 100;

//        ---------------------GET THE USER ACCOUNT AND UPDATE THE USER WALLET INFO--------------------------------------------
        Optional<User> user = walletService.getUserDetailRepository().findUsersById(userId);
        if (user.get().getTransactionPin().equalsIgnoreCase(transactionPin)) {
            double userWalletBalance = user.get().getWalletAccountId().getAccountBalance();
            if (userWalletBalance > 0 && userWalletBalance > amountToWithdraw) {
                double amountAfterCharge = amountToWithdraw + serviceCharge;
                double newBalance = round((userWalletBalance - amountAfterCharge) * 100.0) / 100.0;
                user.get().getWalletAccountId().setAccountBalance(newBalance);
                user.get().getWalletAccountId().setLastDeposit(0);
                user.get().getWalletAccountId().setLastPaymentMethod(paymentMethod);
                user.get().getWalletAccountId().setLastServiceCharge(serviceCharge);
                User newUser = walletService.getUserDetailRepository().save(user.get());
                ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newUser));
                User newUser1 = walletService.getUserDetailRepository().save(user.get());
                ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newUser1));

                ZlogWalletAccount log = new ZlogWalletAccount();
                String action = "Withdrawal";
                String description = "A Withdrawal of " + amountToWithdraw + " was made from your Account";
                log.setAmount(amountToWithdraw);
                log.setCurrentAccountBalance(newUser.getWalletAccountId().getAccountBalance());
                log.setDateCreated(new Date());
                log.setDescription(description);
                log.setService(action);
                log.setStatus("Completed!");
                log.setServiceCharge(serviceCharge);
                log.setUserId(user.get());

                walletService.getZlogWalletAccountRepository().save(log);

                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.InsufficientFunds));
            }
        } else {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.IncorrectPin));
        }


    }


    @ApiOperation("To withdraw from wallet Account")
    @PostMapping("/transfer")
    public ResponseEntity transfer(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "recipientUserId", required = false) Long recipientUserId,
            @RequestParam(value = "recipientAcctNo", required = false) String recipientAcctNo,
            @RequestParam(value = "amountToTransfer", required = false) double amountToTransfer,
            @RequestParam(value = "transactionPin", required = false) String transactionPin
    ) {
        //    ------------------------GET THE SERVICE CHARGE OF WITHDRAWAL SERVICE----------------------------------------------
        double amount = amountToTransfer;
        double percentageAmount = 0.1;
        double calculatedPercentage = amount * percentageAmount;
        double serviceCharge = calculatedPercentage / 100;


        Optional<User> user = walletService.getUserDetailRepository().findUsersById(userId);
        double currentUserBalance = user.get().getWalletAccountId().getAccountBalance();
        if (user.get().getTransactionPin().equalsIgnoreCase(transactionPin) && user.isPresent()) {
            if (amountToTransfer < currentUserBalance) {
                double oldBalance = user.get().getWalletAccountId().getAccountBalance();
                double balanceAfterTransfer = oldBalance - amountToTransfer;
                double newBalance = round((balanceAfterTransfer - serviceCharge) * 100.0) / 100.0;
                user.get().getWalletAccountId().setAccountBalance(newBalance);
                walletService.getUserDetailRepository().save(user.get());

                Optional<User> recipient = walletService.getUserDetailRepository().findUsersById(recipientUserId);
                if (recipient.isPresent() && recipient.get().getWalletAccountId().getAccountNumber().equalsIgnoreCase(recipientAcctNo)) {
                    double oldBal = recipient.get().getWalletAccountId().getAccountBalance();
                    double balAfterTransfer = round((oldBal + amountToTransfer) * 100.0) / 100.0;
                    recipient.get().getWalletAccountId().setAccountBalance(balAfterTransfer);
                    walletService.getUserDetailRepository().save(recipient.get());
                }

                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.InsufficientFunds));
            }


        } else {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.IncorrectPin));
        }
    }

    @ApiOperation("To buy airtime from wallet Account")
    @PostMapping("/buyAirtime")
    public ResponseEntity buyAirtime(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "phoneNo", required = false) String phoneNo,
            @RequestParam(value = "network", required = false) String network,
            @RequestParam(value = "amount", required = false) double amount,
            @RequestParam(value = "transactionPin", required = false) String transactionPin
    ) {
        String alphabet = "0123456789";
        // create random string builder
        StringBuilder sb = new StringBuilder();
        // create an object of Random class
        Random random = new Random();
        // specify length of random string
        int length = 12;
        for (int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(alphabet.length());
            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);
            // append the character to string builder
            sb.append(randomChar);
        }
        String generatedPin = sb.toString();
        String subOne = generatedPin.substring(0, 4);
        String subTwo = generatedPin.substring(4, 8);
        String subThree = generatedPin.substring(8, 12);

        String rechargeCard=network+"--"+subOne+"-"+subTwo+"-"+subThree;



        Optional<User> user = walletService.getUserDetailRepository().findUsersById(userId);
        if (user.get().getTransactionPin().equalsIgnoreCase(transactionPin) && user.isPresent()) {
            double userWalletBalance = user.get().getWalletAccountId().getAccountBalance();

            if (userWalletBalance > amount && userWalletBalance > 0) {
                double newbalance = userWalletBalance - amount;
                user.get().getWalletAccountId().setAccountBalance(newbalance);
                walletService.getUserDetailRepository().save(user.get());

                ZlogWalletAccount log = new ZlogWalletAccount();
                String action = "Buy Airtime";
                String description = "Recharge card was generated for  "+phoneNo +", Card-PIN: "+rechargeCard;
                log.setAmount(amount);
                log.setCurrentAccountBalance(newbalance);
                log.setDateCreated(new Date());
                log.setDescription(description);
                log.setService(action);
                log.setStatus("Completed!");
                log.setServiceCharge(0.0);
                log.setUserId(user.get());

                walletService.getZlogWalletAccountRepository().save(log);
                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, rechargeCard));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.InsufficientFunds));
            }

        } else {

            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.incorrectPin));
        }


    }


}


