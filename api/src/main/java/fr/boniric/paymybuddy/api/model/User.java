package fr.boniric.paymybuddy.api.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(name = "users_registration_date")
    private Date registrationDate;

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
    private int phone;

    @Column(name = "users_email")
    private String email;

    @Column(name = "users_balance")
    private long balance;

    @Column(name = "users_iban")
    private String iban;

    @Column(name = "users_swift")
    private String swift;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
