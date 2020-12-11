package com.easywallet.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="WalletAccount")
public class WalletAccount implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name="account_number")
    private  String accountNumber;

    @Column(name="account_balance")
    private  double accountBalance;

    @Column(name="last_deposit")
    private double lastDeposit;

    @Column(name = "last_service_charge")
    private double lastServiceCharge;

    @Column(name="lastPaymnt_method")
    private String lastPaymentMethod;

    @JoinColumn(name="user_id", referencedColumnName="id")
    @OneToOne(cascade = {CascadeType.REFRESH})
    private User userId;

    public WalletAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getLastDeposit() {
        return lastDeposit;
    }

    public void setLastDeposit(double lastDeposit) {
        this.lastDeposit = lastDeposit;
    }

    public double getLastServiceCharge() {
        return lastServiceCharge;
    }

    public void setLastServiceCharge(double lastServiceCharge) {
        this.lastServiceCharge = lastServiceCharge;
    }

    public String getLastPaymentMethod() {
        return lastPaymentMethod;
    }

    public void setLastPaymentMethod(String lastPaymentMethod) {
        this.lastPaymentMethod = lastPaymentMethod;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WalletAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                ", lastDeposit=" + lastDeposit +
                ", lastServiceCharge=" + lastServiceCharge +
                ", lastPaymentMethod=" + lastPaymentMethod +
                ", userId=" + userId +
                '}';
    }

}
