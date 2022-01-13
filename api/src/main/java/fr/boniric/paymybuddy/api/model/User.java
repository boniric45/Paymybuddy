package fr.boniric.paymybuddy.api.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(name = "users_password")
    private String password;

    @Column(name = "users_lastname")
    private String lastname;

    @Column(name = "users_firstname")
    private String firstname;

    @Column(name = "users_address")
    private String address;

    @Column(name = "users_zip")
    private String zip;

    @Column(name = "users_city")
    private String city;

    @Column(name = "users_phone")
    private String phone;

    @Column(name = "users_email")
    private String email;

    @Column(name = "users_balance")
    private double balance;

    @Column(name = "users_iban")
    private String iban;

    @Column(name = "users_swift")
    private String swift;

    @Column(name = "users_roles")
    private String roles = "user";

    public User(Long id, String password, String lastname, String firstname, String address, String zip, String city, String phone, String email, double balance, String iban, String swift, String roles) {
        this.id = id;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.balance = balance;
        this.iban = iban;
        this.swift = swift;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
