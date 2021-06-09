package com.yuhan.booking.service;

import com.yuhan.car.entity.Car;
import com.yuhan.rent.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yuhan
 * @date 08.06.2021 - 14:45
 * @purpose
 */
@Component
public class CarServiceFallBack implements CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceFallBack.class);
    @Override
    public Car findByCarUid(int carUid) {
        logger.info("FallBack: CarServiceFallBack.findByCarUid");
        return new Car();
    }
}