package com.yuhan.booking.service;

import com.yuhan.payment.entity.Payment;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author yuhan
 * @date 09.06.2021 - 21:19
 * @purpose
 */
@Component
public class PaymentServiceFallBack implements PaymentService{
    @Override
    public Payment newPayment(@Valid Payment payment) {
        return null;
    }

    @Override
    public void update(@Valid Payment payment) {

    }
}