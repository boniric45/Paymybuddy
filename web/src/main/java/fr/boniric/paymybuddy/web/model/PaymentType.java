package fr.boniric.paymybuddy.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentType {

    private int id;
    private String rib;
    private String account;

}
