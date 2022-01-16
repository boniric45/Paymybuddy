package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.PaymentType;
import fr.boniric.paymybuddy.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    /**
     * @author Eric Boniface
     *
     * @return Payment Type
     */
    @GetMapping("/payment_type")
    public Iterable<PaymentType> getPayment() {
        return paymentService.getPaymentTypeAll();
    }

}
