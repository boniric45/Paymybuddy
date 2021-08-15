package fr.boniric.paymybuddy.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "payment_type_id")
    private String paymentTypeId;

    @Column(name = "users_id")
    private String payerAgent;

    @Column(name = "contact_id")
    private Long beneficiary;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "transaction_amount")
    private float transactionAmount;

    @Column(name = "transaction_commission_amount")
    private float transactionCommissionAmount;

}
