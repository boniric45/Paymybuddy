package fr.boniric.paymybuddy.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
@NoArgsConstructor
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


}
