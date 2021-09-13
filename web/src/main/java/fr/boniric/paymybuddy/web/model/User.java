package fr.boniric.paymybuddy.web.model;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private int phone;

    private String password;

    private String address;

    private String zip;

    private String city;

    private double balance;

    private String swift;

    private String iban;

    private String roles = "user";

}
