package com.yuhan.payment.repository;

import com.yuhan.payment.entity.Payment;
import com.yuhan.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:48
 * @purpose
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    public Optional<Payment> findByPaymentUid(int paymentUid);

    @Query(value = "update Payment set status = :status where paymentUid = :paymentUid")
    @Modifying
    public void updatePayment(@Param("paymentUid")int paymentUid, @Param("status") PaymentStatus status);
}
