package com.yuhan.payment.service;

import com.yuhan.payment.entity.Payment;
import com.yuhan.payment.entity.PaymentStatus;

import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:49
 * @purpose
 */
public interface PaymentService {

    public List<Payment> findAll();

    public Payment getPaymentByPaymentUid(int paymentUid);

    public Payment paymentRequest(Payment payment);

    public void update(int paymentUid, PaymentStatus status);




}
