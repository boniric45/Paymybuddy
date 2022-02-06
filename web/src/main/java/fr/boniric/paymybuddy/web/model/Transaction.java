package fr.boniric.paymybuddy.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {

    private Integer transactionId;
    private int paymentTypeId;
    private Integer userId;
    private Integer userReceiverId;
    private Date date = new Date();
    private double transactionAmount;
    private double transactionCommissionAmount;
    private double transactionTotalAmount;
    private String listContact;
    private String description;

}
