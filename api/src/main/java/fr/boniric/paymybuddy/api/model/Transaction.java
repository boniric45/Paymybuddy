package fr.boniric.paymybuddy.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "transaction_date")
    private LocalDateTime date = LocalDateTime.now();

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

    public Transaction(Integer transactionId, int paymentTypeId, int userId, int contactId, LocalDateTime date, double transactionAmount, double transactionCommissionAmount, double transactionTotalAmount, String listEmail, String description) {
        this.transactionId = transactionId;
        this.paymentTypeId = paymentTypeId;
        this.userId = userId;
        this.contactId = contactId;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.transactionCommissionAmount = transactionCommissionAmount;
        this.transactionTotalAmount = transactionTotalAmount;
        this.listEmail = listEmail;
        this.description = description;
    }

    public Transaction() {
    }



}
