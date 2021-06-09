package com.yuhan.car.service;

import com.yuhan.car.entity.Car;
import java.util.List;
import java.util.Optional;

/**
 * @author yuhan
 * @date 07.05.2021 - 10:58
 * @purpose
 */
public interface CarService {

    public List<Car> findAll();

    public Car findById(int CarUid);

    public String createCar(Car car);

    public void deleteCar(int CarUid);

    public String update(Car car);
}
