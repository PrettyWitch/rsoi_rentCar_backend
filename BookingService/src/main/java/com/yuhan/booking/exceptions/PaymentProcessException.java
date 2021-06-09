package com.yuhan.booking.exceptions;

/**
 * @author yuhan
 * @date 04.06.2021 - 15:50
 * @purpose
 */
public class PaymentProcessException extends RuntimeException {
    public PaymentProcessException(String message) {
        super(message);
    }
}
