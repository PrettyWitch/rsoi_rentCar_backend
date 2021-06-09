package com.yuhan.payment.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yuhan
 * @date 09.05.2021 - 12:58
 * @purpose
 */
@Entity
@Data
@Table(name = "payment")
public class Payment {

    //id注解指的是主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_uid", nullable = false, length = 40, unique = true)
    private Integer paymentUid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private Integer price;

    public Payment(Integer id, Integer paymentUid, PaymentStatus status, Integer price) {
        this.id = id;
        this.paymentUid = paymentUid;
        this.status = status;
        this.price = price;
    }

    public Payment() {
    }

    public Payment(PaymentStatus status) {
        this.status = status;
    }

    public Payment(Integer paymentUid) {
        this.paymentUid = paymentUid;
    }

    public Payment(Integer paymentUid, PaymentStatus status) {
        this.paymentUid = paymentUid;
        this.status = status;
    }
}
