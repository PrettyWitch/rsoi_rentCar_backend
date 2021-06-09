package com.yuhan.car.repository;

import com.yuhan.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author yuhan
 * @date 03.05.2021 - 19:36
 * @purpose
 */
//接口 继承JpaRepository<实体类类型，主键类型>
public interface CarRepository extends JpaRepository<Car,Integer> {

    public Optional<Car> findByCarUid(int carUid);
}
