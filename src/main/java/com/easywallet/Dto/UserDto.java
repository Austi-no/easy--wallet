package com.easywallet.Dto;

import com.easywallet.model.Permission;
import com.easywallet.model.Role;
import com.easywallet.model.WalletAccount;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String transactionPin;

    private String phone;

    private String username;

    private String referralCode;

    private Date dateCreated;

    private WalletAccount walletAccount;

    private List<Role> roles;

    private List<Permission> permissions;


    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public WalletAccount getWalletAccount() {
        return walletAccount;
    }

    public void setWalletAccount(WalletAccount walletAccount) {
        this.walletAccount = walletAccount;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(address, userDto.address) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(transactionPin, userDto.transactionPin) &&
                Objects.equals(phone, userDto.phone) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(referralCode, userDto.referralCode) &&
                Objects.equals(dateCreated, userDto.dateCreated) &&
                Objects.equals(walletAccount, userDto.walletAccount) &&
                Objects.equals(roles, userDto.roles) &&
                Objects.equals(permissions, userDto.permissions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, address, email, transactionPin, phone, username, referralCode, dateCreated, walletAccount, roles, permissions);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", transactionPin='" + transactionPin + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", dateCreated=" + dateCreated +
                ", walletAccount=" + walletAccount +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
