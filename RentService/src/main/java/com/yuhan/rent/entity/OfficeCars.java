//package com.yuhan.rent.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
///**
// * @author yuhan
// * @date 01.06.2021 - 15:45
// * @purpose 记录 Officeuid和carUid，registration_number和可用状态
// */
//@Entity
//@Data
//@Table(name = "officeCars", indexes = {
//        @Index(name = "idx_officeCars_office_uid", columnList = "office_uid"),
//        @Index(name = "idx_officeCars_car_uid", columnList = "car_uid"),
//        @Index(name = "idx_officeCars_registration_number", columnList = "registration_number")
//})
//public class OfficeCars {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "office_uid",nullable = false)
//    private Integer officeUid;
//
//    @Column(name = "car_uid",nullable = false)
//    private Integer carUid;
//
//    @Column(name = "registration_number", nullable = false)
//    private Integer registrationNumber;
//
//    @Column(nullable = false)
//    private String availabilitySchedules;
//
//    public OfficeCars(Integer id, Integer officeUid, Integer carUid, Integer registrationNumber, String availabilitySchedules) {
//        this.id = id;
//        this.officeUid = officeUid;
//        this.carUid = carUid;
//        this.registrationNumber = registrationNumber;
//        this.availabilitySchedules = availabilitySchedules;
//    }
//
//    public OfficeCars() {
//    }
//
//    public OfficeCars(Integer registrationNumber, String availabilitySchedules) {
//        this.registrationNumber = registrationNumber;
//        this.availabilitySchedules = availabilitySchedules;
//    }
//}
