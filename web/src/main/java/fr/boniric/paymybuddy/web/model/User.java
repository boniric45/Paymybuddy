package fr.boniric.paymybuddy.web.model;

import lombok.Data;

import java.util.Date;
@Data
public class User {

    private Integer id;

    private Date registrationDate;

    private String roles;

    private String password;

    private String lastname;

    private String firstname;

    private String address;

    private String zip;

    private String city;

    private int phone;

    private String email;

    private long balance;

    private String iban;

    private String swift;

}
