package fr.boniric.paymybuddy.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {

     Integer transactionId;
     int paymentTypeId;
     Integer userId;
     Integer userReceiverId;
     Date date = new Date();
     double transactionAmount;
     double transactionCommissionAmount;
     double transactionTotalAmount;
     String listContact;
     String description;

}
