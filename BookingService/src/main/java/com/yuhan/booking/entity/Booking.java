package com.yuhan.booking.entity;

import com.yuhan.car.entity.Car;
import lombok.Data;

import javax.persistence.*;

/**
 * @author yuhan
 * @date 08.05.2021 - 15:50
 * @purpose
 */

@Entity
@Data
@Table(name = "booking", indexes = {
        @Index(name = "idx_booking_car_uid", columnList = "car_uid"),
        @Index(name = "idx_booking_booking_uid", columnList = "booking_uid"),
        @Index(name = "idx_booking_user_uid", columnList = "user_uid"),
        @Index(name = "idx_booking_takeOffice_uid", columnList = "takeOffice_uid"),
        @Index(name = "idx_booking_returnOffice_uid", columnList = "returnOffice_uid"),
        @Index(name = "idx_booking_payment_uid", columnList = "payment_uid")
})
public class Booking {
    //id注解指的是主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_uid",foreignKey = @ForeignKey(name = "fk_booking_car_uid"), nullable = false)
    private Car car;

    @Column(name = "booking_uid", nullable = false,unique = true)
    private Integer bookingUid;

    @Column(name = "user_uid", nullable = false)
    private Integer userUid;

    @Column(name = "payment_uid", nullable = false)
    private Integer paymentUid;

    @Column(nullable = false)
    private String bookingPeriod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(name = "takeOffice_uid", nullable = false)
    private Integer takeFromOffice;

    @Column(name = "returnOffice_uid", nullable = false)
    private Integer returnToOffice;

    public Booking(Integer id, Car car, Integer bookingUid, Integer userUid, Integer paymentUid, String bookingPeriod, BookingStatus bookingStatus, Integer takeFromOffice, Integer returnToOffice) {
        this.id = id;
        this.car = car;
        this.bookingUid = bookingUid;
        this.userUid = userUid;
        this.paymentUid = paymentUid;
        this.bookingPeriod = bookingPeriod;
        this.bookingStatus = bookingStatus;
        this.takeFromOffice = takeFromOffice;
        this.returnToOffice = returnToOffice;
    }

    public Booking() {
    }


    public Booking(Car car, Integer bookingUid, Integer userUid, Integer paymentUid, String bookingPeriod, BookingStatus bookingStatus, Integer takeFromOffice, Integer returnToOffice) {
        this.car = car;
        this.bookingUid = bookingUid;
        this.userUid = userUid;
        this.paymentUid = paymentUid;
        this.bookingPeriod = bookingPeriod;
        this.bookingStatus = bookingStatus;
        this.takeFromOffice = takeFromOffice;
        this.returnToOffice = returnToOffice;
    }
}
