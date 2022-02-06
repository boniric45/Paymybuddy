package fr.boniric.paymybuddy.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "transaction", schema = "public")
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    @Column(name = "payment_type_id")
    private int paymentTypeId;

    @Column(name = "users_id", nullable = false)
    private Integer userId;

    @Column(name = "transaction_user_receiver_id", nullable = false)
    private Integer userReceiverId;

    @Column(name = "transaction_date")
    private LocalDate date = LocalDate.now(ZoneId.of("Europe/Paris"));

    @Column(name = "transaction_amount")
    private double transactionAmount;

    @Column(name = "transaction_commission_amount")
    private double transactionCommissionAmount;

    @Column(name = "transaction_total_payment")
    private double transactionTotalAmount;

    @Column(name = "transaction_listcontact")
    private String listContact;

    @Column(name = "transaction_description")
    private String description;

}