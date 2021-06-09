package com.yuha.report.service;

import com.yuhan.booking.entity.Booking;
import com.yuhan.car.entity.Car;
import com.yuhan.rent.entity.Office;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yuhan
 * @date 06.06.2021 - 15:20
 * @purpose
 */
@FeignClient(value = "booking-service", url = "localhost:8184")
public interface BookingService {

    @RequestMapping(value = "/booking/booking-by-models", method = RequestMethod.POST)
    public List<Booking> byModel(@RequestParam String model);

    @RequestMapping(value = "/booking/booking-by-offices", method = RequestMethod.POST)
    public List<Booking> byOffice(@RequestParam int officeUid);
}
