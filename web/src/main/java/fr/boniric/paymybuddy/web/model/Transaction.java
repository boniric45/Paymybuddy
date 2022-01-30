package fr.boniric.paymybuddy.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {

    private Integer transactionId;
    private int paymentTypeId;
    private int userId;
    private int buddyId;
    private int contactId;
    private Date date = new Date();
    private double transactionAmount;
    private double transactionCommissionAmount;
    private double transactionTotalAmount;
    private String listEmail;
    private String description;

}
