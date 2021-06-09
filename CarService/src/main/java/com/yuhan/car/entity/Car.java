package com.yuhan.car.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author yuhan
 * @date 06.05.2021 - 13:31
 * @purpose
 */
@Entity
@Data
@Table(name = "car", indexes = {
        @Index(name = "idx_car_uid", columnList = "car_uid"),
})
public class Car {
    //id注解指的是主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "car_uid", nullable = false, length = 40, unique = true)
    private Integer carUid;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String power;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Car() {
    }

    public Car(Integer carUid) {
        this.carUid = carUid;
    }

    public Car(Integer id, String brand, String model, String power, Type type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.power = power;
        this.type = type;
    }

    public Car(String model) {
        this.model = model;
    }
}
