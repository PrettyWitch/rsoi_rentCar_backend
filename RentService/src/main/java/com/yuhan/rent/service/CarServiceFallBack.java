package com.yuhan.rent.service;

import com.yuhan.car.entity.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yuhan
 * @date 09.06.2021 - 21:24
 * @purpose
 */
@Component
public class CarServiceFallBack implements CarService{
    private static final Logger logger = LoggerFactory.getLogger(CarServiceFallBack.class);
    @Override
    public Car findByCarUid(int carUid) {
        logger.info("FallBack: CarServiceFallBack.findByCarUid");
        return new Car();
    }
}