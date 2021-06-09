package com.yuhan.booking.service;

import com.yuhan.payment.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author yuhan
 * @date 02.06.2021 - 20:15
 * @purpose
 */
@FeignClient(value = "payment-service", url = "localhost:8185")
public interface PaymentService {

    @RequestMapping(value = "/payment/new", method = RequestMethod.POST)
    public Payment newPayment(@Valid @RequestBody Payment payment);

    @RequestMapping(value = "/payment/update", method = RequestMethod.POST)
    public void update(@Valid @RequestBody Payment payment);
}
