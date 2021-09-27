package fr.boniric.paymybuddy.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "transaction", schema = "public")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    @Column(name = "payment_type_id")
    private int paymentTypeId;

    @Column(name = "users_id")
    private int userId;

    @Column(name = "contact_id")
    private int contactId;

    @Column(name = "transaction_date")
    private LocalDate date = LocalDate.now();

    @Column(name = "transaction_amount")
    private double transactionAmount;

    @Column(name = "transaction_commission_amount")
    private double transactionCommissionAmount;

    @Column(name = "transaction_total_payment")
    private double transactionTotalAmount;

    @Column(name = "transaction_listemail")
    private String listEmail;

    @Column(name = "transaction_description")
    private String description;

}
