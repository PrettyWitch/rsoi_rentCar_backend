package com.yuhan.booking.request;

import com.yuhan.payment.entity.Payment;
import lombok.Data;

/**
 * @author yuhan
 * @date 01.06.2021 - 20:32
 * @purpose
 */
@Data
public class BookingRequest {
    private int car_uid;
    private int taken_from_office;
    private int return_to_office;
    private String booking_period;
    private Payment payment_data;

    public BookingRequest(int car_uid, int taken_from_office, int return_to_office, String booking_period, Payment payment_data) {
        this.car_uid = car_uid;
        this.taken_from_office = taken_from_office;
        this.return_to_office = return_to_office;
        this.booking_period = booking_period;
        this.payment_data = payment_data;
    }

    public BookingRequest() {
    }
}
