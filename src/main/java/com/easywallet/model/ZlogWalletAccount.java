package com.easywallet.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="zLogWalletAccount")
public class ZlogWalletAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="service")
    private String service;

    @Column(name = "amount")
    private  double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "current_account_balance")
    private double currentAccountBalance;

    @Column(name = "service_charge")
    private double serviceCharge;

    @Column(name = "status")
    private String status;

    @JoinColumn(name="user_id", referencedColumnName="id")
    @OneToOne(cascade = {CascadeType.REFRESH})
    private User userId;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_created")
    private Date dateCreated;

    public ZlogWalletAccount() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public void setCurrentAccountBalance(double currentAccountBalance) {
        this.currentAccountBalance = currentAccountBalance;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZlogWalletAccount that = (ZlogWalletAccount) o;
        return Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.currentAccountBalance, currentAccountBalance) == 0 &&
                Double.compare(that.serviceCharge, serviceCharge) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(service, that.service) &&
                Objects.equals(description, that.description) &&
                Objects.equals(status, that.status) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, service, amount, description, currentAccountBalance, serviceCharge, status, userId, dateCreated);
    }

    @Override
    public String toString() {
        return "ZlogWalletAccount{" +
                "id=" + id +
                ", service='" + service + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", currentAccountBalance=" + currentAccountBalance +
                ", serviceCharge=" + serviceCharge +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
