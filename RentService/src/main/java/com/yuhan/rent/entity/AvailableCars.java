package com.yuhan.rent.entity;

import com.yuhan.car.entity.Car;
import lombok.Data;
import javax.persistence.*;


/**
 * @author yuhan
 * @date 07.05.2021 - 17:11
 * @purpose
 */
@Entity
@Data
@Table(name = "availableCars", indexes = {
        @Index(name = "idx_available_car_uid", columnList = "car_uid"),
        @Index(name = "idx_available_office_uid", columnList = "office_uid"),
        @Index(name = "idx_available_registration_number", columnList = "registration_number")
})
public class AvailableCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "office_uid", nullable = false)
    private Integer officeUid;

//(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "car_uid", foreignKey = @ForeignKey(name = "fk_available_car_uid"), nullable = false)
    private Car car;

    @Column(name = "registration_number", nullable = false, length = 40, unique = true)
    private Integer registrationNumber;

    @Column(nullable = false)
    private String availabilitySchedules;

    public AvailableCars() {
    }

    public AvailableCars(Car car,Integer officeUid, Integer registrationNumber, String availabilitySchedules) {
        this.officeUid = officeUid;
        this.car = car;
        this.registrationNumber = registrationNumber;
        this.availabilitySchedules = availabilitySchedules;
    }

    public AvailableCars(Car car, String availabilitySchedules) {
        this.car = car;
        this.availabilitySchedules = availabilitySchedules;
    }

    public AvailableCars(Car car) {
        this.car = car;
    }

    public AvailableCars(Integer registrationNumber, String availabilitySchedules) {
        this.registrationNumber = registrationNumber;
        this.availabilitySchedules = availabilitySchedules;
    }
}
