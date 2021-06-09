package com.yuhan.payment.service;

import com.yuhan.payment.entity.Payment;
import com.yuhan.payment.entity.PaymentStatus;
import com.yuhan.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:50
 * @purpose
 */
@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepository paymentRepository;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentByPaymentUid(int paymentUid) {
        return paymentRepository.findByPaymentUid(paymentUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Payment noy found for paymentUid %s",paymentUid)));
    }

    @Transactional
    @Override
    public Payment paymentRequest(Payment payment) {
        logger.info("Process payment request: {}",payment);
        int paymentUid = (int) (Math.random() * 100);
        int price = (int)(Math.random() * 1000);
        payment.setPaymentUid(paymentUid);
        payment.setPrice(price);
        paymentRepository.save(payment);
        return payment;
    }

    @Transactional
    @Override
    public void update(int paymentUid, PaymentStatus status) {
        paymentRepository.updatePayment(paymentUid, status);
    }


}
