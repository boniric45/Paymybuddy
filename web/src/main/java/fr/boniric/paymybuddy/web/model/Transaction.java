package fr.boniric.paymybuddy.web.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Transaction extends fr.boniric.paymybuddy.api.model.Transaction {

    private Integer transactionId;
    private int paymentTypeId;
    private int userId;
    private int contactId;
    private LocalDate date = LocalDate.now();
    private float transactionAmount;
    private double transactionCommissionAmount;
    private double transactionTotalAmount;
    private String listEmail;

}
