package com.yuha.report.service;

import com.yuhan.booking.entity.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuhan
 * @date 09.06.2021 - 21:26
 * @purpose
 */
@Component
public class BookingServiceFallBack implements  BookingService{
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceFallBack.class);
    @Override
    public List<Booking> byModel(String model) {
        return null;
    }

    @Override
    public List<Booking> byOffice(int officeUid) {
        return null;
    }
}