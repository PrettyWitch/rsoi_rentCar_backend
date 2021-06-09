package com.yuhan.rent.repository;

import com.yuhan.car.entity.Car;
import com.yuhan.rent.entity.AvailableCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

/**
 * @author yuhan
 * @date 09.05.2021 - 14:03
 * @purpose
 */
public interface AvailableCarRepository extends JpaRepository<AvailableCars, Integer> {

    public Optional<AvailableCars> findByRegistrationNumber(int RegistrationNumber);

    public List<AvailableCars> findByCar(Car car);

    public List<AvailableCars> findByOfficeUid(int officeUid);

    @Modifying
    @Query(value = "update AvailableCars set availabilitySchedules = :availabilitySchedules where registrationNumber = :registrationNumber")
    public void updateAvailableCars(@Param("registrationNumber") int registrationNumber, @Param("availabilitySchedules") String availabilitySchedules);


}
