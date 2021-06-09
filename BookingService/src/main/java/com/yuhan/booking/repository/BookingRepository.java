package com.yuhan.booking.repository;

import com.yuhan.booking.entity.Booking;
import com.yuhan.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:25
 * @purpose
 */
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    public Booking findByBookingUid(int bookingUid);

    @Modifying
    @Query(value = "update Booking set bookingStatus = :bookingStatus where bookingUid= :bookingUid")
    public void updateBooking(@Param("bookingUid") int bookingUid, @Param("bookingStatus") BookingStatus bookingStatus);

//    @Query(value = "select b from Booking b where b.car.model = :model")
//    public Booking byModels(@Param("model") String model);

    @Query(value = "select b from Booking b where b.takeFromOffice = :officeUid")
    public List<Booking> byOffices(@Param("officeUid") int officeUid);
}
