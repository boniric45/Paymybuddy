package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.PaymentType;
import fr.boniric.paymybuddy.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Get All Payment Type
     * @return Iterable<PaymentType>
     */
    public Iterable<PaymentType> getPaymentTypeAll() {
        return paymentRepository.findAll();
    }
}
