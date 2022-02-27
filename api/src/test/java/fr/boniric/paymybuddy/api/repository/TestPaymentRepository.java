package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.PaymentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestPaymentRepository {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void testTypePayment() {
        List<PaymentType> paymentTypeList = (List<PaymentType>) paymentRepository.findAll();
        Assertions.assertEquals(3, paymentTypeList.size());
    }

}
