package com.yuhan.rent.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yuhan
 * @date 06.05.2021 - 18:49
 * @purpose
 */
@Entity
@Data
@Table(name = "office")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "office_uid", nullable = false, length = 40, unique = true)
    private int officeUid;

    @Column(nullable = false)
    private String location;

    public Office() {
    }

    public Office(int officeUid) {
        this.officeUid = officeUid;
    }

    public Office(int officeUid, String location) {
        this.officeUid = officeUid;
        this.location = location;
    }

    public Office(String location) {
        this.location = location;
    }
}