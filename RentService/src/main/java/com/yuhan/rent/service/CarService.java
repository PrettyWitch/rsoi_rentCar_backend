package com.yuhan.rent.service;

import com.yuhan.car.entity.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yuhan
 * @date 07.05.2021 - 10:26
 * @purpose
 */
@FeignClient(value = "car-service", url = "localhost:8181")
public interface CarService {
    // 通过carUid获得car实体
    @RequestMapping(value = "/cars/{carUid}", method = RequestMethod.GET)
    public Car findByCarUid(@PathVariable int carUid);

}
