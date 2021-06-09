package com.yuhan.booking.service;

import com.yuhan.rent.entity.AvailableCars;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author yuhan
 * @date 09.06.2021 - 21:19
 * @purpose
 */
@Component
public class RentServiceFallBack implements RentService{
    @Override
    public AvailableCars findByCarUid(int officeUid, int carUid) {
        return new AvailableCars();
    }

    @Override
    public void deleteCarFromOffice(int officeUid, int carUid) {

    }

    @Override
    public String addCarToOffice(@Valid AvailableCars availableCar, int officeUid, int carUid) {
        return null;
    }

    @Override
    public void updateAvailableCars(@Valid AvailableCars availableCars) {

    }
}
