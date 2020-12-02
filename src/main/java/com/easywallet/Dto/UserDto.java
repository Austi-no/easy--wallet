package com.easywallet.Dto;

import com.easywallet.model.Permission;
import com.easywallet.model.Role;

import java.util.Date;
import java.util.List;

public class UserDto {
    private Long id;

    private String surname;

    private String middleName;

    private String lastName;

    private String address;

    private String email;

    private String transactionPin;

    private String phone;

    private String username;

    private Date dateCreated;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != null ? !id.equals(userDto.id) : userDto.id != null) return false;
        if (surname != null ? !surname.equals(userDto.surname) : userDto.surname != null) return false;
        if (middleName != null ? !middleName.equals(userDto.middleName) : userDto.middleName != null) return false;
        if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
        if (address != null ? !address.equals(userDto.address) : userDto.address != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (transactionPin != null ? !transactionPin.equals(userDto.transactionPin) : userDto.transactionPin != null)
            return false;
        if (phone != null ? !phone.equals(userDto.phone) : userDto.phone != null) return false;
        if (username != null ? !username.equals(userDto.username) : userDto.username != null) return false;
        if (dateCreated != null ? !dateCreated.equals(userDto.dateCreated) : userDto.dateCreated != null) return false;
        if (roles != null ? !roles.equals(userDto.roles) : userDto.roles != null) return false;
        return permissions != null ? permissions.equals(userDto.permissions) : userDto.permissions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (transactionPin != null ? transactionPin.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", transactionPin='" + transactionPin + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", dateCreated=" + dateCreated +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
